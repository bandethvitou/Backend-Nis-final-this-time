package com.sarana_NIS.newTown_backend.mapper.it;

import com.sarana_NIS.newTown_backend.dto.request.it.TicketBulkRequest;
import com.sarana_NIS.newTown_backend.dto.request.it.TicketRequest;
import com.sarana_NIS.newTown_backend.dto.response.it.ITDashboardResponse;
import com.sarana_NIS.newTown_backend.dto.response.it.TicketDetailResponse;
import com.sarana_NIS.newTown_backend.dto.response.it.TicketStatsResponse;
import com.sarana_NIS.newTown_backend.model.entity.it.Ticket;
import com.sarana_NIS.newTown_backend.model.enums.TicketStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface TicketMapper {

    // ========== Entity → Detail Response ==========
    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "priority", source = "priority")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    @Mapping(target = "assignedDate", source = "assignedDate")
    @Mapping(target = "resolvedDate", source = "resolvedDate")
    @Mapping(target = "closedDate", source = "closedDate")
    @Mapping(target = "resolutionNotes", source = "resolutionNotes")
    @Mapping(target = "isUrgent", source = "isUrgent")
    @Mapping(target = "reportedBy", expression = "java(mapUserInfo(ticket.getReportedBy()))")
    @Mapping(target = "assignedTo", expression = "java(mapUserInfo(ticket.getAssignedTo()))")
    TicketDetailResponse toDetailResponse(Ticket ticket);

    // ========== Request → Entity ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "reportedBy", ignore = true)
    @Mapping(target = "assignedTo", ignore = true)
    @Mapping(target = "status", constant = "OPEN")
    @Mapping(target = "resolutionNotes", ignore = true)
    @Mapping(target = "assignedDate", ignore = true)
    @Mapping(target = "resolvedDate", ignore = true)
    @Mapping(target = "closedDate", ignore = true)
    @Mapping(target = "isUrgent", constant = "false")
    Ticket toEntity(TicketRequest request);

    // ========== Bulk Request → Entity ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "reportedBy", ignore = true)
    @Mapping(target = "assignedTo", ignore = true)
    @Mapping(target = "status", constant = "OPEN")
    @Mapping(target = "resolutionNotes", ignore = true)
    @Mapping(target = "assignedDate", ignore = true)
    @Mapping(target = "resolvedDate", ignore = true)
    @Mapping(target = "closedDate", ignore = true)
    @Mapping(target = "isUrgent", constant = "false")
    Ticket toEntity(TicketBulkRequest request);

    // ========== Update ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "reportedBy", ignore = true)
    @Mapping(target = "assignedTo", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "resolutionNotes", ignore = true)
    @Mapping(target = "assignedDate", ignore = true)
    @Mapping(target = "resolvedDate", ignore = true)
    @Mapping(target = "closedDate", ignore = true)
    @Mapping(target = "isUrgent", ignore = true)
    void updateEntity(TicketRequest request, @MappingTarget Ticket ticket);

    // ========== Dashboard Mapping ==========
    default ITDashboardResponse.TicketSummary toTicketSummary(Ticket ticket) {
        if (ticket == null) return null;

        ITDashboardResponse.TicketSummary summary =
                new ITDashboardResponse.TicketSummary();
        summary.setId(ticket.getId());
        summary.setTitle(ticket.getTitle());
        summary.setCategory(ticket.getCategory() != null ?
                ticket.getCategory().name() : null);
        summary.setPriority(ticket.getPriority() != null ?
                ticket.getPriority().name() : null);
        summary.setStatus(ticket.getStatus() != null ?
                ticket.getStatus().name() : null);
        summary.setReportedBy(ticket.getReportedBy() != null ?
                ticket.getReportedBy().getFullName() : null);
        summary.setCreatedAt(ticket.getCreatedAt());
        return summary;
    }

    default List<ITDashboardResponse.TicketSummary> toTicketSummaries(
            List<Ticket> tickets) {
        if (tickets == null) return new java.util.ArrayList<>();
        return tickets.stream()
                .map(this::toTicketSummary)
                .collect(Collectors.toList());
    }

    // ========== Stats Response ==========
    default TicketStatsResponse toStatsResponse(List<Ticket> tickets) {
        if (tickets == null) return new TicketStatsResponse();

        TicketStatsResponse stats = new TicketStatsResponse();
        stats.setTotalTickets(tickets.size());

        int open = 0, inProgress = 0, resolved = 0, closed = 0;

        for (Ticket t : tickets) {
            if (t.getStatus() != null) {
                switch (t.getStatus()) {
                    case OPEN:
                        open++;
                        break;
                    case IN_PROGRESS:
                        inProgress++;
                        break;
                    case RESOLVED:
                        resolved++;
                        break;
                    case CLOSED:
                        closed++;
                        break;
                    default:
                        break;
                }
            }

            // Category breakdown
            if (t.getCategory() != null) {
                stats.getByCategory().merge(t.getCategory().name(), 1, Integer::sum);
            }

            // Priority breakdown
            if (t.getPriority() != null) {
                stats.getByPriority().merge(t.getPriority().name(), 1, Integer::sum);
            }
        }

        stats.setOpenTickets(open);
        stats.setInProgressTickets(inProgress);
        stats.setResolvedTickets(resolved);
        stats.setClosedTickets(closed);

        // Calculate average resolution time
        stats.setAverageResolutionTime(calculateAverageResolutionTime(tickets));

        return stats;
    }

    // ========== Helper Methods ==========

    default TicketDetailResponse.UserInfo mapUserInfo(
            com.sarana_NIS.newTown_backend.model.entity.core.User user) {
        if (user == null) return null;

        TicketDetailResponse.UserInfo info =
                new TicketDetailResponse.UserInfo();
        info.setId(user.getId());
        info.setName(user.getFullName());
        info.setEmail(user.getEmail());
        info.setPhone(user.getPhone());

        // Get department from staff if available
        if (user.getStaff() != null && user.getStaff().getDepartment() != null) {
            info.setDepartment(user.getStaff().getDepartment().getName());
        }

        return info;
    }

    default Integer calculateAverageResolutionTime(List<Ticket> tickets) {
        if (tickets == null || tickets.isEmpty()) return 0;

        List<Ticket> resolvedTickets = tickets.stream()
                .filter(t -> t.getStatus() != null &&
                        (t.getStatus() == TicketStatus.RESOLVED ||
                                t.getStatus() == TicketStatus.CLOSED))
                .filter(t -> t.getCreatedAt() != null && t.getResolvedDate() != null)
                .collect(Collectors.toList());

        if (resolvedTickets.isEmpty()) return 0;

        long totalMinutes = resolvedTickets.stream()
                .mapToLong(t -> java.time.Duration.between(
                        t.getCreatedAt(),
                        t.getResolvedDate()
                ).toMinutes())
                .sum();

        return (int) (totalMinutes / resolvedTickets.size());
    }

    // ========== Dashboard Response ==========
    default ITDashboardResponse toDashboardResponse(
            List<Ticket> openTickets,
            List<Ticket> recentTickets) {

        ITDashboardResponse dashboard = new ITDashboardResponse();

        // Ticket counts
        dashboard.setOpenTickets(openTickets != null ? openTickets.size() : 0);

        // Recent tickets
        if (recentTickets != null) {
            dashboard.setRecentTickets(recentTickets.stream()
                    .map(this::toTicketSummary)
                    .collect(Collectors.toList()));
        }

        return dashboard;
    }
}