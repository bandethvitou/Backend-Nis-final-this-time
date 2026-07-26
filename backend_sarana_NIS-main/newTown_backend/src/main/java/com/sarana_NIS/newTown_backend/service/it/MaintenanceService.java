package com.sarana_NIS.newTown_backend.service.it;

import com.sarana_NIS.newTown_backend.dto.request.it.MaintenanceStatusRequest;
import com.sarana_NIS.newTown_backend.dto.response.it.MaintenanceScheduleResponse;
import com.sarana_NIS.newTown_backend.model.entity.it.Equipment;
import com.sarana_NIS.newTown_backend.model.entity.it.Maintenance;
import com.sarana_NIS.newTown_backend.model.enums.EquipmentStatus;
import com.sarana_NIS.newTown_backend.model.enums.MaintenanceStatus;
import com.sarana_NIS.newTown_backend.repository.EquipmentRepository;
import com.sarana_NIS.newTown_backend.repository.MaintenanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MaintenanceService {

    private static final Logger log = LoggerFactory.getLogger(MaintenanceService.class);

    private final MaintenanceRepository maintenanceRepository;
    private final EquipmentRepository equipmentRepository;

    public MaintenanceService(MaintenanceRepository maintenanceRepository, EquipmentRepository equipmentRepository) {
        this.maintenanceRepository = maintenanceRepository;
        this.equipmentRepository = equipmentRepository;
    }

    @Transactional
    public Maintenance createMaintenance(Maintenance maintenance) {
        Equipment equipment = equipmentRepository.findById(maintenance.getEquipment().getId())
                .orElseThrow(() -> new RuntimeException("Equipment not found"));

        maintenance.setEquipment(equipment);
        maintenance.setStatus(MaintenanceStatus.SCHEDULED);

        Maintenance saved = maintenanceRepository.save(maintenance);
        log.info("Maintenance record created for equipment: {}", equipment.getName());
        return saved;
    }

    @Transactional
    public Maintenance updateMaintenanceStatus(MaintenanceStatusRequest request) {
        Maintenance maintenance = maintenanceRepository.findById(request.getMaintenanceId())
                .orElseThrow(() -> new RuntimeException("Maintenance record not found"));

        MaintenanceStatus newStatus = MaintenanceStatus.valueOf(request.getStatus());
        maintenance.setStatus(newStatus);

        if (request.getActionTaken() != null) maintenance.setActionTaken(request.getActionTaken());
        // ✅ Fix: Convert BigDecimal to Double
        if (request.getCost() != null) maintenance.setCost(request.getCost());
        if (request.getTechnician() != null) maintenance.setTechnician(request.getTechnician());
        // ✅ Fix: Maintenance doesn't have notes field - use description or ignore
        // if (request.getNotes() != null) maintenance.setNotes(request.getNotes());

        Maintenance saved = maintenanceRepository.save(maintenance);
        log.info("Maintenance status updated: {} -> {}", request.getMaintenanceId(), newStatus);
        return saved;
    }

    @Transactional
    public Maintenance markInProgress(Long maintenanceId) {
        Maintenance maintenance = maintenanceRepository.findById(maintenanceId)
                .orElseThrow(() -> new RuntimeException("Maintenance record not found"));
        maintenance.setStatus(MaintenanceStatus.IN_PROGRESS);
        Maintenance saved = maintenanceRepository.save(maintenance);
        log.info("Maintenance marked in progress: {}", maintenanceId);
        return saved;
    }

    @Transactional
    public Maintenance markCompleted(Long maintenanceId, String actionTaken, Double cost) {
        Maintenance maintenance = maintenanceRepository.findById(maintenanceId)
                .orElseThrow(() -> new RuntimeException("Maintenance record not found"));

        maintenance.setStatus(MaintenanceStatus.COMPLETED);
        maintenance.setActionTaken(actionTaken);
        // ✅ Fix: Convert BigDecimal to Double
        if (cost != null) {
            maintenance.setCost(cost);
        }
        maintenance.setMaintenanceDate(LocalDate.now());

        Maintenance saved = maintenanceRepository.save(maintenance);

        Equipment equipment = maintenance.getEquipment();
        if (equipment.getStatus() == EquipmentStatus.UNDER_REPAIR) {
            equipment.setStatus(EquipmentStatus.FUNCTIONAL);
            equipmentRepository.save(equipment);
        }

        log.info("Maintenance marked completed: {}", maintenanceId);
        return saved;
    }

    public Maintenance getMaintenanceById(Long maintenanceId) {
        return maintenanceRepository.findById(maintenanceId)
                .orElseThrow(() -> new RuntimeException("Maintenance record not found"));
    }

    public List<Maintenance> getMaintenanceByEquipment(Long equipmentId) {
        return maintenanceRepository.findByEquipmentId(equipmentId);
    }

    public List<Maintenance> getMaintenanceByStatus(MaintenanceStatus status) {
        return maintenanceRepository.findByStatus(status);
    }

    public List<Maintenance> getActiveMaintenance() {
        return maintenanceRepository.findActiveMaintenance();
    }

    public List<Maintenance> getMaintenanceByDateRange(LocalDate startDate, LocalDate endDate) {
        return maintenanceRepository.findByMaintenanceDateBetween(startDate, endDate);
    }

    public List<Maintenance> getOverdueMaintenance() {
        return maintenanceRepository.findOverdueMaintenance(LocalDate.now());
    }

    public List<Maintenance> getLatestByEquipment(Long equipmentId) {
        return maintenanceRepository.findLatestByEquipmentId(equipmentId);
    }

    public MaintenanceScheduleResponse getMaintenanceSchedule() {
        MaintenanceScheduleResponse response = new MaintenanceScheduleResponse();

        List<Maintenance> todayMaintenance = maintenanceRepository
                .findByMaintenanceDateBetween(LocalDate.now(), LocalDate.now());
        response.setScheduledToday(todayMaintenance.size());

        Long inProgressCount = maintenanceRepository.countInProgressMaintenance();
        response.setInProgress(inProgressCount.intValue());

        Long completedCount = maintenanceRepository.countCompletedMaintenance();
        response.setCompleted(completedCount.intValue());

        List<Maintenance> overdueMaintenance = maintenanceRepository.findOverdueMaintenance(LocalDate.now());
        response.setOverdue(overdueMaintenance.size());

        List<MaintenanceScheduleResponse.MaintenanceTask> tasks = new ArrayList<>();
        List<Maintenance> allMaintenance = maintenanceRepository.findAll();
        for (Maintenance m : allMaintenance) {
            MaintenanceScheduleResponse.MaintenanceTask task = new MaintenanceScheduleResponse.MaintenanceTask();
            task.setId(m.getId());
            task.setEquipmentName(m.getEquipment().getName());
            task.setIssue(m.getIssue());
            task.setStatus(m.getStatus().name());
            task.setMaintenanceDate(m.getMaintenanceDate());
            task.setTechnician(m.getTechnician());
            task.setPriority("MEDIUM");
            tasks.add(task);
        }
        response.setTasks(tasks);

        return response;
    }

    public Map<String, Object> getMaintenanceStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("total", maintenanceRepository.count());
        stats.put("scheduled", maintenanceRepository.countScheduledMaintenance());
        stats.put("inProgress", maintenanceRepository.countInProgressMaintenance());
        stats.put("completed", maintenanceRepository.countCompletedMaintenance());

        Double avgCost = maintenanceRepository.findAverageMaintenanceCost();
        stats.put("averageCost", avgCost != null ? avgCost : 0.0);

        return stats;
    }

    @Transactional
    public void deleteMaintenance(Long maintenanceId) {
        Maintenance maintenance = maintenanceRepository.findById(maintenanceId)
                .orElseThrow(() -> new RuntimeException("Maintenance record not found"));
        maintenanceRepository.delete(maintenance);
        log.info("Maintenance record deleted: {}", maintenanceId);
    }
}