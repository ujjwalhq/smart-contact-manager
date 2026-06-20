package com.scm.peopledesk.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.scm.peopledesk.entities.Contact;
import com.scm.peopledesk.entities.User;
import com.scm.peopledesk.helpers.ResourceNotFoundException;
import com.scm.peopledesk.repsitories.ContactRepo;
import com.scm.peopledesk.services.ContactService;

@Service
public class ContactServiceImpl implements ContactService {

	private final ContactRepo contactRepo;

	public ContactServiceImpl(ContactRepo contactRepo) {
		this.contactRepo = contactRepo;
	}

	@Override
	public Contact save(Contact contact) {

		String contactId = UUID.randomUUID().toString();

		contact.setId(contactId);
		return contactRepo.save(contact);

	}

	@Override
	public Contact update(Contact contact) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'update'");
	}

	@Override
	public List<Contact> getAll() {

		return contactRepo.findAll();
	}

	@Override
	public Contact getById(String id) {

		return contactRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Contact not found with given id" + id));
	}

	@Override
	public void delete(String id) {

		var contact = contactRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Contact not found with given id" + id));
		contactRepo.delete(contact);
	}

	@Override
	public List<Contact> search(String name, String email, String phoneNumber) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'search'");
	}

	@Override
	public List<Contact> getByUserId(String userId) {

		return contactRepo.findByUserId(userId);
	}

	@Override
	public List<Contact> getByUser(User user) {

		return contactRepo.findByUser(user);

	}

}
