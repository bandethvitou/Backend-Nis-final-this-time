package com.sarana_NIS.newTown_backend.mapper.finance;

import com.sarana_NIS.newTown_backend.dto.request.finance.PaymentRequest;
import com.sarana_NIS.newTown_backend.dto.response.finance.PaymentResponse;
import com.sarana_NIS.newTown_backend.model.entity.finance.Payment;
import com.sarana_NIS.newTown_backend.model.entity.user.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface PaymentMapper {

    // ========== Entity → Response ==========
    @Mapping(target = "id", source = "id")
    @Mapping(target = "studentId", source = "student.id")
    @Mapping(target = "studentName", expression = "java(getStudentName(payment))")
    @Mapping(target = "studentEmail", source = "student.email")
    @Mapping(target = "amount", source = "amount")
    @Mapping(target = "paymentDate", source = "paymentDate")
    @Mapping(target = "paymentMethod", source = "paymentMethod")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "referenceNumber", source = "referenceNumber")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "verifiedBy", source = "verifiedBy")  // ✅ String field
    @Mapping(target = "verifiedDate", source = "verifiedDate")
    @Mapping(target = "notes", source = "notes")
    @Mapping(target = "rejectionReason", source = "rejectionReason")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    PaymentResponse toResponse(Payment payment);

    // ========== Request → Entity ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "isActive", ignore = true)  // ✅ Add missing field
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "verifiedBy", ignore = true)
    @Mapping(target = "status", constant = "PENDING")
    @Mapping(target = "verifiedDate", ignore = true)
    @Mapping(target = "notes", ignore = true)  // ✅ Add missing field
    @Mapping(target = "rejectionReason", ignore = true)  // ✅ Add missing field
    Payment toEntity(PaymentRequest request);

    // ========== Update ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "isActive", ignore = true)  // ✅ Add missing field
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "verifiedBy", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "verifiedDate", ignore = true)
    @Mapping(target = "notes", ignore = true)  // ✅ Add missing field
    @Mapping(target = "rejectionReason", ignore = true)  // ✅ Add missing field
    void updateEntity(PaymentRequest request, @MappingTarget Payment payment);

    // ========== Helper Methods ==========

    default String getStudentName(Payment payment) {
        if (payment == null || payment.getStudent() == null) {
            return null;
        }

        Student student = payment.getStudent();

        // Try nameEnglish first (exists in user.Student)
        if (student.getNameEnglish() != null && !student.getNameEnglish().isEmpty()) {
            return student.getNameEnglish();
        }

        // Try fullName (also exists in user.Student)
        if (student.getFullName() != null && !student.getFullName().isEmpty()) {
            return student.getFullName();
        }

        // Try user's full name if available
        if (student.getUser() != null && student.getUser().getFullName() != null) {
            return student.getUser().getFullName();
        }

        // Fallback
        return "Student " + student.getId();
    }
}