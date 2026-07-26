package com.sarana_NIS.newTown_backend.dto.request.it;

import com.sarana_NIS.newTown_backend.model.enums.TicketStatus;
import jakarta.validation.constraints.NotNull;

public class TicketActionRequest {

    @NotNull(message = "Ticket ID is required")
    private Long ticketId;

    private Long assignedTo;
    private String resolutionNotes;
    private TicketStatus status;
    private String action;

    // Constructors
    public TicketActionRequest() {}

    public TicketActionRequest(Long ticketId, String action) {
        this.ticketId = ticketId;
        this.action = action;
    }

    // Getters and Setters
    public Long getTicketId() { return ticketId; }
    public void setTicketId(Long ticketId) { this.ticketId = ticketId; }

    public Long getAssignedTo() { return assignedTo; }
    public void setAssignedTo(Long assignedTo) { this.assignedTo = assignedTo; }

    public String getResolutionNotes() { return resolutionNotes; }
    public void setResolutionNotes(String resolutionNotes) { this.resolutionNotes = resolutionNotes; }

    public TicketStatus getStatus() { return status; }
    public void setStatus(TicketStatus status) { this.status = status; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
}