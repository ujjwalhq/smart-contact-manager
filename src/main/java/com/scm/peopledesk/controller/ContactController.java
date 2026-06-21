package com.scm.peopledesk.controller;

import com.scm.peopledesk.entities.User;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.peopledesk.entities.Contact;
import com.scm.peopledesk.forms.ContactForm;
import com.scm.peopledesk.helpers.Helper;
import com.scm.peopledesk.helpers.Message;
import com.scm.peopledesk.helpers.MessageType;
import com.scm.peopledesk.services.ContactService;
import com.scm.peopledesk.services.ImageService;
import com.scm.peopledesk.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {

  private Logger logger = org.slf4j.LoggerFactory.getLogger(ContactController.class);

  @Autowired
  private ContactService contactService;

  @Autowired
  private ImageService imageService;

  @Autowired
  private UserService userService;

  @RequestMapping("/add")
  // add contact page: handler
  public String addContactView(Model model) {

    ContactForm contactForm = new ContactForm();

    model.addAttribute("contactForm", contactForm);

    return "user/add-contact";
  }

  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public String saveContact(@Valid @ModelAttribute ContactForm contactForm, BindingResult result,
      Authentication authentication, HttpSession session) {

    // process the form data

    // validate form
    if (result.hasErrors()) {
      session.setAttribute("message",
          Message.builder()
              .content("Please correct the highlighted errors")
              .type(MessageType.red)
              .build());
      return "user/add-contact";
    }

    String username = Helper.getEmailOfLoggedInUser(authentication);

    // form--->contact
    User user = userService.getUserByEmail(username);

    // process the contact picture

    String fileURL = null;
    String filename = null;

    if (contactForm.getProfileImage() != null &&
        !contactForm.getProfileImage().isEmpty()) {

      logger.info("file information : {}",
          contactForm.getProfileImage().getOriginalFilename());

      // upload image

      filename = UUID.randomUUID().toString();

      fileURL = imageService.uploadImage(
          contactForm.getProfileImage(),
          filename);
    }

    Contact contact = new Contact();
    contact.setName(contactForm.getName());
    contact.setFavorite(contactForm.isFavorite());
    contact.setEmail(contactForm.getEmail());
    contact.setPhoneNumber(contactForm.getPhoneNumber());
    contact.setAddress(contactForm.getAddress());
    contact.setDescription(contactForm.getDescription());
    contact.setUser(user);
    contact.setLinkedInLink(contactForm.getLinkedInLink());
    contact.setWebsiteLink(contactForm.getWebsiteLink());

    // Save uploaded image details if profile image is provided,
    // otherwise assign a default contact image.
    if (fileURL != null) {

      contact.setPicture(fileURL);
      contact.setCloudinaryImagePublicId(filename);

    } else {

      String avatarUrl = "https://ui-avatars.com/api/?name="
          + contactForm.getName().replace(" ", "+")
          + "&background=1E40AF"
          + "&color=fff"
          + "&rounded=true";

      contact.setPicture(avatarUrl);
      
    }

    contactService.save(contact);

    System.out.print(contactForm);

    // set the contact picture url

    // set the message to be display on the view
    session.setAttribute("message",
        Message.builder()
            .content("Contact added successfully")
            .type(MessageType.green)
            .build());
    return "redirect:/user/contacts/add";
  }

  // view contacts
  @RequestMapping
  public String viewContacts(Model model, Authentication authentication) {

    // load all the contacts
    String username = Helper.getEmailOfLoggedInUser(authentication);

    User user = userService.getUserByEmail(username);

    List<Contact> contacts = contactService.getByUser(user);

    model.addAttribute("contacts", contacts);

    return "user/contacts";
  }
}
