package com.sarana_NIS.newTown_backend.mapper.it;

import com.sarana_NIS.newTown_backend.dto.request.it.MaintenanceRequest;
import com.sarana_NIS.newTown_backend.dto.response.it.MaintenanceResponse;
import com.sarana_NIS.newTown_backend.dto.response.it.MaintenanceScheduleResponse;
import com.sarana_NIS.newTown_backend.model.entity.it.Maintenance;
import com.sarana_NIS.newTown_backend.model.enums.MaintenanceStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.time.LocalDate;
import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface MaintenanceMapper {

    // ========== Entity → Response ==========
    @Mapping(target = "id", source = "id")
    @Mapping(target = "equipmentId", source = "equipment.id")
    @Mapping(target = "equipmentName", source = "equipment.name")
    @Mapping(target = "equipmentCategory", source = "equipment.category")
    @Mapping(target = "maintenanceType", source = "maintenanceType")
    @Mapping(target = "issue", source = "issue")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "maintenanceDate", source = "maintenanceDate")
    @Mapping(target = "scheduledDate", source = "scheduledDate")
    @Mapping(target = "completedDate", source = "completedDate")
    @Mapping(target = "technician", source = "technician")
    @Mapping(target = "actionTaken", source = "actionTaken")
    @Mapping(target = "cost", source = "cost")
    @Mapping(target = "priority", source = "priority")
    @Mapping(target = "statusColor", expression = "java(getStatusColor(maintenance.getStatus()))")
    @Mapping(target = "isOverdue", expression = "java(maintenance.isOverdue())")
    MaintenanceResponse toResponse(Maintenance maintenance);

    // ========== Request → Entity ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "equipment", ignore = true)
    @Mapping(target = "status", constant = "SCHEDULED")
    @Mapping(target = "scheduledDate", expression = "java(java.time.LocalDate.now())")
    @Mapping(target = "completedDate", ignore = true)
    @Mapping(target = "actionTaken", ignore = true)
    @Mapping(target = "reportedBy", ignore = true)
    Maintenance toEntity(MaintenanceRequest request);

    // ========== Update ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "equipment", ignore = true)
    @Mapping(target = "scheduledDate", ignore = true)
    @Mapping(target = "completedDate", ignore = true)
    @Mapping(target = "reportedBy", ignore = true)
    void updateEntity(MaintenanceRequest request, @MappingTarget Maintenance maintenance);

    // ========== Schedule Response ==========
    default MaintenanceScheduleResponse toScheduleResponse(List<Maintenance> maintenanceList) {
        if (maintenanceList == null) return new MaintenanceScheduleResponse();

        MaintenanceScheduleResponse response = new MaintenanceScheduleResponse();

        int scheduledToday = 0, inProgress = 0, completed = 0, overdue = 0;
        LocalDate today = LocalDate.now();

        for (Maintenance m : maintenanceList) {
            if (m.getStatus() != null) {
                switch (m.getStatus()) {
                    case SCHEDULED:
                        if (m.getMaintenanceDate() != null &&
                                m.getMaintenanceDate().equals(today)) {
                            scheduledToday++;
                        }
                        if (m.isOverdue()) {
                            overdue++;
                        }
                        break;
                    case IN_PROGRESS:
                        inProgress++;
                        break;
                    case COMPLETED:
                        completed++;
                        break;
                    default:
                        break;
                }
            }
        }

        response.setScheduledToday(scheduledToday);
        response.setInProgress(inProgress);
        response.setCompleted(completed);
        response.setOverdue(overdue);

        // Tasks list
        maintenanceList.stream()
                .filter(m -> m.getStatus() != null &&
                        (m.getStatus() == MaintenanceStatus.SCHEDULED ||
                                m.getStatus() == MaintenanceStatus.IN_PROGRESS))
                .sorted((m1, m2) -> {
                    if (m1.getMaintenanceDate() == null) return 1;
                    if (m2.getMaintenanceDate() == null) return -1;
                    return m1.getMaintenanceDate().compareTo(m2.getMaintenanceDate());
                })
                .forEach(m -> {
                    MaintenanceScheduleResponse.MaintenanceTask task =
                            new MaintenanceScheduleResponse.MaintenanceTask();
                    task.setId(m.getId());
                    task.setEquipmentName(m.getEquipment() != null ?
                            m.getEquipment().getName() : "Unknown");
                    task.setIssue(m.getIssue());
                    task.setStatus(m.getStatus() != null ? m.getStatus().name() : "UNKNOWN");
                    task.setMaintenanceDate(m.getMaintenanceDate());
                    task.setTechnician(m.getTechnician());
                    task.setPriority(m.getPriority());
                    response.getTasks().add(task);
                });

        return response;
    }

    // ========== Helper Methods ==========

    default String getStatusColor(MaintenanceStatus status) {
        if (status == null) return "#6B7280";
        switch (status) {
            case SCHEDULED: return "#3B82F6";
            case IN_PROGRESS: return "#F59E0B";
            case COMPLETED: return "#10B981";
            case CANCELLED: return "#EF4444";
            case ON_HOLD: return "#8B5CF6";
            default: return "#6B7280";
        }
    }
}