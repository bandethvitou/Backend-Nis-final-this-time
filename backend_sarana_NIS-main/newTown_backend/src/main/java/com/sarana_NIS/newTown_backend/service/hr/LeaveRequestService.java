package com.sarana_NIS.newTown_backend.service.hr;

import com.sarana_NIS.newTown_backend.dto.request.hr.LeaveRequestDTO;
import com.sarana_NIS.newTown_backend.dto.request.hr.BulkLeaveRequest;
import com.sarana_NIS.newTown_backend.dto.request.hr.LeaveApprovalRequest;
import com.sarana_NIS.newTown_backend.dto.response.hr.LeaveBalanceResponse;
import com.sarana_NIS.newTown_backend.dto.response.hr.LeaveResponse;
import com.sarana_NIS.newTown_backend.model.entity.core.User;
import com.sarana_NIS.newTown_backend.model.entity.user.LeaveRequest;
import com.sarana_NIS.newTown_backend.model.entity.user.Staff;
import com.sarana_NIS.newTown_backend.model.enums.LeaveStatus;
import com.sarana_NIS.newTown_backend.model.enums.LeaveType;
import com.sarana_NIS.newTown_backend.repository.LeaveRequestRepository;
import com.sarana_NIS.newTown_backend.repository.StaffRepository;
import com.sarana_NIS.newTown_backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LeaveRequestService {

    private static final Logger log = LoggerFactory.getLogger(LeaveRequestService.class);

    private final LeaveRequestRepository leaveRequestRepository;
    private final StaffRepository staffRepository;
    private final UserRepository userRepository;

    public LeaveRequestService(LeaveRequestRepository leaveRequestRepository, StaffRepository staffRepository,
                               UserRepository userRepository) {
        this.leaveRequestRepository = leaveRequestRepository;
        this.staffRepository = staffRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public LeaveResponse createLeaveRequest(LeaveRequestDTO request) {
        Staff staff = staffRepository.findById(request.getStaffId())
                .orElseThrow(() -> new RuntimeException("Staff not found"));

        List<LeaveRequest> existing = leaveRequestRepository.findByStaffAndDateRange(
                request.getStaffId(), request.getStartDate(), request.getEndDate());
        if (!existing.isEmpty()) {
            throw new RuntimeException("Staff already has a leave request in this period");
        }

        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setStaff(staff);
        leaveRequest.setLeaveType(request.getLeaveType());
        leaveRequest.setStartDate(request.getStartDate());
        leaveRequest.setEndDate(request.getEndDate());
        leaveRequest.setReason(request.getReason());
        leaveRequest.setStatus(LeaveStatus.PENDING);
        leaveRequest.calculateDays();

        LeaveRequest saved = leaveRequestRepository.save(leaveRequest);
        log.info("Leave request created for staff: {}", staff.getStaffId());
        return mapToResponse(saved);
    }

    @Transactional
    public List<LeaveResponse> createBulkLeaveRequests(BulkLeaveRequest request) {
        List<Staff> staffList = staffRepository.findAllById(request.getStaffIds());
        List<LeaveRequest> leaveRequests = new ArrayList<>();

        for (Staff staff : staffList) {
            List<LeaveRequest> existing = leaveRequestRepository.findByStaffAndDateRange(
                    staff.getId(), request.getStartDate(), request.getEndDate());
            if (existing.isEmpty()) {
                LeaveRequest leaveRequest = new LeaveRequest();
                leaveRequest.setStaff(staff);
                leaveRequest.setLeaveType(request.getLeaveType());
                leaveRequest.setStartDate(request.getStartDate());
                leaveRequest.setEndDate(request.getEndDate());
                leaveRequest.setReason(request.getReason());
                leaveRequest.setStatus(LeaveStatus.PENDING);
                leaveRequest.calculateDays();
                leaveRequests.add(leaveRequest);
            } else {
                log.warn("Staff {} already has a leave request in this period", staff.getStaffId());
            }
        }

        List<LeaveRequest> saved = leaveRequestRepository.saveAll(leaveRequests);
        log.info("Bulk leave requests created for {} staff", saved.size());
        return saved.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Transactional
    public LeaveResponse approveLeave(LeaveApprovalRequest request) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(request.getLeaveId())
                .orElseThrow(() -> new RuntimeException("Leave request not found"));

        switch (request.getAction().toUpperCase()) {
            case "APPROVE":
                leaveRequest.setStatus(LeaveStatus.APPROVED);
                break;
            case "REJECT":
            case "DENY":
                leaveRequest.setStatus(LeaveStatus.REJECTED);
                leaveRequest.setDenialReason(request.getReason());
                break;
            case "CANCEL":
                leaveRequest.setStatus(LeaveStatus.CANCELLED);
                break;
            default:
                throw new RuntimeException("Invalid action: " + request.getAction());
        }

        LeaveRequest saved = leaveRequestRepository.save(leaveRequest);
        log.info("Leave request {}: {}", request.getAction(), leaveRequest.getId());
        return mapToResponse(saved);
    }

    @Transactional
    public LeaveResponse approveLeaveByManager(Long leaveId, Long managerId, String notes) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave request not found"));
        User manager = userRepository.findById(managerId)
                .orElseThrow(() -> new RuntimeException("Manager not found"));

        leaveRequest.setStatus(LeaveStatus.APPROVED);
        leaveRequest.setApprovedBy(manager);
        leaveRequest.setApprovedDate(LocalDate.now());

        LeaveRequest saved = leaveRequestRepository.save(leaveRequest);
        log.info("Leave request {} approved by manager: {}", leaveId, manager.getUsername());
        return mapToResponse(saved);
    }

    @Transactional
    public LeaveResponse rejectLeave(Long leaveId, String reason) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave request not found"));

        leaveRequest.setStatus(LeaveStatus.REJECTED);
        leaveRequest.setDenialReason(reason);

        LeaveRequest saved = leaveRequestRepository.save(leaveRequest);
        log.info("Leave request {} rejected", leaveId);
        return mapToResponse(saved);
    }

    @Transactional
    public LeaveResponse cancelLeaveRequest(Long leaveId) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave request not found"));

        leaveRequest.setStatus(LeaveStatus.CANCELLED);

        LeaveRequest saved = leaveRequestRepository.save(leaveRequest);
        log.info("Leave request {} cancelled", leaveId);
        return mapToResponse(saved);
    }

    public LeaveResponse getLeaveRequestById(Long leaveId) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave request not found"));
        return mapToResponse(leaveRequest);
    }

    public List<LeaveResponse> getAllLeaveRequests() {
        List<LeaveRequest> leaves = leaveRequestRepository.findAll();
        return leaves.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public List<LeaveResponse> getPendingLeaveRequests() {
        List<LeaveRequest> leaves = leaveRequestRepository.findPendingApproval();
        return leaves.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public List<LeaveResponse> getLeaveRequestsByStaff(Long staffId) {
        List<LeaveRequest> leaves = leaveRequestRepository.findByStaffId(staffId);
        return leaves.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public List<LeaveResponse> getLeaveRequestsByStaffAndStatus(Long staffId, LeaveStatus status) {
        List<LeaveRequest> leaves = leaveRequestRepository.findByStaffAndStatus(staffId, status);
        return leaves.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public List<LeaveResponse> getLeaveRequestsByStatus(LeaveStatus status) {
        List<LeaveRequest> leaves = leaveRequestRepository.findByStatus(status);
        return leaves.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public List<LeaveResponse> getLeaveRequestsByDateRange(LocalDate startDate, LocalDate endDate) {
        List<LeaveRequest> leaves = leaveRequestRepository.findByDateRange(startDate, endDate);
        return leaves.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public List<LeaveResponse> getActiveLeavesOnDate(LocalDate date) {
        List<LeaveRequest> leaves = leaveRequestRepository.findActiveLeavesOnDate(date);
        return leaves.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public List<LeaveResponse> getStaffOnLeaveToday() {
        List<LeaveRequest> leaves = leaveRequestRepository.findActiveLeavesOnDate(LocalDate.now());
        return leaves.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public LeaveBalanceResponse getLeaveBalance(Long staffId) {
        Staff staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new RuntimeException("Staff not found"));

        LeaveBalanceResponse response = new LeaveBalanceResponse();
        response.setStaffId(staffId);
        response.setStaffName(staff.getUser() != null ? staff.getUser().getFullName() : staff.getStaffId());
        response.setYear(String.valueOf(LocalDate.now().getYear()));

        Map<String, LeaveBalanceResponse.LeaveTypeBalance> balances = new HashMap<>();
        LeaveType[] leaveTypes = {LeaveType.ANNUAL, LeaveType.SICK, LeaveType.OTHER, LeaveType.UNPAID};
        int[] allottedDays = {15, 10, 5, 3};

        int totalUsed = 0;
        int totalRemaining = 0;
        int totalPending = 0;

        for (int i = 0; i < leaveTypes.length; i++) {
            Integer used = leaveRequestRepository.countApprovedLeavesByStaffAndType(
                    staffId, leaveTypes[i].name(), LocalDate.now().getYear());
            Integer pending = leaveRequestRepository.countPendingByStaffAndType(
                    staffId, leaveTypes[i].name(), LocalDate.now().getYear());

            if (used == null) used = 0;
            if (pending == null) pending = 0;

            LeaveBalanceResponse.LeaveTypeBalance balance = new LeaveBalanceResponse.LeaveTypeBalance();
            balance.setType(leaveTypes[i].getDisplayName());
            balance.setAllotted(allottedDays[i]);
            balance.setUsed(used);
            balance.setRemaining(allottedDays[i] - used);
            balance.setPending(pending);

            if (balance.getRemaining() < 0) {
                balance.setStatus("EXCEEDED");
            } else if (balance.getRemaining() < 3) {
                balance.setStatus("WARNING");
            } else {
                balance.setStatus("GOOD");
            }

            balances.put(leaveTypes[i].getDisplayName(), balance);
            totalUsed += used;
            totalRemaining += balance.getRemaining();
            totalPending += pending;
        }

        response.setBalances(balances);
        response.setTotalDaysUsed(totalUsed);
        response.setTotalDaysRemaining(totalRemaining);
        response.setPendingDays(totalPending);

        return response;
    }

    public List<LeaveBalanceResponse> getAllStaffLeaveBalances() {
        List<Staff> staffList = staffRepository.findAllActiveStaff();
        return staffList.stream()
                .map(staff -> getLeaveBalance(staff.getId()))
                .collect(Collectors.toList());
    }

    public Map<String, Object> getLeaveStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalRequests", leaveRequestRepository.count());
        stats.put("pendingRequests", leaveRequestRepository.countPendingLeaves());
        stats.put("approvedRequests", leaveRequestRepository.countByStatus(LeaveStatus.APPROVED));
        stats.put("rejectedRequests", leaveRequestRepository.countByStatus(LeaveStatus.REJECTED));
        stats.put("cancelledRequests", leaveRequestRepository.countByStatus(LeaveStatus.CANCELLED));
        stats.put("staffOnLeave", leaveRequestRepository.countActiveLeavesOnDate(LocalDate.now()));
        return stats;
    }

    public Map<String, Integer> getLeaveRequestsByType() {
        List<Object[]> results = leaveRequestRepository.countByLeaveType();
        Map<String, Integer> stats = new HashMap<>();
        for (Object[] result : results) {
            stats.put(result[0].toString(), ((Number) result[1]).intValue());
        }
        return stats;
    }

    public Map<String, Integer> getLeaveRequestsByMonth() {
        List<Object[]> results = leaveRequestRepository.countByMonth();
        Map<String, Integer> stats = new HashMap<>();
        for (Object[] result : results) {
            stats.put(result[0].toString(), ((Number) result[1]).intValue());
        }
        return stats;
    }

    @Transactional
    public void deleteLeaveRequest(Long leaveId) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave request not found"));

        if (leaveRequest.getStatus() == LeaveStatus.APPROVED) {
            throw new RuntimeException("Cannot delete an approved leave request");
        }

        leaveRequestRepository.delete(leaveRequest);
        log.info("Leave request deleted: {}", leaveId);
    }

    @Transactional
    public void deleteAllLeaveRequestsByStaff(Long staffId) {
        List<LeaveRequest> leaves = leaveRequestRepository.findByStaffId(staffId);
        leaveRequestRepository.deleteAll(leaves);
        log.info("All leave requests deleted for staff: {}", staffId);
    }

    public boolean hasOverlappingLeave(Long staffId, LocalDate startDate, LocalDate endDate) {
        List<LeaveRequest> existing = leaveRequestRepository.findByStaffAndDateRange(staffId, startDate, endDate);
        return !existing.isEmpty();
    }

    public boolean isStaffOnLeave(Long staffId, LocalDate date) {
        List<LeaveRequest> leaves = leaveRequestRepository.findActiveLeavesOnDate(date);
        return leaves.stream().anyMatch(l -> l.getStaff().getId().equals(staffId));
    }

    private LeaveResponse mapToResponse(LeaveRequest leaveRequest) {
        LeaveResponse response = new LeaveResponse();
        response.setId(leaveRequest.getId());
        response.setStaffId(leaveRequest.getStaff().getId());
        response.setStaffName(leaveRequest.getStaff().getUser() != null ?
                leaveRequest.getStaff().getUser().getFullName() : leaveRequest.getStaff().getStaffId());
        response.setStaffCode(leaveRequest.getStaff().getStaffId());
        response.setLeaveType(leaveRequest.getLeaveType());
        response.setStartDate(leaveRequest.getStartDate());
        response.setEndDate(leaveRequest.getEndDate());
        response.setDays(leaveRequest.getDays());
        response.setReason(leaveRequest.getReason());
        response.setStatus(leaveRequest.getStatus());
        response.setApprovedBy(leaveRequest.getApprovedBy() != null ?
                leaveRequest.getApprovedBy().getUsername() : null);
        if (leaveRequest.getApprovedBy() != null) {
            response.setApprovedByName(leaveRequest.getApprovedBy().getFullName());
        }
        response.setDenialReason(leaveRequest.getDenialReason());

        if (leaveRequest.getStatus() == LeaveStatus.PENDING) {
            response.setStatusColor("ORANGE");
        } else if (leaveRequest.getStatus() == LeaveStatus.APPROVED) {
            response.setStatusColor("GREEN");
        } else if (leaveRequest.getStatus() == LeaveStatus.REJECTED) {
            response.setStatusColor("RED");
        } else {
            response.setStatusColor("GREY");
        }

        return response;
    }
}