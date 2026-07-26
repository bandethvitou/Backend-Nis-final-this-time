package com.sarana_NIS.newTown_backend.mapper.finance;

import com.sarana_NIS.newTown_backend.dto.request.finance.FeeRequest;
import com.sarana_NIS.newTown_backend.dto.response.finance.FeeResponse;
import com.sarana_NIS.newTown_backend.model.entity.finance.Fee;
import com.sarana_NIS.newTown_backend.model.enums.FeeStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.time.LocalDate;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface FeeMapper {

    // ========== Entity → Response ==========
    @Mapping(target = "id", source = "id")
    @Mapping(target = "studentId", source = "student.id")
    @Mapping(target = "studentName", source = "student.nameEnglish")
    @Mapping(target = "studentClass", source = "student.currentClass.name")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "amount", source = "amount")
    @Mapping(target = "dueDate", source = "dueDate")
    @Mapping(target = "paymentDate", source = "paymentDate")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "receiptNumber", source = "receiptNumber")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "academicYear", source = "academicYear")
    @Mapping(target = "term", source = "term")
    @Mapping(target = "daysOverdue", expression = "java(calculateDaysOverdue(fee))")
    @Mapping(target = "statusColor", expression = "java(getStatusColor(fee.getStatus()))")
    @Mapping(target = "isOverdue", expression = "java(fee.isOverdue())")
    FeeResponse toResponse(Fee fee);

    // ========== Request → Entity ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "status", constant = "DUE")
    @Mapping(target = "paymentDate", ignore = true)
    @Mapping(target = "receiptNumber", ignore = true)
    Fee toEntity(FeeRequest request);

    // ========== Update ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "paymentDate", ignore = true)
    @Mapping(target = "receiptNumber", ignore = true)
    void updateEntity(FeeRequest request, @MappingTarget Fee fee);

    // ========== Helper Methods ==========

    default Integer calculateDaysOverdue(Fee fee) {
        if (fee == null || fee.getDueDate() == null) return 0;
        if (fee.getStatus() == FeeStatus.PAID) return 0;
        if (fee.getDueDate().isBefore(LocalDate.now())) {
            return (int) java.time.temporal.ChronoUnit.DAYS.between(fee.getDueDate(), LocalDate.now());
        }
        return 0;
    }

    default String getStatusColor(FeeStatus status) {
        if (status == null) return "#6B7280";
        switch (status) {
            case PAID: return "#10B981";
            case DUE: return "#F59E0B";
            case OVERDUE: return "#EF4444";
            case PARTIALLY_PAID: return "#8B5CF6";
            case WAIVED: return "#3B82F6";
            case DISPUTED: return "#EC4899";
            default: return "#6B7280";
        }
    }
}