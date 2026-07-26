package com.sarana_NIS.newTown_backend.mapper.position;

import com.sarana_NIS.newTown_backend.dto.request.position.StaffPositionRequest;
import com.sarana_NIS.newTown_backend.dto.response.position.StaffPositionResponse;
import com.sarana_NIS.newTown_backend.dto.response.position.StaffPositionSummaryResponse;
import com.sarana_NIS.newTown_backend.dto.response.position.TempRoleStatusResponse;
import com.sarana_NIS.newTown_backend.model.entity.position.StaffPosition;
import com.sarana_NIS.newTown_backend.model.enums.TempRoleStatus;
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
public interface StaffPositionMapper {

    // ========== Entity → Response ==========
    @Mapping(target = "id", source = "id")
    @Mapping(target = "staffId", source = "staff.id")
    @Mapping(target = "staffName", source = "staff.user.fullName")
    @Mapping(target = "staffCode", source = "staff.staffId")
    @Mapping(target = "positionId", source = "position.id")
    @Mapping(target = "positionName", source = "position.name")
    @Mapping(target = "positionCategory", source = "position.category")
    @Mapping(target = "assignedBy", source = "assignedBy.fullName")
    @Mapping(target = "assignedByName", source = "assignedBy.fullName")
    @Mapping(target = "status", expression = "java(determineStatus(staffPosition))")
    @Mapping(target = "statusColor", expression = "java(getStatusColor(staffPosition))")
    @Mapping(target = "daysRemaining", expression = "java(calculateDaysRemaining(staffPosition))")
    StaffPositionResponse toResponse(StaffPosition staffPosition);

    // ========== Request → Entity ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "staff", ignore = true)
    @Mapping(target = "position", ignore = true)
    @Mapping(target = "assignedBy", ignore = true)
    @Mapping(target = "isActive", constant = "true")
    StaffPosition toEntity(StaffPositionRequest request);

