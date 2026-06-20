package com.scm.peopledesk.services;

import java.util.List;

import com.scm.peopledesk.entities.Contact;
import com.scm.peopledesk.entities.User;

public interface ContactService {

  //save contact
  Contact save(Contact contact);

  //update contact
  Contact update(Contact contact);

  //get contacts
  List<Contact> getAll();

  //get contact by id
  Contact getById(String id);

  //delete contact
  void delete(String id);

  //search contact
  List<Contact> search(String name,String email,String phoneNumber);

  //get contacts by userId
  List<Contact> getByUserId(String userId);

  List<Contact> getByUser(User user);

}
