package com.sarana_NIS.newTown_backend.service.core;

import com.sarana_NIS.newTown_backend.dto.request.core.ContactRequest;
import com.sarana_NIS.newTown_backend.dto.response.core.ContactResponse;
import com.sarana_NIS.newTown_backend.mapper.core.ContactMapper;
import com.sarana_NIS.newTown_backend.model.entity.core.Contact;
import com.sarana_NIS.newTown_backend.repository.ContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactService {

    private static final Logger log = LoggerFactory.getLogger(ContactService.class);

    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;

    public ContactService(ContactRepository contactRepository, ContactMapper contactMapper) {
        this.contactRepository = contactRepository;
        this.contactMapper = contactMapper;
    }

    @Transactional
    public ContactResponse createContact(ContactRequest request) {
        log.info("Creating contact: {}", request.getName());
        Contact contact = contactMapper.toEntity(request);
        contact.setIsAvailable(true);
        Contact saved = contactRepository.save(contact);
        log.info("Contact created: {}", contact.getName());
        return contactMapper.toResponse(saved);
    }

    @Transactional
    public ContactResponse updateContact(Long contactId, ContactRequest request) {
        log.info("Updating contact: {}", contactId);
        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new RuntimeException("Contact not found"));

        contactMapper.updateEntity(request, contact);

        Contact saved = contactRepository.save(contact);
        log.info("Contact updated: {}", contactId);
        return contactMapper.toResponse(saved);
    }

    @Transactional
    public ContactResponse toggleAvailability(Long contactId) {
        log.info("Toggling availability for contact: {}", contactId);
        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new RuntimeException("Contact not found"));
        contact.setIsAvailable(!contact.getIsAvailable());
        Contact saved = contactRepository.save(contact);
        log.info("Contact availability toggled: {}", contactId);
        return contactMapper.toResponse(saved);
    }

    public ContactResponse getContactById(Long contactId) {
        log.info("Fetching contact: {}", contactId);
        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new RuntimeException("Contact not found"));
        return contactMapper.toResponse(contact);
    }

    public List<ContactResponse> getContactsByDepartment(String department) {
        log.info("Fetching contacts by department: {}", department);
        List<Contact> contacts = contactRepository.findByDepartment(department);
        return contacts.stream().map(contactMapper::toResponse).collect(Collectors.toList());
    }

    public List<ContactResponse> getContactsByPosition(String position) {
        log.info("Fetching contacts by position: {}", position);
        List<Contact> contacts = contactRepository.findByPosition(position);
        return contacts.stream().map(contactMapper::toResponse).collect(Collectors.toList());
    }

    public List<ContactResponse> getAvailableContacts() {
        log.info("Fetching available contacts");
        List<Contact> contacts = contactRepository.findAvailableContacts();
        return contacts.stream().map(contactMapper::toResponse).collect(Collectors.toList());
    }

    public List<ContactResponse> searchContacts(String keyword) {
        log.info("Searching contacts with keyword: {}", keyword);
        List<Contact> contacts = contactRepository.searchContacts(keyword);
        return contacts.stream().map(contactMapper::toResponse).collect(Collectors.toList());
    }

    public List<Object[]> countContactsByDepartment() {
        return contactRepository.countContactsByDepartment();
    }

    @Transactional
    public void deleteContact(Long contactId) {
        log.info("Deleting contact: {}", contactId);
        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new RuntimeException("Contact not found"));
        contactRepository.delete(contact);
        log.info("Contact deleted: {}", contactId);
    }
}