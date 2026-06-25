package com.scm.peopledesk.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
	public List<Contact> getByUserId(String userId) {

		return contactRepo.findByUserId(userId);
	}

	@Override
	public Page<Contact> getByUser(User user, int page, int size, String sortBy, String direction) {

		Sort sort = direction.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		var pageable = PageRequest.of(page, size);

		return contactRepo.findByUser(user, pageable);

	}

	@Override
	public List<Contact> getByUser(User user) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getByUser'");
	}

	@Override
	public Page<Contact> searchByName(
			String nameKeyword,
			int size,
			int page,
			String sortBy,
			String order,
			User user) {

		Sort sort = order.equalsIgnoreCase("desc")
				? Sort.by(sortBy).descending()
				: Sort.by(sortBy).ascending();

		Pageable pageable = PageRequest.of(page, size, sort);

		return contactRepo.findByUserAndNameContaining(
				user,
				nameKeyword,
				pageable);
	}

	@Override
	public Page<Contact> searchByEmail(
			String emailKeyword,
			int size,
			int page,
			String sortBy,
			String order,
			User user) {

		Sort sort = order.equalsIgnoreCase("desc")
				? Sort.by(sortBy).descending()
				: Sort.by(sortBy).ascending();

		Pageable pageable = PageRequest.of(page, size, sort);

		return contactRepo.findByUserAndEmailContaining(
				user,
				emailKeyword,
				pageable);
	}

	@Override
	public Page<Contact> searchByPhoneNumber(
			String phoneNumberKeyword,
			int size,
			int page,
			String sortBy,
			String order,
			User user) {

		Sort sort = order.equalsIgnoreCase("desc")
				? Sort.by(sortBy).descending()
				: Sort.by(sortBy).ascending();

		Pageable pageable = PageRequest.of(page, size, sort);

		return contactRepo.findByUserAndPhoneNumberContaining(
				user,
				phoneNumberKeyword,
				pageable);
	}

}
