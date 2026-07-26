package com.sarana_NIS.newTown_backend.mapper.finance;

import com.sarana_NIS.newTown_backend.dto.request.finance.BillRequest;
import com.sarana_NIS.newTown_backend.dto.response.finance.BillResponse;
import com.sarana_NIS.newTown_backend.model.entity.finance.Bill;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.time.LocalDate;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface BillMapper {

    // ========== Entity → Response ==========
    @Mapping(target = "requestedById", source = "requestedBy.id")
    @Mapping(target = "requestedByName", source = "requestedBy.fullName")
    @Mapping(target = "requestedBy", expression = "java(getRequestedByUsername(bill))")
    @Mapping(target = "approvedById", source = "approvedBy.id")
    @Mapping(target = "approvedByName", source = "approvedBy.fullName")
    @Mapping(target = "approvedBy", expression = "java(getApprovedByUsername(bill))")
    @Mapping(target = "daysOverdue", expression = "java(calculateDaysOverdue(bill))")
    BillResponse toResponse(Bill bill);

    // ========== Request → Entity ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "requestedBy", ignore = true)
    @Mapping(target = "approvedBy", ignore = true)
    @Mapping(target = "status", constant = "PENDING")
    @Mapping(target = "approvedAt", ignore = true)
    @Mapping(target = "rejectedReason", ignore = true)  // ✅ Remove rejectedAt
    Bill toEntity(BillRequest request);

    // ========== Update ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "requestedBy", ignore = true)
    @Mapping(target = "approvedBy", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "approvedAt", ignore = true)
    @Mapping(target = "rejectedReason", ignore = true)  // ✅ Remove rejectedAt
    void updateEntity(BillRequest request, @MappingTarget Bill bill);

    // ========== Helper Methods ==========

    default String getRequestedByUsername(Bill bill) {
        if (bill == null || bill.getRequestedBy() == null) {
            return null;
        }
        return bill.getRequestedBy().getUsername();
    }

    default String getApprovedByUsername(Bill bill) {
        if (bill == null || bill.getApprovedBy() == null) {
            return null;
        }
        return bill.getApprovedBy().getUsername();
    }

    default Integer calculateDaysOverdue(Bill bill) {
        if (bill == null || bill.getDueDate() == null) return 0;
        if (bill.getStatus() == null || bill.getStatus().name().equals("APPROVED")) return 0;
        if (bill.getDueDate().isBefore(LocalDate.now())) {
            return (int) java.time.temporal.ChronoUnit.DAYS.between(bill.getDueDate(), LocalDate.now());
        }
        return 0;
    }
}