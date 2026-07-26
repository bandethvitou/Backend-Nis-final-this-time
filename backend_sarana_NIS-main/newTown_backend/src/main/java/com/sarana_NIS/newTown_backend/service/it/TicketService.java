package com.sarana_NIS.newTown_backend.service.it;

import com.sarana_NIS.newTown_backend.dto.request.it.TicketActionRequest;
import com.sarana_NIS.newTown_backend.dto.request.it.TicketBulkRequest;
import com.sarana_NIS.newTown_backend.dto.response.it.TicketDetailResponse;
import com.sarana_NIS.newTown_backend.dto.response.it.TicketStatsResponse;
import com.sarana_NIS.newTown_backend.dto.response.common.PageResponse;
import com.sarana_NIS.newTown_backend.model.entity.core.User;
import com.sarana_NIS.newTown_backend.model.entity.it.Ticket;
import com.sarana_NIS.newTown_backend.model.enums.Priority;
import com.sarana_NIS.newTown_backend.model.enums.TicketCategory;
import com.sarana_NIS.newTown_backend.model.enums.TicketStatus;
import com.sarana_NIS.newTown_backend.repository.TicketRepository;
import com.sarana_NIS.newTown_backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TicketService {

    private static final Logger log = LoggerFactory.getLogger(TicketService.class);

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public TicketService(TicketRepository ticketRepository, UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Ticket createTicket(Ticket ticket, Long reportedByUserId) {
        User reportedBy = userRepository.findById(reportedByUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ticket.setReportedBy(reportedBy);
        ticket.setStatus(TicketStatus.OPEN);
        ticket.setPriority(ticket.getPriority() != null ? ticket.getPriority() : Priority.MEDIUM);

        Ticket saved = ticketRepository.save(ticket);
        log.info("Ticket created: {}", saved.getTitle());
        return saved;
    }

    @Transactional
    public List<Ticket> createBulkTickets(TicketBulkRequest request, Long reportedByUserId) {
        User reportedBy = userRepository.findById(reportedByUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Ticket> tickets = new ArrayList<>();

        // ✅ Use getUserIds() directly
        List<Long> userIds = request.getUserIds();
        if (userIds == null || userIds.isEmpty()) {
            // Fallback to assignedToIds
            userIds = request.getAssignedToIds();
        }
        if (userIds == null || userIds.isEmpty()) {
            throw new RuntimeException("No user IDs provided for bulk ticket creation");
        }

        for (Long userId : userIds) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found: " + userId));

            Ticket ticket = new Ticket();
            ticket.setTitle(request.getTitle());
            ticket.setDescription(request.getDescription());
            ticket.setCategory(request.getCategory());
            ticket.setPriority(request.getPriority());
            // ✅ Location is handled separately if needed
            ticket.setReportedBy(reportedBy);
            ticket.setStatus(TicketStatus.OPEN);
            tickets.add(ticket);
        }

        List<Ticket> saved = ticketRepository.saveAll(tickets);
        log.info("Bulk tickets created: {} tickets", saved.size());
        return saved;
    }

    @Transactional
    public Ticket assignTicket(TicketActionRequest request) {
        Ticket ticket = ticketRepository.findById(request.getTicketId())
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        if (request.getAssignedTo() != null) {
            User assignee = userRepository.findById(request.getAssignedTo())
                    .orElseThrow(() -> new RuntimeException("Assignee not found"));
            ticket.setAssignedTo(assignee);
        }

        ticket.setStatus(TicketStatus.IN_PROGRESS);
        Ticket saved = ticketRepository.save(ticket);
        log.info("Ticket assigned: {}", request.getTicketId());
        return saved;
    }

    @Transactional
    public Ticket resolveTicket(TicketActionRequest request) {
        Ticket ticket = ticketRepository.findById(request.getTicketId())
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        ticket.setStatus(TicketStatus.RESOLVED);
        ticket.setResolutionNotes(request.getResolutionNotes());

        Ticket saved = ticketRepository.save(ticket);
        log.info("Ticket resolved: {}", request.getTicketId());
        return saved;
    }

    @Transactional
    public Ticket closeTicket(TicketActionRequest request) {
        Ticket ticket = ticketRepository.findById(request.getTicketId())
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        ticket.setStatus(TicketStatus.CLOSED);
        ticket.setResolutionNotes(request.getResolutionNotes());

        Ticket saved = ticketRepository.save(ticket);
        log.info("Ticket closed: {}", request.getTicketId());
        return saved;
    }

    @Transactional
    public Ticket reopenTicket(TicketActionRequest request) {
        Ticket ticket = ticketRepository.findById(request.getTicketId())
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        ticket.setStatus(TicketStatus.OPEN);
        ticket.setResolutionNotes(null);

        Ticket saved = ticketRepository.save(ticket);
        log.info("Ticket reopened: {}", request.getTicketId());
        return saved;
    }

    @Transactional
    public Ticket updateTicketStatus(Long ticketId, TicketStatus status) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        ticket.setStatus(status);
        Ticket saved = ticketRepository.save(ticket);
        log.info("Ticket status updated: {} -> {}", ticketId, status);
        return saved;
    }

    public Ticket getTicketById(Long ticketId) {
        return ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
    }

    public TicketDetailResponse getTicketDetail(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        TicketDetailResponse response = new TicketDetailResponse();
        response.setId(ticket.getId());
        response.setTitle(ticket.getTitle());
        response.setDescription(ticket.getDescription());

        // ✅ Set enum values directly
        response.setCategory(ticket.getCategory());
        response.setPriority(ticket.getPriority());
        response.setStatus(ticket.getStatus());

        response.setCreatedAt(ticket.getCreatedAt());
        response.setUpdatedAt(ticket.getUpdatedAt());

        if (ticket.getReportedBy() != null) {
            TicketDetailResponse.UserInfo reporter = new TicketDetailResponse.UserInfo();
            reporter.setId(ticket.getReportedBy().getId());
            reporter.setName(ticket.getReportedBy().getFullName());
            reporter.setEmail(ticket.getReportedBy().getEmail());
            response.setReportedBy(reporter);
        }

        if (ticket.getAssignedTo() != null) {
            TicketDetailResponse.UserInfo assignee = new TicketDetailResponse.UserInfo();
            assignee.setId(ticket.getAssignedTo().getId());
            assignee.setName(ticket.getAssignedTo().getFullName());
            assignee.setEmail(ticket.getAssignedTo().getEmail());
            response.setAssignedTo(assignee);
        }

        return response;
    }

    public List<Ticket> getTicketsByReportedBy(Long userId) {
        return ticketRepository.findByReportedBy(userId);
    }

    public List<Ticket> getTicketsByAssignedTo(Long userId) {
        return ticketRepository.findByAssignedTo(userId);
    }

    public List<Ticket> getTicketsByStatus(TicketStatus status) {
        return ticketRepository.findByStatus(status);
    }

    public List<Ticket> getOpenTickets() {
        return ticketRepository.findOpenTickets();
    }

    public List<Ticket> getUnassignedTickets() {
        return ticketRepository.findUnassignedTickets();
    }

    public List<Ticket> getTicketsByCategory(TicketCategory category) {
        return ticketRepository.findByCategory(category);
    }

    public PageResponse<Ticket> getAllTickets(Pageable pageable) {
        Page<Ticket> page = ticketRepository.findAll(pageable);
        return new PageResponse<>(page.getContent(), page.getNumber(), page.getSize(),
                page.getTotalElements(), page.getTotalPages());
    }

    public TicketStatsResponse getTicketStats() {
        TicketStatsResponse stats = new TicketStatsResponse();

        Long totalTickets = ticketRepository.count();
        stats.setTotalTickets(totalTickets.intValue());

        Long openTickets = ticketRepository.countOpenTickets();
        stats.setOpenTickets(openTickets.intValue());

        // Count by status
        for (TicketStatus status : TicketStatus.values()) {
            List<Ticket> tickets = ticketRepository.findByStatus(status);
            switch (status) {
                case OPEN:
                    stats.setOpenTickets(tickets.size());
                    break;
                case IN_PROGRESS:
                    stats.setInProgressTickets(tickets.size());
                    break;
                case RESOLVED:
                    stats.setResolvedTickets(tickets.size());
                    break;
                case CLOSED:
                    stats.setClosedTickets(tickets.size());
                    break;
                default:
                    break;
            }
        }

        Map<String, Integer> byCategory = new HashMap<>();
        List<Object[]> categoryResults = ticketRepository.countTicketsByCategory();
        for (Object[] result : categoryResults) {
            byCategory.put(result[0].toString(), ((Number) result[1]).intValue());
        }
        stats.setByCategory(byCategory);

        Map<String, Integer> byPriority = new HashMap<>();
        List<Object[]> priorityResults = ticketRepository.countTicketsByPriority();
        for (Object[] result : priorityResults) {
            byPriority.put(result[0].toString(), ((Number) result[1]).intValue());
        }
        stats.setByPriority(byPriority);

        Double avgTime = ticketRepository.findAverageResolutionTime();
        stats.setAverageResolutionTime(avgTime != null ? avgTime.intValue() : 0);

        return stats;
    }

    @Transactional
    public void deleteTicket(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
        ticketRepository.delete(ticket);
        log.info("Ticket deleted: {}", ticketId);
    }
}