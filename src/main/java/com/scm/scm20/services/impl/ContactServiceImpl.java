package com.scm.scm20.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.scm.scm20.entities.Contact;
import com.scm.scm20.entities.User;
import com.scm.scm20.helpers.ResourceNotFoundException;
import com.scm.scm20.repositories.ContactRepo;
import com.scm.scm20.services.ContactService;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    ContactRepo contactRepo;

    @Override
    public Contact save(Contact contact) {
        String contactId = UUID.randomUUID().toString();
        contact.setId(contactId);
        return contactRepo.save(contact);
    }

    @Override
    public Contact update(Contact contact) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Contact> getAll() {
        return contactRepo.findAll();
    }

    @Override
    public Contact getById(String id) {
        return contactRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Contact not found with given id : " + id));
    }

    @Override
    public void delete(String id) {
        var contact = contactRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Contact not found with given id : " + id));
        contactRepo.delete(contact);
    }

    @Override
    public List<Contact> search(String name, String email, String phoneNumber) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Contact> getByUserId(String id) {
        return contactRepo.findByUserId(id);
    }

    @Override
    public Page<Contact> getByUser(User user, int page, int size, String sortBy, String direction) {

        Sort sort = direction.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        var pageable = PageRequest.of(page, size, sort);

        return contactRepo.findByUser(user, pageable);
    }

}
