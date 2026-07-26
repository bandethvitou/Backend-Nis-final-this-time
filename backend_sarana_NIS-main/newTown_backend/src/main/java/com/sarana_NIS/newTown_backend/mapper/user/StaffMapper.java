package com.sarana_NIS.newTown_backend.mapper.user;

import com.sarana_NIS.newTown_backend.dto.request.user.StaffCreateRequest;
import com.sarana_NIS.newTown_backend.dto.request.user.StaffUpdateRequest;
import com.sarana_NIS.newTown_backend.dto.response.user.StaffResponse;
import com.sarana_NIS.newTown_backend.dto.response.user.StaffResponse.DepartmentInfo;
import com.sarana_NIS.newTown_backend.dto.response.user.StaffResponse.PositionInfo;
import com.sarana_NIS.newTown_backend.model.entity.management.Department;
import com.sarana_NIS.newTown_backend.model.entity.position.StaffPosition;
import com.sarana_NIS.newTown_backend.model.entity.user.Staff;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface StaffMapper {

    // ========== Entity → Response ==========
    @Mapping(target = "user", expression = "java(mapUser(staff.getUser()))")
    @Mapping(target = "department", expression = "java(mapDepartment(staff.getDepartment()))")
    @Mapping(target = "positions", expression = "java(mapPositions(staff.getStaffPositions()))")
    @Mapping(target = "totalSalary", expression = "java(calculateTotalSalary(staff))")
    @Mapping(target = "shiftDisplay", expression = "java(getShiftDisplay(staff.getShift()))")
    @Mapping(target = "leaveDaysUsed", expression = "java(calculateLeaveDaysUsed(staff))")
    @Mapping(target = "leaveDaysRemaining", expression = "java(calculateLeaveDaysRemaining(staff))")
    @Mapping(target = "leaveDaysTotal", constant = "20")
    StaffResponse toResponse(Staff staff);

    // ========== Request → Entity ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "staffPositions", ignore = true)
    @Mapping(target = "leaveRequests", ignore = true)
    // ❌ REMOVE THIS - "salaries" doesn't exist in Staff entity
    // @Mapping(target = "salaries", ignore = true)
    @Mapping(target = "staffStatus", constant = "ACTIVE")
    Staff toEntity(StaffCreateRequest request);

    // ========== Update ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "staffId", ignore = true)
    @Mapping(target = "staffPositions", ignore = true)
    @Mapping(target = "leaveRequests", ignore = true)
    // ❌ REMOVE THIS - "salaries" doesn't exist in Staff entity
    // @Mapping(target = "salaries", ignore = true)
    void updateEntity(StaffUpdateRequest request, @MappingTarget Staff staff);

    // ========== Helper Methods ==========

    // Map User
    default com.sarana_NIS.newTown_backend.dto.response.user.UserResponse mapUser(com.sarana_NIS.newTown_backend.model.entity.core.User user) {
        if (user == null) return null;
        com.sarana_NIS.newTown_backend.dto.response.user.UserResponse response =
                new com.sarana_NIS.newTown_backend.dto.response.user.UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setFullName(user.getFullName());
        response.setPhone(user.getPhone());
        response.setAvatarUrl(user.getAvatarUrl());
        response.setUserStatus(user.getUserStatus() != null ? user.getUserStatus().name() : null);
        response.setIsEmailVerified(user.getIsEmailVerified());
        response.setLastLogin(user.getLastLogin());
        return response;
    }

    // Map Department
    default DepartmentInfo mapDepartment(Department department) {
        if (department == null) return null;
        DepartmentInfo info = new DepartmentInfo();
        info.setId(department.getId());
        info.setName(department.getName());
        info.setHeadName(null);
        return info;
    }

    // Map Positions
    default List<PositionInfo> mapPositions(List<StaffPosition> positions) {
        if (positions == null) return new java.util.ArrayList<>();
        return positions.stream().map(p -> {
            PositionInfo info = new PositionInfo();
            info.setId(p.getId());
            info.setName(p.getPosition() != null ? p.getPosition().getName() : null);
            info.setIsTemporary(p.getIsTemporary());
            info.setStartDate(p.getStartDate());
            info.setEndDate(p.getEndDate());
            info.setIsActive(p.getIsActive());
            return info;
        }).collect(Collectors.toList());
    }

    // Calculate Total Salary
    default BigDecimal calculateTotalSalary(Staff staff) {
        if (staff == null) return BigDecimal.ZERO;
        BigDecimal base = staff.getBaseSalary() != null ? staff.getBaseSalary() : BigDecimal.ZERO;
        BigDecimal allowances = staff.getAllowances() != null ? staff.getAllowances() : BigDecimal.ZERO;
        return base.add(allowances);
    }

    // Get Shift Display
    default String getShiftDisplay(com.sarana_NIS.newTown_backend.model.enums.Shift shift) {
        if (shift == null) return null;
        switch (shift) {
            case MORNING: return "Morning (7:00 AM - 12:00 PM)";
            case AFTERNOON: return "Afternoon (1:00 PM - 5:00 PM)";
            case EVENING: return "Evening (5:00 PM - 9:00 PM)";
            case FULL_DAY: return "Full Day (7:00 AM - 5:00 PM)";
            default: return shift.name();
        }
    }

    // Calculate Leave Days Used
    default Integer calculateLeaveDaysUsed(Staff staff) {
        if (staff == null || staff.getLeaveRequests() == null) return 0;
        return staff.getLeaveRequests().stream()
                .filter(l -> l.getStatus() != null && l.getStatus().name().equals("APPROVED"))
                .mapToInt(l -> l.getDays() != null ? l.getDays() : 0)
                .sum();
    }

    // Calculate Leave Days Remaining
    default Integer calculateLeaveDaysRemaining(Staff staff) {
        if (staff == null) return 0;
        int used = calculateLeaveDaysUsed(staff);
        return Math.max(20 - used, 0);
    }
}