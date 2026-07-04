package com.scm.peopledesk.controller;

import com.scm.peopledesk.entities.User;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.peopledesk.entities.Contact;
import com.scm.peopledesk.forms.ContactForm;
import com.scm.peopledesk.helpers.Helper;
import com.scm.peopledesk.helpers.Message;
import com.scm.peopledesk.helpers.MessageType;
import com.scm.peopledesk.services.ContactService;
import com.scm.peopledesk.services.CsvService;
import com.scm.peopledesk.services.ImageService;
import com.scm.peopledesk.services.UserService;
import com.scm.peopledesk.services.CsvService;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.http.HttpServletResponse;

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

  @Autowired
  private CsvService csvService;

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
  public String viewContacts(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size,
      @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
      @RequestParam(value = "direction", defaultValue = "asc") String direction,
      Model model,
      Authentication authentication) {

    // load all the contacts
    String username = Helper.getEmailOfLoggedInUser(authentication);

    User user = userService.getUserByEmail(username);

    Page<Contact> pageContact = contactService.getByUser(user, page, size, sortBy, direction);

    model.addAttribute("pageContact", pageContact);

    model.addAttribute("isSearch", false);
    return "user/contacts";
  }

  // delete contact
  @RequestMapping("/delete/{contactId}")
  public String deleteContact(@PathVariable("contactId") String contactId, HttpSession session) {

    // Fetch contact details before deletion so we can show the contact name in the
    // success message.
    Contact contact = contactService.getById(contactId);
    String contactName = contact.getName();

    // Delete the selected contact from the database.
    contactService.delete(contactId);
    logger.info("Contact {} deteled ", contactId);

    session.setAttribute("message",
        Message.builder()
            .content(contactName + " has been removed from your contacts")
            .type(MessageType.green)
            .build());

    return "redirect:/user/contacts";
  }

  // search handler

  @GetMapping("/search")
  public String searchHandler(
      @RequestParam("field") String field,
      @RequestParam("keyword") String value,
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size,
      @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
      @RequestParam(value = "direction", defaultValue = "asc") String direction,
      Model model, Authentication authentication) {

    logger.info("Searching by {} with value {}", field, value);

    var user = userService.getUserByEmail(Helper.getEmailOfLoggedInUser(authentication));

    Page<Contact> pageContact = null;

    if (field.equalsIgnoreCase("name")) {

      pageContact = contactService.searchByName(
          value,
          size,
          page,
          sortBy,
          direction,
          user);

    } else if (field.equalsIgnoreCase("email")) {

      pageContact = contactService.searchByEmail(
          value,
          size,
          page,
          sortBy,
          direction,
          user);

    } else if (field.equalsIgnoreCase("phoneNumber")) {
      value = value.trim(); // for removing +

      pageContact = contactService.searchByPhoneNumber(
          value,
          size,
          page,
          sortBy,
          direction,
          user);
    }

    model.addAttribute("pageContact", pageContact);
    model.addAttribute("contacts", pageContact.getContent());

    model.addAttribute("isSearch", true);
    model.addAttribute("field", field);
    model.addAttribute("keyword", value);
    model.addAttribute("sortBy", sortBy);
    model.addAttribute("direction", direction);

    return "user/contacts";
  }

  // Export Contacts
  @GetMapping("/export")
  public void exportContacts(
      Authentication authentication,
      HttpServletResponse response) throws IOException {

    String username = Helper.getEmailOfLoggedInUser(authentication);

    User user = userService.getUserByEmail(username);

    List<Contact> contacts = contactService.getByUser(user);

    response.setContentType("text/csv");
    response.setHeader(
        "Content-Disposition",
        "attachment; filename=contacts.csv");

    csvService.exportContacts(
        contacts,
        response.getWriter());
  }

}
