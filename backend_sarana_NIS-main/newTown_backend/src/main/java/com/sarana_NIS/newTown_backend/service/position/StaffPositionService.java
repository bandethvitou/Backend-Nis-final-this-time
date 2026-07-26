package com.sarana_NIS.newTown_backend.service.position;

import com.sarana_NIS.newTown_backend.dto.request.position.StaffPositionRequest;
import com.sarana_NIS.newTown_backend.dto.request.position.PositionAssignmentRequest;
import com.sarana_NIS.newTown_backend.dto.response.position.StaffPositionResponse;
import com.sarana_NIS.newTown_backend.dto.response.position.PositionHistoryResponse;
import com.sarana_NIS.newTown_backend.dto.response.position.PositionAssignmentResultResponse;
import com.sarana_NIS.newTown_backend.model.entity.position.Position;
import com.sarana_NIS.newTown_backend.model.entity.position.PositionHistory;
import com.sarana_NIS.newTown_backend.model.entity.position.StaffPosition;
import com.sarana_NIS.newTown_backend.model.entity.user.Staff;
import com.sarana_NIS.newTown_backend.model.enums.ActionType;
import com.sarana_NIS.newTown_backend.repository.PositionHistoryRepository;
import com.sarana_NIS.newTown_backend.repository.PositionRepository;
import com.sarana_NIS.newTown_backend.repository.StaffPositionRepository;
import com.sarana_NIS.newTown_backend.repository.StaffRepository;
import com.sarana_NIS.newTown_backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StaffPositionService {

    private static final Logger log = LoggerFactory.getLogger(StaffPositionService.class);

    private final StaffPositionRepository staffPositionRepository;
    private final PositionRepository positionRepository;
    private final StaffRepository staffRepository;
    private final UserRepository userRepository;
    private final PositionHistoryRepository positionHistoryRepository;

    public StaffPositionService(StaffPositionRepository staffPositionRepository, PositionRepository positionRepository,
                                StaffRepository staffRepository, UserRepository userRepository,
                                PositionHistoryRepository positionHistoryRepository) {
        this.staffPositionRepository = staffPositionRepository;
        this.positionRepository = positionRepository;
        this.staffRepository = staffRepository;
        this.userRepository = userRepository;
        this.positionHistoryRepository = positionHistoryRepository;
    }

    @Transactional
    public StaffPositionResponse assignPosition(StaffPositionRequest request) {
        Staff staff = staffRepository.findById(request.getStaffId())
                .orElseThrow(() -> new RuntimeException("Staff not found"));
        Position position = positionRepository.findById(request.getPositionId())
                .orElseThrow(() -> new RuntimeException("Position not found"));

        StaffPosition existing = staffPositionRepository.findByStaffAndPosition(
                request.getStaffId(), request.getPositionId());
        if (existing != null && existing.getIsActive()) {
            throw new RuntimeException("Staff already has this position");
        }

        StaffPosition staffPosition = new StaffPosition();
        staffPosition.setStaff(staff);
        staffPosition.setPosition(position);
        staffPosition.setIsTemporary(request.getIsTemporary() != null && request.getIsTemporary());
        staffPosition.setStartDate(request.getStartDate() != null ? request.getStartDate() : LocalDate.now());
        staffPosition.setEndDate(request.getEndDate());
        staffPosition.setIsActive(true);
        staffPosition.setNotes(request.getNotes());

        StaffPosition saved = staffPositionRepository.save(staffPosition);
        logPositionHistory(saved, ActionType.ASSIGNED, null);

        log.info("Position assigned: {} to staff {}", position.getName(), staff.getStaffId());
        return mapToResponse(saved);
    }

    @Transactional
    public PositionAssignmentResultResponse bulkAssignPositions(PositionAssignmentRequest request) {
        Staff staff = staffRepository.findById(request.getStaffId())
                .orElseThrow(() -> new RuntimeException("Staff not found"));

        PositionAssignmentResultResponse response = new PositionAssignmentResultResponse();
        response.setStaffId(staff.getId());
        response.setStaffName(staff.getUser() != null ? staff.getUser().getFullName() : staff.getStaffId());
        response.setSuccess(true);

        List<PositionAssignmentResultResponse.AssignedPosition> assigned = new ArrayList<>();
        List<PositionAssignmentResultResponse.RemovedPosition> removed = new ArrayList<>();
        List<String> warnings = new ArrayList<>();

        if (request.getRemovePositions() != null) {
            for (PositionAssignmentRequest.PositionRemoval removal : request.getRemovePositions()) {
                StaffPosition staffPosition = staffPositionRepository.findById(removal.getStaffPositionId())
                        .orElse(null);
                if (staffPosition != null && staffPosition.getStaff().getId().equals(staff.getId())) {
                    staffPosition.setIsActive(false);
                    staffPositionRepository.save(staffPosition);
                    logPositionHistory(staffPosition, ActionType.DEMOTED, removal.getReason());

                    PositionAssignmentResultResponse.RemovedPosition removedPos =
                            new PositionAssignmentResultResponse.RemovedPosition();
                    removedPos.setPositionId(staffPosition.getPosition().getId());
                    removedPos.setPositionName(staffPosition.getPosition().getName());
                    removedPos.setReason(removal.getReason());
                    removedPos.setStatus("REMOVED");
                    removed.add(removedPos);
                } else {
                    warnings.add("Position removal failed: ID " + removal.getStaffPositionId() + " not found");
                }
            }
        }

        if (request.getPositions() != null) {
            for (PositionAssignmentRequest.PositionAssignment assignment : request.getPositions()) {
                try {
                    Position position = positionRepository.findById(assignment.getPositionId())
                            .orElseThrow(() -> new RuntimeException("Position not found"));

                    StaffPosition staffPosition = new StaffPosition();
                    staffPosition.setStaff(staff);
                    staffPosition.setPosition(position);
                    staffPosition.setIsTemporary(assignment.getIsTemporary() != null && assignment.getIsTemporary());
                    staffPosition.setStartDate(assignment.getStartDate() != null ? assignment.getStartDate() : LocalDate.now());
                    staffPosition.setEndDate(assignment.getEndDate());
                    staffPosition.setIsActive(true);

                    StaffPosition saved = staffPositionRepository.save(staffPosition);
                    logPositionHistory(saved, ActionType.ASSIGNED, request.getReason());

                    PositionAssignmentResultResponse.AssignedPosition assignedPos =
                            new PositionAssignmentResultResponse.AssignedPosition();
                    assignedPos.setPositionId(position.getId());
                    assignedPos.setPositionName(position.getName());
                    assignedPos.setIsTemporary(staffPosition.getIsTemporary());
                    assignedPos.setStartDate(staffPosition.getStartDate());
                    assignedPos.setEndDate(staffPosition.getEndDate());
                    assignedPos.setStatus("ASSIGNED");
                    assigned.add(assignedPos);
                } catch (Exception e) {
                    warnings.add("Failed to assign position " + assignment.getPositionId() + ": " + e.getMessage());
                }
            }
        }

        response.setAssigned(assigned);
        response.setRemoved(removed);
        response.setWarnings(warnings);
        response.setMessage("Processed " + (assigned.size() + removed.size()) + " position changes");
        response.setSuccess(warnings.isEmpty());

        return response;
    }

    @Transactional
    public void removePosition(Long staffPositionId, String reason) {
        StaffPosition staffPosition = staffPositionRepository.findById(staffPositionId)
                .orElseThrow(() -> new RuntimeException("Staff position not found"));

        staffPosition.setIsActive(false);
        staffPositionRepository.save(staffPosition);
        logPositionHistory(staffPosition, ActionType.DEMOTED, reason);

        log.info("Position removed: {} from staff {}",
                staffPosition.getPosition().getName(),
                staffPosition.getStaff().getStaffId());
    }

    @Transactional
    public void handleTempPositions() {
        List<StaffPosition> expired = staffPositionRepository.findExpiredTemporary();

        for (StaffPosition sp : expired) {
            sp.setIsActive(false);
            staffPositionRepository.save(sp);
            logPositionHistory(sp, ActionType.EXPIRED, "Temporary position expired");
            log.info("Temporary position expired: {} for staff {}",
                    sp.getPosition().getName(),
                    sp.getStaff().getStaffId());
        }
    }

    public List<StaffPositionResponse> getStaffPositions(Long staffId) {
        List<StaffPosition> positions = staffPositionRepository.findByStaffId(staffId);
        return positions.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public List<PositionHistoryResponse> getPositionHistory(Long staffPositionId) {
        List<PositionHistory> history = positionHistoryRepository.findByStaffPositionId(staffPositionId);
        return history.stream().map(this::mapHistoryToResponse).collect(Collectors.toList());
    }

    private void logPositionHistory(StaffPosition staffPosition, ActionType action, String notes) {
        PositionHistory history = new PositionHistory();
        history.setStaffPosition(staffPosition);
        history.setAction(action);
        history.setChangedAt(LocalDateTime.now());
        history.setNotes(notes);
        positionHistoryRepository.save(history);
    }

    private StaffPositionResponse mapToResponse(StaffPosition staffPosition) {
        StaffPositionResponse response = new StaffPositionResponse();
        response.setId(staffPosition.getId());
        response.setStaffId(staffPosition.getStaff().getId());
        response.setStaffName(staffPosition.getStaff().getUser() != null ?
                staffPosition.getStaff().getUser().getFullName() : staffPosition.getStaff().getStaffId());
        response.setStaffCode(staffPosition.getStaff().getStaffId());
        response.setPositionId(staffPosition.getPosition().getId());
        response.setPositionName(staffPosition.getPosition().getName());
        response.setPositionCategory(staffPosition.getPosition().getCategory());
        response.setIsTemporary(staffPosition.getIsTemporary());
        response.setStartDate(staffPosition.getStartDate());
        response.setEndDate(staffPosition.getEndDate());
        response.setIsActive(staffPosition.getIsActive());
        response.setAssignedBy(staffPosition.getAssignedBy() != null ?
                staffPosition.getAssignedBy().getId().toString() : null);
        response.setNotes(staffPosition.getNotes());

        if (staffPosition.getIsActive()) {
            if (staffPosition.getIsTemporary() && staffPosition.getEndDate() != null) {
                LocalDate now = LocalDate.now();
                if (staffPosition.getEndDate().isBefore(now)) {
                    response.setStatus("EXPIRED");
                    response.setStatusColor("RED");
                    response.setDaysRemaining(0);
                } else {
                    int days = (int) java.time.temporal.ChronoUnit.DAYS.between(now, staffPosition.getEndDate());
                    response.setDaysRemaining(days);
                    if (days <= 7) {
                        response.setStatus("EXPIRING");
                        response.setStatusColor("ORANGE");
                    } else {
                        response.setStatus("ACTIVE");
                        response.setStatusColor("GREEN");
                    }
                }
            } else {
                response.setStatus("ACTIVE");
                response.setStatusColor("GREEN");
            }
        } else {
            response.setStatus("INACTIVE");
            response.setStatusColor("GREY");
        }

        return response;
    }

    private PositionHistoryResponse mapHistoryToResponse(PositionHistory history) {
        PositionHistoryResponse response = new PositionHistoryResponse();
        response.setId(history.getId());
        response.setStaffPositionId(history.getStaffPosition().getId());
        response.setStaffName(history.getStaffPosition().getStaff().getUser() != null ?
                history.getStaffPosition().getStaff().getUser().getFullName() :
                history.getStaffPosition().getStaff().getStaffId());
        response.setPositionName(history.getStaffPosition().getPosition().getName());
        response.setAction(history.getAction());
        response.setChangedAt(history.getChangedAt());
        response.setNotes(history.getNotes());
        return response;
    }
}