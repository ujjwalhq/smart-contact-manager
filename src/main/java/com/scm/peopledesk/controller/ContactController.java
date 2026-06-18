package com.scm.peopledesk.controller;

import com.scm.peopledesk.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.peopledesk.entities.Contact;
import com.scm.peopledesk.forms.ContactForm;
import com.scm.peopledesk.helpers.Helper;
import com.scm.peopledesk.services.ContactService;
import com.scm.peopledesk.services.UserService;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {

  @Autowired
  private ContactService contactService;

  @Autowired
  private UserService userService;

  @RequestMapping("/add")
  // add contact page: handler
  public String addContactView(Model model) {

    ContactForm contactForm = new ContactForm();
    contactForm.setName("Ujjwal Singh");
    contactForm.setFavorite(true);

    model.addAttribute("contactForm", contactForm);

    return "user/add-contact";
  }

  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public String saveContact(@ModelAttribute ContactForm contactForm, Authentication authentication) {

    // process the form data

    //validate form


    String username=Helper.getEmailOfLoggedInUser(authentication);

    // form--->contact
    User user= userService.getUserByEmail(username);


    //process the contact picture


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

    contactService.save(contact);

    System.out.print(contactForm);

    //set the contact picture url

    //set the message to be display on the view

    return "redirect:/user/contacts/add";
  }
}
