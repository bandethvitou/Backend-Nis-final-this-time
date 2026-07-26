package com.sarana_NIS.newTown_backend.mapper.hr;

import com.sarana_NIS.newTown_backend.dto.request.hr.LeaveRequestDTO;
import com.sarana_NIS.newTown_backend.dto.response.hr.LeaveBalanceResponse;
import com.sarana_NIS.newTown_backend.dto.response.hr.LeaveResponse;
import com.sarana_NIS.newTown_backend.dto.response.hr.LeaveSummaryResponse;
import com.sarana_NIS.newTown_backend.model.entity.user.LeaveRequest;
import com.sarana_NIS.newTown_backend.model.enums.LeaveStatus;
import com.sarana_NIS.newTown_backend.model.enums.LeaveType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface LeaveRequestMapper {

    // ========== Entity → Response ==========
    @Mapping(target = "id", source = "id")
    @Mapping(target = "staffId", source = "staff.id")
    @Mapping(target = "staffName", source = "staff.user.fullName")
    @Mapping(target = "staffCode", source = "staff.staffId")
    @Mapping(target = "approvedBy", source = "approvedBy.fullName")
    @Mapping(target = "approvedByName", source = "approvedBy.fullName")
    @Mapping(target = "statusColor", expression = "java(getStatusColor(leaveRequest.getStatus()))")
    @Mapping(target = "statusLabel", expression = "java(getStatusLabel(leaveRequest.getStatus()))")
    LeaveResponse toResponse(LeaveRequest leaveRequest);

    // ========== Request → Entity ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "staff", ignore = true)
    @Mapping(target = "approvedBy", ignore = true)
    @Mapping(target = "status", constant = "PENDING")
    @Mapping(target = "denialReason", ignore = true)
    @Mapping(target = "days", expression = "java(calculateDays(request))")
    @Mapping(target = "approvedDate", ignore = true)
    LeaveRequest toEntity(LeaveRequestDTO request);

    // ========== Update ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "staff", ignore = true)
    @Mapping(target = "approvedBy", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "denialReason", ignore = true)
    @Mapping(target = "days", ignore = true)
    @Mapping(target = "approvedDate", ignore = true)
    void updateEntity(LeaveRequestDTO request, @MappingTarget LeaveRequest leaveRequest);

    // ========== Balance Mapping ==========
    default LeaveBalanceResponse toBalanceResponse(
            Long staffId,
            String staffName,
            String year,
            List<LeaveRequest> requests) {

        LeaveBalanceResponse response = new LeaveBalanceResponse(staffId, staffName);
        response.setYear(year);

        // Group by leave type
        requests.stream()
                .filter(r -> r.getStatus() == LeaveStatus.APPROVED)
                .collect(Collectors.groupingBy(LeaveRequest::getLeaveType))
                .forEach((type, list) -> {
                    int used = list.stream().mapToInt(r -> r.getDays() != null ? r.getDays() : 0).sum();
                    LeaveBalanceResponse.LeaveTypeBalance balance =
                            new LeaveBalanceResponse.LeaveTypeBalance(
                                    type.name(),
                                    20, // allotted days (configurable per type)
                                    used
                            );
                    balance.setRemaining(20 - used);
                    balance.setPending((int) requests.stream()
                            .filter(r -> r.getLeaveType() == type && r.getStatus() == LeaveStatus.PENDING)
                            .count());
                    response.getBalances().put(type.name(), balance);
                });

        // Calculate totals
        int totalUsed = response.getBalances().values().stream()
                .mapToInt(LeaveBalanceResponse.LeaveTypeBalance::getUsed)
                .sum();
        int totalRemaining = response.getBalances().values().stream()
                .mapToInt(LeaveBalanceResponse.LeaveTypeBalance::getRemaining)
                .sum();
        int totalPending = response.getBalances().values().stream()
                .mapToInt(LeaveBalanceResponse.LeaveTypeBalance::getPending)
                .sum();

        response.setTotalDaysUsed(totalUsed);
        response.setTotalDaysRemaining(totalRemaining);
        response.setPendingDays(totalPending);

        return response;
    }

    // ========== Summary Mapping ==========
    default LeaveSummaryResponse toSummaryResponse(List<LeaveRequest> requests) {
        if (requests == null) return new LeaveSummaryResponse();

        LeaveSummaryResponse summary = new LeaveSummaryResponse();
        summary.setTotalRequests(requests.size());

        long pending = requests.stream()
                .filter(r -> r.getStatus() != null && r.getStatus() == LeaveStatus.PENDING)
                .count();
        long approved = requests.stream()
                .filter(r -> r.getStatus() != null && r.getStatus() == LeaveStatus.APPROVED)
                .count();
        long denied = requests.stream()
                .filter(r -> r.getStatus() != null && r.getStatus() == LeaveStatus.REJECTED)
                .count();

        summary.setPendingRequests((int) pending);
        summary.setApprovedRequests((int) approved);
        summary.setDeniedRequests((int) denied);

        // Staff on leave today
        LocalDate today = LocalDate.now();
        long onLeave = requests.stream()
                .filter(r -> r.getStatus() == LeaveStatus.APPROVED)
                .filter(r -> r.isActiveOnDate(today))
                .count();
        summary.setStaffOnLeave((int) onLeave);

        // By type summary
        requests.stream()
                .collect(Collectors.groupingBy(LeaveRequest::getLeaveType))
                .forEach((type, list) -> {
                    LeaveSummaryResponse.LeaveTypeSummary typeSummary =
                            new LeaveSummaryResponse.LeaveTypeSummary();
                    typeSummary.setType(type != null ? type.name() : "UNKNOWN");
                    typeSummary.setCount(list.size());
                    typeSummary.setDaysUsed(list.stream()
                            .filter(r -> r.getStatus() == LeaveStatus.APPROVED)
                            .mapToInt(r -> r.getDays() != null ? r.getDays() : 0)
                            .sum());
                    summary.getByType().add(typeSummary);
                });

        // Upcoming leaves (next 7 days)
        LocalDate nextWeek = today.plusDays(7);
        requests.stream()
                .filter(r -> r.getStatus() == LeaveStatus.APPROVED)
                .filter(r -> !r.getStartDate().isBefore(today) && !r.getStartDate().isAfter(nextWeek))
                .forEach(r -> {
                    LeaveSummaryResponse.UpcomingLeave upcoming =
                            new LeaveSummaryResponse.UpcomingLeave();
                    upcoming.setId(r.getId());
                    upcoming.setStaffName(r.getStaff() != null && r.getStaff().getUser() != null ?
                            r.getStaff().getUser().getFullName() : null);
                    upcoming.setDepartment(r.getStaff() != null && r.getStaff().getDepartment() != null ?
                            r.getStaff().getDepartment().getName() : null);
                    upcoming.setType(r.getLeaveType() != null ? r.getLeaveType().name() : null);
                    upcoming.setStartDate(r.getStartDate());
                    upcoming.setEndDate(r.getEndDate());
                    upcoming.setDays(r.getDays());
                    summary.getUpcomingLeaves().add(upcoming);
                });

        return summary;
    }

    // ========== Helper Methods ==========

    default Integer calculateDays(LeaveRequestDTO request) {
        if (request == null || request.getStartDate() == null || request.getEndDate() == null) {
            return 0;
        }
        return (int) java.time.temporal.ChronoUnit.DAYS.between(
                request.getStartDate(),
                request.getEndDate()
        ) + 1;
    }

    default String getStatusColor(LeaveStatus status) {
        if (status == null) return "#6B7280";
        switch (status) {
            case PENDING: return "#F59E0B";
            case APPROVED: return "#10B981";
            case REJECTED: return "#EF4444";
            case CANCELLED: return "#6B7280";
            default: return "#6B7280";
        }
    }

    default String getStatusLabel(LeaveStatus status) {
        if (status == null) return "Unknown";
        switch (status) {
            case PENDING: return "Pending Approval";
            case APPROVED: return "Approved";
            case REJECTED: return "Rejected";
            case CANCELLED: return "Cancelled";
            default: return status.name();
        }
    }
}