    // ========== Update ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "staff", ignore = true)
    @Mapping(target = "position", ignore = true)
    @Mapping(target = "assignedBy", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    void updateEntity(StaffPositionRequest request, @MappingTarget StaffPosition staffPosition);

    // ========== Summary Response ==========
    default StaffPositionSummaryResponse toSummaryResponse(
            Long staffId,
            String staffName,
            String staffCode,
            List<StaffPosition> positions) {

        StaffPositionSummaryResponse response =
                new StaffPositionSummaryResponse(staffId, staffName, staffCode);
        response.setTotalPositions(positions != null ? positions.size() : 0);

        if (positions != null) {
            boolean hasTemp = false;
            int tempCount = 0;

            for (StaffPosition sp : positions) {
                StaffPositionSummaryResponse.PositionDetail detail =
                        new StaffPositionSummaryResponse.PositionDetail();
                detail.setPositionName(sp.getPosition() != null ?
                        sp.getPosition().getName() : null);
                detail.setCategory(sp.getPosition() != null ?
                        sp.getPosition().getCategory() : null);
                detail.setIsTemporary(sp.getIsTemporary());
                detail.setStartDate(sp.getStartDate());
                detail.setEndDate(sp.getEndDate());
                detail.setAssignedBy(sp.getAssignedBy() != null ?
                        sp.getAssignedBy().getFullName() : null);
                detail.setStatus(determineStatus(sp));
                detail.setStatusColor(getStatusColor(sp));
                detail.setDaysRemaining(calculateDaysRemaining(sp));

                if (sp.getIsActive() != null && sp.getIsActive()) {
                    response.getCurrentPositions().add(detail);
                } else {
                    response.getPreviousPositions().add(detail);
                }

                if (sp.getIsTemporary() != null && sp.getIsTemporary()) {
                    hasTemp = true;
                    tempCount++;
                }
            }

            response.setHasTemporaryPositions(hasTemp);
            response.setTempPositionsCount(tempCount);
        }

        return response;
    }

    // ========== Temp Role Status Response ==========
    default TempRoleStatusResponse toTempRoleStatusResponse(
            Long staffId,
            String staffName,
            List<StaffPosition> positions) {

        TempRoleStatusResponse response =
                new TempRoleStatusResponse(staffId, staffName);

        if (positions != null) {
            int expiringCount = 0;
            int expiredCount = 0;

            for (StaffPosition sp : positions) {
                if (sp.getIsTemporary() != null && sp.getIsTemporary()) {
                    TempRoleStatusResponse.TempPositionDetail detail =
                            new TempRoleStatusResponse.TempPositionDetail();
                    detail.setStaffPositionId(sp.getId());
                    detail.setPositionName(sp.getPosition() != null ?
                            sp.getPosition().getName() : null);
                    detail.setStartDate(sp.getStartDate());
                    detail.setEndDate(sp.getEndDate());

                    // Determine status
                    TempRoleStatus status = determineTempStatus(sp);
                    detail.setStatus(status);
                    detail.setStatusColor(getTempStatusColor(status));
                    detail.setDaysRemaining(calculateDaysRemaining(sp));

                    // Determine action required
                    if (status == TempRoleStatus.EXPIRED) {
                        detail.setActionRequired("Review and update position");
                        expiredCount++;
                    } else if (status == TempRoleStatus.ACTIVE) {
                        Integer days = calculateDaysRemaining(sp);
                        if (days != null && days < 30) {
                            detail.setActionRequired("Position expiring soon - consider renewal");
                            expiringCount++;
                        } else {
                            detail.setActionRequired("Monitor position");
                        }
                    }

                    response.getPositions().add(detail);
                }
            }

            response.setExpiringCount(expiringCount);
            response.setExpiredCount(expiredCount);

            // Overall status
            if (expiredCount > 0) {
                response.setOverallStatus(TempRoleStatus.EXPIRED);
                response.setNextAction("Review expired temporary positions");
            } else if (expiringCount > 0) {
                response.setOverallStatus(TempRoleStatus.ACTIVE);
                response.setNextAction("Renew expiring positions");
            } else {
                response.setOverallStatus(TempRoleStatus.ACTIVE);
                response.setNextAction("All positions are active");
            }
        }

        return response;
    }

    // ========== Helper Methods ==========

    default String determineStatus(StaffPosition staffPosition) {
        if (staffPosition == null) return "UNKNOWN";
        if (staffPosition.getIsActive() == null || !staffPosition.getIsActive()) {
            return "INACTIVE";
        }
        if (staffPosition.getIsTemporary() != null && staffPosition.getIsTemporary()) {
            if (staffPosition.getEndDate() != null &&
                    staffPosition.getEndDate().isBefore(LocalDate.now())) {
                return "EXPIRED";
            }
            return "TEMPORARY";
        }
        return "ACTIVE";
    }

    default String getStatusColor(StaffPosition staffPosition) {
        if (staffPosition == null) return "#6B7280";
        String status = determineStatus(staffPosition);
        switch (status) {
            case "ACTIVE": return "#10B981";
            case "TEMPORARY": return "#3B82F6";
            case "EXPIRED": return "#EF4444";
            case "INACTIVE": return "#6B7280";
            default: return "#6B7280";
        }
    }

    default Integer calculateDaysRemaining(StaffPosition staffPosition) {
        if (staffPosition == null || staffPosition.getEndDate() == null) {
            return null;
        }
        if (staffPosition.getIsActive() == null || !staffPosition.getIsActive()) {
            return null;
        }
        return (int) java.time.temporal.ChronoUnit.DAYS.between(
                LocalDate.now(),
                staffPosition.getEndDate()
        );
    }

    default TempRoleStatus determineTempStatus(StaffPosition staffPosition) {
        if (staffPosition == null || staffPosition.getIsTemporary() == null ||
                !staffPosition.getIsTemporary()) {
            return null;
        }
        if (staffPosition.getEndDate() == null) {
            return TempRoleStatus.ACTIVE;
        }
        if (staffPosition.getEndDate().isBefore(LocalDate.now())) {
            return TempRoleStatus.EXPIRED;
        }
        return TempRoleStatus.ACTIVE;
    }

    default String getTempStatusColor(TempRoleStatus status) {
        if (status == null) return "#6B7280";
        switch (status) {
            case ACTIVE: return "#10B981";
            case EXPIRED: return "#EF4444";
            default: return "#6B7280";
        }
    }
}