package com.sarana_NIS.newTown_backend.controller.it;

import com.sarana_NIS.newTown_backend.dto.request.it.TicketActionRequest;
import com.sarana_NIS.newTown_backend.dto.request.it.TicketBulkRequest;
import com.sarana_NIS.newTown_backend.dto.response.common.ApiResponse;
import com.sarana_NIS.newTown_backend.dto.response.common.PageResponse;
import com.sarana_NIS.newTown_backend.dto.response.it.TicketDetailResponse;
import com.sarana_NIS.newTown_backend.dto.response.it.TicketStatsResponse;
import com.sarana_NIS.newTown_backend.model.entity.it.Ticket;
import com.sarana_NIS.newTown_backend.model.enums.TicketCategory;
import com.sarana_NIS.newTown_backend.model.enums.TicketStatus;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresPermission;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresRole;
import com.sarana_NIS.newTown_backend.service.it.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/it/tickets")
@Tag(name = "IT Ticket Management", description = "IT support ticket management APIs")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    @RequiresRole({"STAFF", "TEACHER", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @RequiresPermission("TICKET_CREATE")
    @Operation(summary = "Create a new ticket")
    public ResponseEntity<ApiResponse<Ticket>> createTicket(
            @Valid @RequestBody Ticket ticket) {
        System.out.println("Creating new ticket: " + ticket.getTitle());
        Long currentUserId = 1L;
        Ticket created = ticketService.createTicket(ticket, currentUserId);
        return ResponseEntity.ok(ApiResponse.success("Ticket created successfully", created));
    }

    @PostMapping("/bulk")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @RequiresPermission("TICKET_CREATE")
    @Operation(summary = "Create bulk tickets")
    public ResponseEntity<ApiResponse<List<Ticket>>> createBulkTickets(
            @Valid @RequestBody TicketBulkRequest request) {
        System.out.println("Creating bulk tickets for " + request.getUserIds().size() + " users");
        Long currentUserId = 1L;
        List<Ticket> tickets = ticketService.createBulkTickets(request, currentUserId);
        return ResponseEntity.ok(ApiResponse.success("Bulk tickets created successfully", tickets));
    }

    @GetMapping("/{ticketId}")
    @RequiresRole({"STAFF", "TEACHER", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @Operation(summary = "Get ticket by ID")
    public ResponseEntity<ApiResponse<TicketDetailResponse>> getTicketDetail(
            @PathVariable Long ticketId) {
        System.out.println("Fetching ticket detail: " + ticketId);
        TicketDetailResponse response = ticketService.getTicketDetail(ticketId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR", "IT_SUPPORT"})
    @Operation(summary = "Get all tickets with pagination")
    public ResponseEntity<ApiResponse<PageResponse<Ticket>>> getAllTickets(
            Pageable pageable) {
        System.out.println("Fetching all tickets");
        PageResponse<Ticket> response = ticketService.getAllTickets(pageable);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/status/{status}")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR", "IT_SUPPORT"})
    @Operation(summary = "Get tickets by status")
    public ResponseEntity<ApiResponse<List<Ticket>>> getTicketsByStatus(
            @PathVariable TicketStatus status) {
        System.out.println("Fetching tickets by status: " + status);
        List<Ticket> tickets = ticketService.getTicketsByStatus(status);
        return ResponseEntity.ok(ApiResponse.success(tickets));
    }

    @GetMapping("/open")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR", "IT_SUPPORT"})
    @Operation(summary = "Get open tickets")
    public ResponseEntity<ApiResponse<List<Ticket>>> getOpenTickets() {
        System.out.println("Fetching open tickets");
        List<Ticket> tickets = ticketService.getOpenTickets();
        return ResponseEntity.ok(ApiResponse.success(tickets));
    }

    @GetMapping("/unassigned")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR", "IT_SUPPORT"})
    @Operation(summary = "Get unassigned tickets")
    public ResponseEntity<ApiResponse<List<Ticket>>> getUnassignedTickets() {
        System.out.println("Fetching unassigned tickets");
        List<Ticket> tickets = ticketService.getUnassignedTickets();
        return ResponseEntity.ok(ApiResponse.success(tickets));
    }

    @GetMapping("/category/{category}")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR", "IT_SUPPORT"})
    @Operation(summary = "Get tickets by category")
    public ResponseEntity<ApiResponse<List<Ticket>>> getTicketsByCategory(
            @PathVariable TicketCategory category) {
        System.out.println("Fetching tickets by category: " + category);
        List<Ticket> tickets = ticketService.getTicketsByCategory(category);
        return ResponseEntity.ok(ApiResponse.success(tickets));
    }

    @GetMapping("/reporter/{userId}")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR", "IT_SUPPORT"})
    @Operation(summary = "Get tickets by reporter")
    public ResponseEntity<ApiResponse<List<Ticket>>> getTicketsByReportedBy(
            @PathVariable Long userId) {
        System.out.println("Fetching tickets by reporter: " + userId);
        List<Ticket> tickets = ticketService.getTicketsByReportedBy(userId);
        return ResponseEntity.ok(ApiResponse.success(tickets));
    }

    @GetMapping("/assignee/{userId}")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR", "IT_SUPPORT"})
    @Operation(summary = "Get tickets by assignee")
    public ResponseEntity<ApiResponse<List<Ticket>>> getTicketsByAssignedTo(
            @PathVariable Long userId) {
        System.out.println("Fetching tickets by assignee: " + userId);
        List<Ticket> tickets = ticketService.getTicketsByAssignedTo(userId);
        return ResponseEntity.ok(ApiResponse.success(tickets));
    }

    @PostMapping("/assign")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR", "IT_SUPPORT"})
    @RequiresPermission("TICKET_ASSIGN")
    @Operation(summary = "Assign ticket to staff")
    public ResponseEntity<ApiResponse<Ticket>> assignTicket(
            @Valid @RequestBody TicketActionRequest request) {
        System.out.println("Assigning ticket: " + request.getTicketId());
        Ticket ticket = ticketService.assignTicket(request);
        return ResponseEntity.ok(ApiResponse.success("Ticket assigned successfully", ticket));
    }

    @PostMapping("/resolve")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR", "IT_SUPPORT"})
    @RequiresPermission("TICKET_RESOLVE")
    @Operation(summary = "Resolve ticket")
    public ResponseEntity<ApiResponse<Ticket>> resolveTicket(
            @Valid @RequestBody TicketActionRequest request) {
        System.out.println("Resolving ticket: " + request.getTicketId());
        Ticket ticket = ticketService.resolveTicket(request);
        return ResponseEntity.ok(ApiResponse.success("Ticket resolved successfully", ticket));
    }

    @PostMapping("/close")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR", "IT_SUPPORT"})
    @RequiresPermission("TICKET_CLOSE")
    @Operation(summary = "Close ticket")
    public ResponseEntity<ApiResponse<Ticket>> closeTicket(
            @Valid @RequestBody TicketActionRequest request) {
        System.out.println("Closing ticket: " + request.getTicketId());
        Ticket ticket = ticketService.closeTicket(request);
        return ResponseEntity.ok(ApiResponse.success("Ticket closed successfully", ticket));
    }

    @PostMapping("/reopen")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR", "IT_SUPPORT"})
    @RequiresPermission("TICKET_REOPEN")
    @Operation(summary = "Reopen ticket")
    public ResponseEntity<ApiResponse<Ticket>> reopenTicket(
            @Valid @RequestBody TicketActionRequest request) {
        System.out.println("Reopening ticket: " + request.getTicketId());
        Ticket ticket = ticketService.reopenTicket(request);
        return ResponseEntity.ok(ApiResponse.success("Ticket reopened successfully", ticket));
    }

    @GetMapping("/stats")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR", "IT_SUPPORT"})
    @Operation(summary = "Get ticket statistics")
    public ResponseEntity<ApiResponse<TicketStatsResponse>> getTicketStats() {
        System.out.println("Fetching ticket statistics");
        TicketStatsResponse stats = ticketService.getTicketStats();
        return ResponseEntity.ok(ApiResponse.success(stats));
    }

    @DeleteMapping("/{ticketId}")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR", "IT_SUPPORT"})
    @RequiresPermission("TICKET_DELETE")
    @Operation(summary = "Delete ticket")
    public ResponseEntity<ApiResponse<Void>> deleteTicket(
            @PathVariable Long ticketId) {
        System.out.println("Deleting ticket: " + ticketId);
        ticketService.deleteTicket(ticketId);
        return ResponseEntity.ok(ApiResponse.success("Ticket deleted successfully", null));
    }
}