package com.sarana_NIS.newTown_backend.service.it;

import com.sarana_NIS.newTown_backend.dto.request.it.EquipmentAssignmentRequest;
import com.sarana_NIS.newTown_backend.dto.request.it.EquipmentSearchRequest;
import com.sarana_NIS.newTown_backend.dto.response.it.EquipmentStatusResponse;
import com.sarana_NIS.newTown_backend.dto.response.common.PageResponse;
import com.sarana_NIS.newTown_backend.model.entity.core.User;
import com.sarana_NIS.newTown_backend.model.entity.it.Equipment;
import com.sarana_NIS.newTown_backend.model.enums.EquipmentStatus;
import com.sarana_NIS.newTown_backend.repository.EquipmentRepository;
import com.sarana_NIS.newTown_backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EquipmentService {

    private static final Logger log = LoggerFactory.getLogger(EquipmentService.class);

    private final EquipmentRepository equipmentRepository;
    private final UserRepository userRepository;

    public EquipmentService(EquipmentRepository equipmentRepository, UserRepository userRepository) {
        this.equipmentRepository = equipmentRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Equipment createEquipment(Equipment equipment) {
        if (equipment.getSerialNumber() != null) {
            equipmentRepository.findBySerialNumber(equipment.getSerialNumber())
                    .ifPresent(existing -> {
                        throw new RuntimeException("Equipment with serial number already exists: " +
                                equipment.getSerialNumber());
                    });
        }

        equipment.setStatus(EquipmentStatus.AVAILABLE); // Changed from FUNCTIONAL
        Equipment saved = equipmentRepository.save(equipment);
        log.info("Equipment created: {}", saved.getName());
        return saved;
    }

    @Transactional
    public Equipment updateEquipment(Long equipmentId, Equipment updates) {
        Equipment equipment = equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new RuntimeException("Equipment not found"));

        if (updates.getName() != null) equipment.setName(updates.getName());
        if (updates.getCategory() != null) equipment.setCategory(updates.getCategory());
        if (updates.getModel() != null) equipment.setModel(updates.getModel());
        if (updates.getSerialNumber() != null) equipment.setSerialNumber(updates.getSerialNumber());
        if (updates.getLocation() != null) equipment.setLocation(updates.getLocation());
        if (updates.getStatus() != null) equipment.setStatus(updates.getStatus());
        if (updates.getPurchaseDate() != null) equipment.setPurchaseDate(updates.getPurchaseDate());
        if (updates.getWarrantyExpiry() != null) equipment.setWarrantyExpiry(updates.getWarrantyExpiry());
        if (updates.getNotes() != null) equipment.setNotes(updates.getNotes());

        Equipment saved = equipmentRepository.save(equipment);
        log.info("Equipment updated: {}", equipmentId);
        return saved;
    }

    @Transactional
    public Equipment updateEquipmentStatus(Long equipmentId, EquipmentStatus status) {
        Equipment equipment = equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new RuntimeException("Equipment not found"));
        equipment.setStatus(status);
        Equipment saved = equipmentRepository.save(equipment);
        log.info("Equipment status updated: {} -> {}", equipmentId, status);
        return saved;
    }

    @Transactional
    public Equipment assignEquipment(EquipmentAssignmentRequest request) {
        Equipment equipment = equipmentRepository.findById(request.getEquipmentId())
                .orElseThrow(() -> new RuntimeException("Equipment not found"));

        User assignedTo = userRepository.findById(request.getAssignedToUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        equipment.setAssignedTo(assignedTo); // Actually set the assignment
        equipment.setLocation(request.getLocation());
        equipment.setNotes(request.getNotes());
        equipment.setStatus(EquipmentStatus.ASSIGNED);

        Equipment saved = equipmentRepository.save(equipment);
        log.info("Equipment assigned to user: {}", request.getAssignedToUserId());
        return saved;
    }

    public Equipment getEquipmentById(Long equipmentId) {
        return equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new RuntimeException("Equipment not found"));
    }

    public Equipment getEquipmentBySerialNumber(String serialNumber) {
        return equipmentRepository.findBySerialNumber(serialNumber)
                .orElseThrow(() -> new RuntimeException("Equipment not found with serial: " + serialNumber));
    }

    public List<Equipment> getEquipmentByStatus(EquipmentStatus status) {
        return equipmentRepository.findByStatus(status);
    }

    public List<Equipment> getFunctionalEquipment() {
        return equipmentRepository.findByStatus(EquipmentStatus.FUNCTIONAL);
    }

    public List<Equipment> getUnderRepairEquipment() {
        return equipmentRepository.findByStatus(EquipmentStatus.UNDER_REPAIR);
    }

    public List<Equipment> getEquipmentByCategory(String category) {
        return equipmentRepository.findByCategory(category);
    }

    public List<Equipment> getEquipmentByLocation(String location) {
        return equipmentRepository.findByLocation(location);
    }

    public List<Equipment> getExpiredWarranty() {
        return equipmentRepository.findExpiredWarranty(LocalDate.now());
    }

    public List<Equipment> getWarrantyExpiringSoon() {
        LocalDate start = LocalDate.now();
        LocalDate end = LocalDate.now().plusMonths(1);
        return equipmentRepository.findWarrantyExpiringBetween(start, end);
    }

    public PageResponse<Equipment> searchEquipment(EquipmentSearchRequest request) {
        Sort.Direction direction = request.getSortDirection().equals("ASC") ?
                Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, request.getSortBy());
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), sort);

        Page<Equipment> page;
        if (request.getKeyword() != null && !request.getKeyword().isEmpty()) {
            page = equipmentRepository.searchEquipment(request.getKeyword(), pageable);
        } else {
            page = equipmentRepository.findAll(pageable);
        }

        return new PageResponse<>(page.getContent(), page.getNumber(), page.getSize(),
                page.getTotalElements(), page.getTotalPages());
    }

    public EquipmentStatusResponse getEquipmentStatus(Long equipmentId) {
        Equipment equipment = equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new RuntimeException("Equipment not found"));

        EquipmentStatusResponse response = new EquipmentStatusResponse();
        response.setId(equipment.getId());
        response.setName(equipment.getName());
        response.setCategory(equipment.getCategory());
        response.setModel(equipment.getModel());
        response.setSerialNumber(equipment.getSerialNumber());
        response.setLocation(equipment.getLocation());
        response.setStatus(equipment.getStatus());
        response.setPurchaseDate(equipment.getPurchaseDate());
        response.setWarrantyExpiry(equipment.getWarrantyExpiry());

        if (equipment.getWarrantyExpiry() != null) {
            LocalDate now = LocalDate.now();
            response.setIsUnderWarranty(equipment.getWarrantyExpiry().isAfter(now));
            response.setDaysRemainingOnWarranty((int) ChronoUnit.DAYS.between(now, equipment.getWarrantyExpiry()));
        }

        if (equipment.getStatus() == EquipmentStatus.FUNCTIONAL || equipment.getStatus() == EquipmentStatus.AVAILABLE) {
            response.setStatusColor("GREEN");
        } else if (equipment.getStatus() == EquipmentStatus.UNDER_REPAIR || equipment.getStatus() == EquipmentStatus.UNDER_MAINTENANCE) {
            response.setStatusColor("ORANGE");
        } else if (equipment.getStatus() == EquipmentStatus.OUT_OF_SERVICE || equipment.getStatus() == EquipmentStatus.DAMAGED) {
            response.setStatusColor("RED");
        } else {
            response.setStatusColor("BLUE");
        }

        return response;
    }

    public Map<String, Object> getEquipmentStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("total", equipmentRepository.count());
        stats.put("available", equipmentRepository.countByStatus(EquipmentStatus.AVAILABLE));
        stats.put("assigned", equipmentRepository.countByStatus(EquipmentStatus.ASSIGNED));
        stats.put("underMaintenance", equipmentRepository.countByStatus(EquipmentStatus.UNDER_MAINTENANCE));
        stats.put("underRepair", equipmentRepository.countByStatus(EquipmentStatus.UNDER_REPAIR));
        stats.put("functional", equipmentRepository.countByStatus(EquipmentStatus.FUNCTIONAL));
        stats.put("outOfService", equipmentRepository.countByStatus(EquipmentStatus.OUT_OF_SERVICE));
        stats.put("expiredWarranty", equipmentRepository.findExpiredWarranty(LocalDate.now()).size());

        List<Object[]> categoryResults = equipmentRepository.countEquipmentByCategory();
        Map<String, Integer> byCategory = new HashMap<>();
        for (Object[] result : categoryResults) {
            byCategory.put(result[0].toString(), ((Number) result[1]).intValue());
        }
        stats.put("byCategory", byCategory);

        return stats;
    }

    @Transactional
    public void deleteEquipment(Long equipmentId) {
        Equipment equipment = equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new RuntimeException("Equipment not found"));
        equipmentRepository.delete(equipment);
        log.info("Equipment deleted: {}", equipmentId);
    }
}