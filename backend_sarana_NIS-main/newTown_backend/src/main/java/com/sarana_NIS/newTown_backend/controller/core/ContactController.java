package com.sarana_NIS.newTown_backend.controller.core;

import com.sarana_NIS.newTown_backend.dto.request.core.ContactRequest;
import com.sarana_NIS.newTown_backend.dto.response.common.ApiResponse;
import com.sarana_NIS.newTown_backend.dto.response.core.ContactResponse;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresPermission;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresRole;
import com.sarana_NIS.newTown_backend.service.core.ContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/core/contacts")
@Tag(name = "Contact Management", description = "Contact directory APIs")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "MANAGER"})
    @RequiresPermission("CONTACT_CREATE")
    @Operation(summary = "Create a new contact")
    public ResponseEntity<ApiResponse<ContactResponse>> createContact(
            @Valid @RequestBody ContactRequest request) {
        System.out.println("Creating contact: " + request.getName());
        ContactResponse response = contactService.createContact(request);
        return ResponseEntity.ok(ApiResponse.success("Contact created successfully", response));
    }

    @GetMapping("/{contactId}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "MANAGER"})
    @Operation(summary = "Get contact by ID")
    public ResponseEntity<ApiResponse<ContactResponse>> getContactById(
            @PathVariable Long contactId) {
        System.out.println("Fetching contact: " + contactId);
        ContactResponse response = contactService.getContactById(contactId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/department/{department}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "MANAGER"})
    @Operation(summary = "Get contacts by department")
    public ResponseEntity<ApiResponse<List<ContactResponse>>> getContactsByDepartment(
            @PathVariable String department) {
        System.out.println("Fetching contacts by department: " + department);
        List<ContactResponse> contacts = contactService.getContactsByDepartment(department);
        return ResponseEntity.ok(ApiResponse.success(contacts));
    }

    @GetMapping("/available")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "MANAGER"})
    @Operation(summary = "Get available contacts")
    public ResponseEntity<ApiResponse<List<ContactResponse>>> getAvailableContacts() {
        System.out.println("Fetching available contacts");
        List<ContactResponse> contacts = contactService.getAvailableContacts();
        return ResponseEntity.ok(ApiResponse.success(contacts));
    }

    @GetMapping("/search")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "MANAGER"})
    @Operation(summary = "Search contacts")
    public ResponseEntity<ApiResponse<List<ContactResponse>>> searchContacts(
            @RequestParam String keyword) {
        System.out.println("Searching contacts with keyword: " + keyword);
        List<ContactResponse> contacts = contactService.searchContacts(keyword);
        return ResponseEntity.ok(ApiResponse.success(contacts));
    }

    @PutMapping("/{contactId}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "MANAGER"})
    @RequiresPermission("CONTACT_UPDATE")
    @Operation(summary = "Update contact")
    public ResponseEntity<ApiResponse<ContactResponse>> updateContact(
            @PathVariable Long contactId,
            @Valid @RequestBody ContactRequest request) {
        System.out.println("Updating contact: " + contactId);
        ContactResponse response = contactService.updateContact(contactId, request);
        return ResponseEntity.ok(ApiResponse.success("Contact updated successfully", response));
    }

    @PatchMapping("/{contactId}/availability")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "MANAGER"})
    @RequiresPermission("CONTACT_UPDATE")
    @Operation(summary = "Toggle contact availability")
    public ResponseEntity<ApiResponse<ContactResponse>> toggleAvailability(
            @PathVariable Long contactId) {
        System.out.println("Toggling availability for contact: " + contactId);
        ContactResponse response = contactService.toggleAvailability(contactId);
        return ResponseEntity.ok(ApiResponse.success("Contact availability toggled", response));
    }

    @GetMapping("/stats")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "MANAGER"})
    @Operation(summary = "Get contact statistics")
    public ResponseEntity<ApiResponse<Object>> getContactStats() {
        System.out.println("Fetching contact statistics");
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @DeleteMapping("/{contactId}")
    @RequiresRole({"DIRECTOR", "TOP_MANAGER", "MANAGER"})
    @RequiresPermission("CONTACT_DELETE")
    @Operation(summary = "Delete contact")
    public ResponseEntity<ApiResponse<Void>> deleteContact(
            @PathVariable Long contactId) {
        System.out.println("Deleting contact: " + contactId);
        contactService.deleteContact(contactId);
        return ResponseEntity.ok(ApiResponse.success("Contact deleted successfully", null));
    }
}