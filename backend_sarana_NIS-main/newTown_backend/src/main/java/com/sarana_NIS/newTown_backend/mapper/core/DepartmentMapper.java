package com.sarana_NIS.newTown_backend.mapper.core;

import com.sarana_NIS.newTown_backend.dto.request.core.DepartmentRequest;
import com.sarana_NIS.newTown_backend.dto.response.core.DepartmentResponse;
import com.sarana_NIS.newTown_backend.dto.response.core.DepartmentWithStaffResponse;
import com.sarana_NIS.newTown_backend.model.entity.management.Department;  // ADD THIS IMPORT
import com.sarana_NIS.newTown_backend.model.entity.position.StaffPosition;  // ADD THIS IMPORT
import com.sarana_NIS.newTown_backend.model.entity.user.Staff;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface DepartmentMapper {

    // Entity → Response
    @Mapping(target = "headUserName", expression = "java(getHeadUserName(department))")
    @Mapping(target = "headUserEmail", expression = "java(getHeadUserEmail(department))")
    @Mapping(target = "staffCount", expression = "java(department.getStaff() != null ? department.getStaff().size() : 0)")
    @Mapping(target = "positions", expression = "java(getDistinctPositions(department))")
    @Mapping(target = "staff", expression = "java(mapStaff(department.getStaff()))")
    DepartmentResponse toResponse(Department department);

    // DepartmentWithStaffResponse
    @Mapping(target = "department", expression = "java(toResponse(department))")
    @Mapping(target = "staff", expression = "java(mapStaffDetail(department.getStaff()))")
    @Mapping(target = "totalStaff", expression = "java(department.getStaff() != null ? department.getStaff().size() : 0)")
    @Mapping(target = "activeStaff", expression = "java(countActiveStaff(department.getStaff()))")
    @Mapping(target = "onLeaveStaff", expression = "java(countOnLeaveStaff(department.getStaff()))")
    DepartmentWithStaffResponse toDepartmentWithStaffResponse(Department department);

    // Request → Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "staff", ignore = true)
    Department toEntity(DepartmentRequest request);

    // Update
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "staff", ignore = true)
    void updateEntity(DepartmentRequest request, @MappingTarget Department department);

    // ========== Helper Methods ==========

    default String getHeadUserName(Department department) {
        if (department == null || department.getHeadUserId() == null) return null;
        return "Head User"; // You'll need to fetch user by ID
    }

    default String getHeadUserEmail(Department department) {
        if (department == null || department.getHeadUserId() == null) return null;
        return "head@school.com"; // You'll need to fetch user by ID
    }

    default List<String> getDistinctPositions(Department department) {
        if (department == null || department.getStaff() == null) return new ArrayList<>();
        return department.getStaff().stream()
                .filter(s -> s.getStaffPositions() != null)
                .flatMap(s -> s.getStaffPositions().stream())
                .map(StaffPosition::getPosition)
                .filter(p -> p != null)
                .map(p -> p.getName())
                .distinct()
                .collect(Collectors.toList());
    }

    default List<DepartmentResponse.StaffSummary> mapStaff(List<Staff> staff) {
        if (staff == null) return new ArrayList<>();
        return staff.stream()
                .map(s -> {
                    DepartmentResponse.StaffSummary summary = new DepartmentResponse.StaffSummary();
                    summary.setId(s.getId());
                    summary.setName(s.getUser() != null ? s.getUser().getFullName() : null);
                    summary.setEmail(s.getUser() != null ? s.getUser().getEmail() : null);
                    summary.setPhone(s.getUser() != null ? s.getUser().getPhone() : null);

                    // Get primary position
                    if (s.getStaffPositions() != null && !s.getStaffPositions().isEmpty()) {
                        StaffPosition primaryPosition = s.getStaffPositions().get(0);
                        if (primaryPosition.getPosition() != null) {
                            summary.setPosition(primaryPosition.getPosition().getName());
                        }
                    }
                    return summary;
                })
                .collect(Collectors.toList());
    }

    default List<DepartmentWithStaffResponse.StaffDetail> mapStaffDetail(List<Staff> staff) {
        if (staff == null) return new ArrayList<>();
        return staff.stream()
                .map(s -> {
                    DepartmentWithStaffResponse.StaffDetail detail = new DepartmentWithStaffResponse.StaffDetail();
                    detail.setId(s.getId());
                    detail.setStaffId(s.getStaffId());
                    detail.setFullName(s.getUser() != null ? s.getUser().getFullName() : null);
                    detail.setEmail(s.getUser() != null ? s.getUser().getEmail() : null);
                    detail.setPhone(s.getUser() != null ? s.getUser().getPhone() : null);
                    detail.setShift(s.getShift() != null ? s.getShift().name() : null);
                    detail.setStatus(s.getStaffStatus() != null ? s.getStaffStatus().name() : null);
                    detail.setJoinDate(s.getJoinDate() != null ? s.getJoinDate().toString() : null);

                    // Get all positions
                    if (s.getStaffPositions() != null) {
                        List<String> positionNames = s.getStaffPositions().stream()
                                .map(StaffPosition::getPosition)
                                .filter(p -> p != null)
                                .map(p -> p.getName())
                                .collect(Collectors.toList());
                        detail.setPositions(positionNames);
                        if (!positionNames.isEmpty()) {
                            detail.setPosition(positionNames.get(0));
                        }
                    }
                    return detail;
                })
                .collect(Collectors.toList());
    }

    default int countActiveStaff(List<Staff> staff) {
        if (staff == null) return 0;
        return (int) staff.stream()
                .filter(s -> s.getStaffStatus() != null && s.getStaffStatus().name().equals("ACTIVE"))
                .count();
    }

    default int countOnLeaveStaff(List<Staff> staff) {
        if (staff == null) return 0;
        return (int) staff.stream()
                .filter(s -> s.getStaffStatus() != null && s.getStaffStatus().name().equals("ON_LEAVE"))
                .count();
    }
}