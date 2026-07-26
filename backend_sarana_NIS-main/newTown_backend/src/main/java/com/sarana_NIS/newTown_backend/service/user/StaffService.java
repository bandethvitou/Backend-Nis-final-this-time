package com.sarana_NIS.newTown_backend.service.user;

import com.sarana_NIS.newTown_backend.dto.request.user.StaffCreateRequest;
import com.sarana_NIS.newTown_backend.dto.request.user.StaffUpdateRequest;
import com.sarana_NIS.newTown_backend.dto.response.user.StaffResponse;
import com.sarana_NIS.newTown_backend.dto.response.user.StaffPositionSummaryResponse;
import com.sarana_NIS.newTown_backend.dto.response.common.PageResponse;
import com.sarana_NIS.newTown_backend.model.entity.core.User;
import com.sarana_NIS.newTown_backend.model.entity.management.Department;  // ADD THIS IMPORT
import com.sarana_NIS.newTown_backend.model.entity.position.Position;
import com.sarana_NIS.newTown_backend.model.entity.position.StaffPosition;
import com.sarana_NIS.newTown_backend.model.entity.user.Staff;
import com.sarana_NIS.newTown_backend.model.enums.EmployeeStatus;
import com.sarana_NIS.newTown_backend.repository.DepartmentRepository;
import com.sarana_NIS.newTown_backend.repository.PositionRepository;
import com.sarana_NIS.newTown_backend.repository.StaffPositionRepository;
import com.sarana_NIS.newTown_backend.repository.StaffRepository;
import com.sarana_NIS.newTown_backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StaffService {

    private static final Logger log = LoggerFactory.getLogger(StaffService.class);

    private final StaffRepository staffRepository;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final PositionRepository positionRepository;
    private final StaffPositionRepository staffPositionRepository;

    public StaffService(StaffRepository staffRepository, UserRepository userRepository,
                        DepartmentRepository departmentRepository, PositionRepository positionRepository,
                        StaffPositionRepository staffPositionRepository) {
        this.staffRepository = staffRepository;
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
        this.positionRepository = positionRepository;
        this.staffPositionRepository = staffPositionRepository;
    }

    @Transactional
    public StaffResponse createStaff(StaffCreateRequest request) {
        if (staffRepository.findByStaffId(request.getStaffId()).isPresent()) {
            throw new RuntimeException("Staff ID already exists: " + request.getStaffId());
        }

        Staff staff = new Staff();
        staff.setStaffId(request.getStaffId());
        staff.setBaseSalary(request.getBaseSalary());
        staff.setAllowances(request.getAllowances());
        staff.setShift(request.getShift());
        staff.setJoinDate(request.getJoinDate());
        staff.setStaffStatus(EmployeeStatus.ACTIVE);

        if (request.getUserId() != null) {
            User user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            staff.setUser(user);
        }

        if (request.getDepartmentId() != null) {
            Department department = departmentRepository.findById(request.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Department not found"));
            staff.setDepartment(department);
        }

        Staff savedStaff = staffRepository.save(staff);

        if (request.getPositionIds() != null) {
            for (Long positionId : request.getPositionIds()) {
                Position position = positionRepository.findById(positionId)
                        .orElseThrow(() -> new RuntimeException("Position not found: " + positionId));
                StaffPosition staffPosition = new StaffPosition();
                staffPosition.setStaff(savedStaff);
                staffPosition.setPosition(position);
                staffPosition.setIsTemporary(false);
                staffPosition.setStartDate(LocalDate.now());
                staffPosition.setIsActive(true);
                staffPositionRepository.save(staffPosition);
            }
        }

        if (request.getTempPositions() != null) {
            for (StaffCreateRequest.TempPositionRequest tempReq : request.getTempPositions()) {
                Position position = positionRepository.findById(tempReq.getPositionId())
                        .orElseThrow(() -> new RuntimeException("Position not found"));
                StaffPosition staffPosition = new StaffPosition();
                staffPosition.setStaff(savedStaff);
                staffPosition.setPosition(position);
                staffPosition.setIsTemporary(true);
                staffPosition.setStartDate(tempReq.getStartDate());
                staffPosition.setEndDate(tempReq.getEndDate());
                staffPosition.setIsActive(true);
                staffPositionRepository.save(staffPosition);
            }
        }

        log.info("Staff created: {} ({})", savedStaff.getUser() != null ? savedStaff.getUser().getFullName() : savedStaff.getStaffId(), savedStaff.getStaffId());
        return mapToResponse(savedStaff);
    }

    @Transactional
    public StaffResponse updateStaff(Long staffId, StaffUpdateRequest request) {
        Staff staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new RuntimeException("Staff not found"));

        if (request.getDepartmentId() != null) {
            Department department = departmentRepository.findById(request.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Department not found"));
            staff.setDepartment(department);
        }
        if (request.getBaseSalary() != null) staff.setBaseSalary(request.getBaseSalary());
        if (request.getAllowances() != null) staff.setAllowances(request.getAllowances());
        if (request.getShift() != null) staff.setShift(request.getShift());
        if (request.getJoinDate() != null) staff.setJoinDate(request.getJoinDate());
        if (request.getStaffStatus() != null) staff.setStaffStatus(request.getStaffStatus());

        Staff updatedStaff = staffRepository.save(staff);
        log.info("Staff updated: {}", updatedStaff.getStaffId());
        return mapToResponse(updatedStaff);
    }

    public StaffResponse getStaffById(Long staffId) {
        Staff staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new RuntimeException("Staff not found"));
        return mapToResponse(staff);
    }

    public StaffResponse getStaffByStaffId(String staffId) {
        Staff staff = staffRepository.findByStaffId(staffId)
                .orElseThrow(() -> new RuntimeException("Staff not found with ID: " + staffId));
        return mapToResponse(staff);
    }

    public PageResponse<StaffResponse> getAllStaff(Pageable pageable) {
        Page<Staff> page = staffRepository.findAll(pageable);
        return mapToPageResponse(page);
    }

    public List<StaffResponse> getStaffByDepartment(Long departmentId) {
        List<Staff> staff = staffRepository.findByDepartmentId(departmentId);
        return staff.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public StaffPositionSummaryResponse getStaffPositions(Long staffId) {
        Staff staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new RuntimeException("Staff not found"));

        StaffPositionSummaryResponse response = new StaffPositionSummaryResponse();
        response.setStaffId(staff.getId());
        response.setStaffName(staff.getUser() != null ? staff.getUser().getFullName() : staff.getStaffId());
        response.setStaffCode(staff.getStaffId());

        List<StaffPosition> positions = staffPositionRepository.findByStaffId(staffId);

        List<StaffPositionSummaryResponse.PositionDetail> currentPositions = new ArrayList<>();
        List<StaffPositionSummaryResponse.PositionDetail> previousPositions = new ArrayList<>();

        for (StaffPosition sp : positions) {
            StaffPositionSummaryResponse.PositionDetail detail = new StaffPositionSummaryResponse.PositionDetail();
            detail.setPositionName(sp.getPosition().getName());
            detail.setCategory(sp.getPosition().getCategory());
            detail.setIsTemporary(sp.getIsTemporary());
            detail.setStartDate(sp.getStartDate());
            detail.setEndDate(sp.getEndDate());
            detail.setAssignedBy(sp.getAssignedBy() != null ? sp.getAssignedBy().getFullName() : null);

            if (sp.getIsActive()) {
                if (sp.getIsTemporary() && sp.getEndDate() != null) {
                    LocalDate now = LocalDate.now();
                    if (sp.getEndDate().isBefore(now)) {
                        detail.setStatus("EXPIRED");
                        detail.setStatusColor("RED");
                        detail.setDaysRemaining(0);
                    } else {
                        int days = (int) java.time.temporal.ChronoUnit.DAYS.between(now, sp.getEndDate());
                        detail.setDaysRemaining(days);
                        if (days <= 7) {
                            detail.setStatus("EXPIRING");
                            detail.setStatusColor("ORANGE");
                        } else {
                            detail.setStatus("ACTIVE");
                            detail.setStatusColor("GREEN");
                        }
                    }
                } else {
                    detail.setStatus("ACTIVE");
                    detail.setStatusColor("GREEN");
                }
                currentPositions.add(detail);
            } else {
                detail.setStatus("INACTIVE");
                previousPositions.add(detail);
            }
        }

        response.setCurrentPositions(currentPositions);
        response.setPreviousPositions(previousPositions);
        response.setTotalPositions(positions.size());
        response.setHasTemporaryPositions(currentPositions.stream().anyMatch(StaffPositionSummaryResponse.PositionDetail::getIsTemporary));
        response.setTempPositionsCount((int) currentPositions.stream().filter(StaffPositionSummaryResponse.PositionDetail::getIsTemporary).count());

        return response;
    }

    @Transactional
    public void deleteStaff(Long staffId) {
        Staff staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new RuntimeException("Staff not found"));

        List<StaffPosition> positions = staffPositionRepository.findByStaffId(staffId);
        for (StaffPosition sp : positions) {
            sp.setIsActive(false);
        }
        staffPositionRepository.saveAll(positions);

        staff.setStaffStatus(EmployeeStatus.INACTIVE);
        staffRepository.save(staff);
        log.info("Staff deactivated: {}", staff.getStaffId());
    }

    private StaffResponse mapToResponse(Staff staff) {
        StaffResponse response = new StaffResponse();
        response.setId(staff.getId());
        response.setStaffId(staff.getStaffId());

        if (staff.getUser() != null) {
            response.setUser(UserService.mapToUserResponse(staff.getUser()));
        }

        if (staff.getDepartment() != null) {
            StaffResponse.DepartmentInfo deptInfo = new StaffResponse.DepartmentInfo();
            deptInfo.setId(staff.getDepartment().getId());
            deptInfo.setName(staff.getDepartment().getName());
            deptInfo.setHeadName(staff.getDepartment().getHeadUserId() != null ?
                    staff.getDepartment().getHeadUserId().toString() : null);
            response.setDepartment(deptInfo);
        }

        response.setBaseSalary(staff.getBaseSalary());
        response.setAllowances(staff.getAllowances());
        if (staff.getBaseSalary() != null && staff.getAllowances() != null) {
            response.setTotalSalary(staff.getBaseSalary().add(staff.getAllowances()));
        } else if (staff.getBaseSalary() != null) {
            response.setTotalSalary(staff.getBaseSalary());
        }
        response.setShift(staff.getShift());
        response.setShiftDisplay(staff.getShift() != null ? staff.getShift().name() : null);
        response.setJoinDate(staff.getJoinDate());
        response.setStaffStatus(staff.getStaffStatus());

        List<StaffResponse.PositionInfo> positions = staffPositionRepository.findActiveByStaffId(staff.getId())
                .stream()
                .map(sp -> {
                    StaffResponse.PositionInfo info = new StaffResponse.PositionInfo();
                    info.setId(sp.getId());
                    info.setName(sp.getPosition().getName());
                    info.setIsTemporary(sp.getIsTemporary());
                    info.setStartDate(sp.getStartDate());
                    info.setEndDate(sp.getEndDate());
                    info.setIsActive(sp.getIsActive());
                    return info;
                })
                .collect(Collectors.toList());
        response.setPositions(positions);

        return response;
    }

    private PageResponse<StaffResponse> mapToPageResponse(Page<Staff> page) {
        PageResponse<StaffResponse> response = new PageResponse<>();
        response.setContent(page.getContent().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList()));
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLast(page.isLast());
        response.setFirst(page.isFirst());
        return response;
    }
}