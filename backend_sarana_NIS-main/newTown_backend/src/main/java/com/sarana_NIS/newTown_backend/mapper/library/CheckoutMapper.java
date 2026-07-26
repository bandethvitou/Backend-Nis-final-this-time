package com.sarana_NIS.newTown_backend.mapper.library;

import com.sarana_NIS.newTown_backend.dto.request.library.CheckoutRequest;
import com.sarana_NIS.newTown_backend.dto.response.library.CheckoutResponse;
import com.sarana_NIS.newTown_backend.dto.response.library.OverdueBookResponse;
import com.sarana_NIS.newTown_backend.model.entity.library.Checkout;
import com.sarana_NIS.newTown_backend.model.enums.CheckoutStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.time.LocalDate;
import java.util.Set;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CheckoutMapper {

    // ========== Entity → Response ==========
    @Mapping(target = "bookId", source = "book.id")
    @Mapping(target = "bookTitle", source = "book.title")
    @Mapping(target = "bookIsbn", source = "book.isbn")
    @Mapping(target = "bookAuthor", source = "book.author")
    @Mapping(target = "studentId", source = "student.id")
    @Mapping(target = "studentName", source = "student.nameEnglish")
    @Mapping(target = "studentClass", source = "student.currentClass.name")
    @Mapping(target = "studentGrade", source = "student.currentClass.grade")
    @Mapping(target = "parentName", expression = "java(getParentName(checkout))")
    @Mapping(target = "isOverdue", expression = "java(checkout.isOverdue())")
    @Mapping(target = "statusColor", expression = "java(getStatusColor(checkout.getStatus()))")
    @Mapping(target = "daysRemaining", expression = "java(calculateDaysRemaining(checkout))")
    @Mapping(target = "daysOverdue", expression = "java(calculateDaysOverdue(checkout))")
    CheckoutResponse toResponse(Checkout checkout);

    // ========== Request → Entity ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "book", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "status", constant = "CHECKED_OUT")
    @Mapping(target = "checkoutDate", expression = "java(java.time.LocalDate.now())")
    @Mapping(target = "dueDate", expression = "java(java.time.LocalDate.now().plusDays(14))")
    @Mapping(target = "returnDate", ignore = true)
    @Mapping(target = "fineAmount", ignore = true)
    @Mapping(target = "finePaid", constant = "false")
    Checkout toEntity(CheckoutRequest request);

    // ========== Overdue Response ==========
    @Mapping(target = "checkoutId", source = "id")
    @Mapping(target = "bookTitle", source = "book.title")
    @Mapping(target = "bookIsbn", source = "book.isbn")
    @Mapping(target = "studentName", source = "student.nameEnglish")
    @Mapping(target = "studentId", source = "student.studentId")
    @Mapping(target = "className", source = "student.currentClass.name")
    @Mapping(target = "parentName", expression = "java(getParentName(checkout))")
    @Mapping(target = "parentPhone", expression = "java(getParentPhone(checkout))")
    @Mapping(target = "parentEmail", expression = "java(getParentEmail(checkout))")
    @Mapping(target = "daysOverdue", expression = "java(calculateDaysOverdue(checkout))")
    @Mapping(target = "status", expression = "java(determineOverdueStatus(checkout))")
    OverdueBookResponse toOverdueResponse(Checkout checkout);

    // ========== Helper Methods ==========

    default String getParentName(Checkout checkout) {
        if (checkout == null || checkout.getStudent() == null) return null;
        Set<com.sarana_NIS.newTown_backend.model.entity.user.Parent> parents = checkout.getStudent().getParents();
        if (parents == null || parents.isEmpty()) return null;
        return parents.stream()
                .findFirst()
                .map(p -> p.getUser() != null ? p.getUser().getFullName() : null)
                .orElse(null);
    }

    default String getParentPhone(Checkout checkout) {
        if (checkout == null || checkout.getStudent() == null) return null;
        Set<com.sarana_NIS.newTown_backend.model.entity.user.Parent> parents = checkout.getStudent().getParents();
        if (parents == null || parents.isEmpty()) return null;
        return parents.stream()
                .findFirst()
                .map(p -> p.getUser() != null ? p.getUser().getPhone() : null)
                .orElse(null);
    }

    default String getParentEmail(Checkout checkout) {
        if (checkout == null || checkout.getStudent() == null) return null;
        Set<com.sarana_NIS.newTown_backend.model.entity.user.Parent> parents = checkout.getStudent().getParents();
        if (parents == null || parents.isEmpty()) return null;
        return parents.stream()
                .findFirst()
                .map(p -> p.getUser() != null ? p.getUser().getEmail() : null)
                .orElse(null);
    }

    default boolean isCheckoutOverdue(Checkout checkout) {
        if (checkout == null) return false;
        return checkout.isOverdue();
    }

    default Integer calculateDaysRemaining(Checkout checkout) {
        if (checkout == null || checkout.getDueDate() == null) return 0;
        if (checkout.getStatus() == CheckoutStatus.RETURNED) return 0;
        if (checkout.getDueDate().isBefore(LocalDate.now())) return 0;
        return (int) java.time.temporal.ChronoUnit.DAYS.between(
                LocalDate.now(),
                checkout.getDueDate()
        );
    }

    default Integer calculateDaysOverdue(Checkout checkout) {
        if (checkout == null || checkout.getDueDate() == null) return 0;
        if (!checkout.isOverdue()) return 0;
        return (int) java.time.temporal.ChronoUnit.DAYS.between(
                checkout.getDueDate(),
                LocalDate.now()
        );
    }

    default String getStatusColor(CheckoutStatus status) {
        if (status == null) return "#6B7280";
        switch (status) {
            case CHECKED_OUT: return "#3B82F6";
            case RETURNED: return "#10B981";
            case OVERDUE: return "#EF4444";
            default: return "#6B7280";
        }
    }

    default String determineOverdueStatus(Checkout checkout) {
        if (checkout == null) return "UNKNOWN";
        if (checkout.getStatus() == CheckoutStatus.RETURNED) {
            return "RETURNED";
        }
        if (checkout.isOverdue()) {
            return "OVERDUE";
        }
        return "PENDING";
    }
}