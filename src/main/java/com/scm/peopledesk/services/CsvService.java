package com.scm.peopledesk.services;

import java.io.Writer;
import java.util.List;

import com.scm.peopledesk.entities.Contact;

public interface CsvService {
    
    void exportContacts(List<Contact> contacts, Writer writer);

}