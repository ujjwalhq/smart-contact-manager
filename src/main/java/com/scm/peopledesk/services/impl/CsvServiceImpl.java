package com.scm.peopledesk.services.impl;

import java.io.Writer;
import java.util.List;

import org.springframework.stereotype.Service;

import com.opencsv.CSVWriter;
import com.scm.peopledesk.entities.Contact;
import com.scm.peopledesk.services.CsvService;

@Service
public class CsvServiceImpl implements CsvService {

    @Override
    public void exportContacts(List<Contact> contacts, Writer writer) {

        CSVWriter csvWriter = new CSVWriter(writer);

        // Header
        csvWriter.writeNext(new String[]{
                "Name",
                "Email",
                "Phone",
                "Address",
                "Website",
                "LinkedIn",
                "Favorite"
        });

        // Data
        for (Contact contact : contacts) {

            csvWriter.writeNext(new String[]{

                    contact.getName(),
                    contact.getEmail(),
                    contact.getPhoneNumber(),
                    contact.getAddress(),
                    contact.getWebsiteLink(),
                    contact.getLinkedInLink(),
                    String.valueOf(contact.isFavorite())

            });

        }

        try {
            csvWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}