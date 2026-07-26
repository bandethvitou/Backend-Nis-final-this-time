package com.sarana_NIS.newTown_backend.mapper.it;

import com.sarana_NIS.newTown_backend.dto.request.it.EquipmentRequest;
import com.sarana_NIS.newTown_backend.dto.response.it.EquipmentResponse;
import com.sarana_NIS.newTown_backend.model.entity.it.Equipment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface EquipmentMapper {

    // ========== Entity → Response ==========
    @Mapping(target = "categoryName", source = "category")
    @Mapping(target = "assignedToName", source = "assignedTo.fullName")
    @Mapping(target = "assignedToId", source = "assignedTo.id")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "categoryId", ignore = true)  // ✅ Add this - Equipment doesn't have categoryId
    EquipmentResponse toResponse(Equipment equipment);

    // ========== Request → Entity ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "assignedDate", ignore = true)
    @Mapping(target = "lastMaintenanceDate", ignore = true)
    @Mapping(target = "nextMaintenanceDate", ignore = true)
    @Mapping(target = "notes", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "assignedTo", ignore = true)
    @Mapping(target = "status", constant = "AVAILABLE")
    @Mapping(target = "purchaseDate", expression = "java(java.time.LocalDate.now())")
    @Mapping(target = "serialNumber", source = "serialNumber")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "category", source = "category")
    Equipment toEntity(EquipmentRequest request);

    // ========== Update ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "assignedDate", ignore = true)
    @Mapping(target = "lastMaintenanceDate", ignore = true)
    @Mapping(target = "nextMaintenanceDate", ignore = true)
    @Mapping(target = "notes", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "assignedTo", ignore = true)
    @Mapping(target = "serialNumber", ignore = true)
    // ✅ REMOVED duplicate createdAt and updatedAt mappings
    void updateEntity(@MappingTarget Equipment equipment, EquipmentRequest request);

    // ========== List Mapping ==========
    List<EquipmentResponse> toResponseList(List<Equipment> equipments);
}