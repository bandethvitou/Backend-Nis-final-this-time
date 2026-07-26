
package com.sarana_NIS.newTown_backend.service.core;

import com.sarana_NIS.newTown_backend.dto.request.core.DepartmentRequest;
import com.sarana_NIS.newTown_backend.dto.response.core.DepartmentResponse;
import com.sarana_NIS.newTown_backend.dto.response.core.DepartmentWithStaffResponse;
import com.sarana_NIS.newTown_backend.model.entity.core.User;
import com.sarana_NIS.newTown_backend.model.entity.management.Department;
import com.sarana_NIS.newTown_backend.repository.DepartmentRepository;
import com.sarana_NIS.newTown_backend.repository.StaffRepository;
import com.sarana_NIS.newTown_backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    private static final Logger log = LoggerFactory.getLogger(DepartmentService.class);

    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;
    private final StaffRepository staffRepository;

    public DepartmentService(DepartmentRepository departmentRepository, UserRepository userRepository,
                             StaffRepository staffRepository) {
        this.departmentRepository = departmentRepository;
        this.userRepository = userRepository;
        this.staffRepository = staffRepository;
    }

    @Transactional
    public DepartmentResponse createDepartment(DepartmentRequest request) {
        if (departmentRepository.existsByName(request.getName())) {
            throw new RuntimeException("Department already exists: " + request.getName());
        }

        Department department = new Department();
        department.setName(request.getName());
        department.setDescription(request.getDescription());

        if (request.getHeadUserId() != null) {
            User head = userRepository.findById(request.getHeadUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            department.setHeadUserId(request.getHeadUserId());
        }

        Department saved = departmentRepository.save(department);
        log.info("Department created: {}", saved.getName());
        return mapToResponse(saved);
    }

    @Transactional
    public DepartmentResponse updateDepartment(Long departmentId, DepartmentRequest request) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        if (request.getName() != null) department.setName(request.getName());
        if (request.getDescription() != null) department.setDescription(request.getDescription());
        if (request.getHeadUserId() != null) department.setHeadUserId(request.getHeadUserId());

        Department updated = departmentRepository.save(department);
        log.info("Department updated: {}", updated.getName());
        return mapToResponse(updated);
    }

    public DepartmentResponse getDepartmentById(Long departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found"));
        return mapToResponse(department);
    }

    public DepartmentResponse getDepartmentByName(String name) {
        Department department = departmentRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Department not found: " + name));
        return mapToResponse(department);
    }

    public List<DepartmentResponse> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return departments.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public DepartmentWithStaffResponse getDepartmentWithStaff(Long departmentId) {
        Department department = departmentRepository.findByIdWithStaff(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        DepartmentWithStaffResponse response = new DepartmentWithStaffResponse();
        response.setDepartment(mapToResponse(department));

        List<DepartmentWithStaffResponse.StaffDetail> staffDetails = department.getStaff().stream()
                .map(staff -> {
                    DepartmentWithStaffResponse.StaffDetail detail = new DepartmentWithStaffResponse.StaffDetail();
                    detail.setId(staff.getId());
                    detail.setStaffId(staff.getStaffId());
                    detail.setFullName(staff.getUser() != null ? staff.getUser().getFullName() : staff.getStaffId());
                    detail.setEmail(staff.getUser() != null ? staff.getUser().getEmail() : null);
                    detail.setPhone(staff.getUser() != null ? staff.getUser().getPhone() : null);
                    detail.setShift(staff.getShift() != null ? staff.getShift().name() : null);
                    detail.setStatus(staff.getStaffStatus() != null ? staff.getStaffStatus().name() : null);
                    detail.setJoinDate(staff.getJoinDate() != null ? staff.getJoinDate().toString() : null);
                    return detail;
                })
                .collect(Collectors.toList());

        response.setStaff(staffDetails);
        response.setTotalStaff(staffDetails.size());

        long active = staffDetails.stream().filter(s -> "ACTIVE".equals(s.getStatus())).count();
        response.setActiveStaff((int) active);

        return response;
    }

    public List<Object[]> getDepartmentsWithStaffCount() {
        return departmentRepository.findDepartmentsWithStaffCount();
    }

    public long countStaffByDepartment(Long departmentId) {
        return departmentRepository.countStaffByDepartmentId(departmentId);
    }

    @Transactional
    public void deleteDepartment(Long departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        long staffCount = departmentRepository.countStaffByDepartmentId(departmentId);
        if (staffCount > 0) {
            throw new RuntimeException("Cannot delete department with assigned staff");
        }

        departmentRepository.delete(department);
        log.info("Department deleted: {}", department.getName());
    }

    private DepartmentResponse mapToResponse(Department department) {
        DepartmentResponse response = new DepartmentResponse();
        response.setId(department.getId());
        response.setName(department.getName());
        response.setDescription(department.getDescription());
        response.setHeadUserId(department.getHeadUserId());

        if (department.getHeadUserId() != null) {
            userRepository.findById(department.getHeadUserId()).ifPresent(head -> {
                response.setHeadUserName(head.getFullName());
                response.setHeadUserEmail(head.getEmail());
            });
        }

        long staffCount = departmentRepository.countStaffByDepartmentId(department.getId());
        response.setStaffCount((int) staffCount);

        response.setCreatedAt(department.getCreatedAt());
        response.setCreatedBy(department.getCreatedBy() != null ? department.getCreatedBy().toString() : null);
        response.setUpdatedAt(department.getUpdatedAt());
        response.setUpdatedBy(department.getUpdatedBy() != null ? department.getUpdatedBy().toString() : null);

        return response;
    }
}