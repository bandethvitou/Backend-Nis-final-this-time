package com.sarana_NIS.newTown_backend.mapper.admissions;

import com.sarana_NIS.newTown_backend.dto.request.admissions.VisitorCheckoutRequest;
import com.sarana_NIS.newTown_backend.dto.request.admissions.VisitorRequest;
import com.sarana_NIS.newTown_backend.dto.response.admissions.VisitorPassResponse;
import com.sarana_NIS.newTown_backend.dto.response.admissions.VisitorResponse;
import com.sarana_NIS.newTown_backend.model.entity.admissions.Visitor;
import com.sarana_NIS.newTown_backend.model.entity.core.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.time.Duration;
import java.util.UUID;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface VisitorMapper {

    // ========== Entity → Response ==========
    @Mapping(target = "hostName", source = "host.fullName")
    @Mapping(target = "hostDepartment", expression = "java(getHostDepartment(visitor))")
    @Mapping(target = "hostEmail", source = "host.email")
    @Mapping(target = "hostPhone", source = "host.phone")
    @Mapping(target = "hostPosition", expression = "java(getHostPosition(visitor))")
    @Mapping(target = "isActive", expression = "java(visitor.getStatus() != null && visitor.getStatus().name().equals(\"ACTIVE\"))")
    @Mapping(target = "isCheckedOut", expression = "java(visitor.getCheckOut() != null)")
    @Mapping(target = "duration", expression = "java(calculateDuration(visitor))")
    VisitorResponse toResponse(Visitor visitor);

    // ========== Entity → Pass Response ==========
    @Mapping(target = "visitorName", source = "name")
    @Mapping(target = "hostName", source = "host.fullName")
    @Mapping(target = "hostDepartment", expression = "java(getHostDepartment(visitor))")
    @Mapping(target = "isActive", expression = "java(visitor.getStatus() != null && visitor.getStatus().name().equals(\"ACTIVE\"))")
    @Mapping(target = "qrCode", expression = "java(generateQRCode(visitor))")
    VisitorPassResponse toPassResponse(Visitor visitor);

    // ========== Request → Entity ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "host", ignore = true)
    @Mapping(target = "status", constant = "ACTIVE")
    @Mapping(target = "checkIn", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "checkOut", ignore = true)
    @Mapping(target = "passNumber", expression = "java(generatePassNumber())")
    Visitor toEntity(VisitorRequest request);

    // ========== Checkout Update ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "host", ignore = true)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "phone", ignore = true)
    @Mapping(target = "purpose", ignore = true)
    @Mapping(target = "organization", ignore = true)
    @Mapping(target = "vehicleNumber", ignore = true)
    @Mapping(target = "passNumber", ignore = true)
    @Mapping(target = "checkIn", ignore = true)
    @Mapping(target = "status", constant = "CHECKED_OUT")
    @Mapping(target = "checkOut", expression = "java(java.time.LocalDateTime.now())")
    void updateForCheckout(VisitorCheckoutRequest request, @MappingTarget Visitor visitor);

    // ========== Helper Methods ==========

    default String getHostDepartment(Visitor visitor) {
        if (visitor == null || visitor.getHost() == null) return null;
        User host = visitor.getHost();

        // Try to get department from Staff relationship
        if (host.getStaff() != null && host.getStaff().getDepartment() != null) {
            return host.getStaff().getDepartment().getName();
        }

        // Fallback: Use role as department
        if (host.getRole() != null) {
            return host.getRole().name();
        }

        return null;
    }

    default String getHostPosition(Visitor visitor) {
        if (visitor == null || visitor.getHost() == null) return null;
        User host = visitor.getHost();

        // Try to get position from Staff
        if (host.getStaff() != null && host.getStaff().getPosition() != null) {
            return host.getStaff().getPosition();
        }

        // Fallback: Use role as position
        if (host.getRole() != null) {
            return host.getRole().getDisplayName();
        }

        return null;
    }

    default String calculateDuration(Visitor visitor) {
        if (visitor == null || visitor.getCheckIn() == null) return null;
        if (visitor.getCheckOut() == null) {
            return "In progress";
        }
        long minutes = Duration.between(
                visitor.getCheckIn(),
                visitor.getCheckOut()
        ).toMinutes();
        if (minutes < 60) {
            return minutes + " minutes";
        }
        long hours = minutes / 60;
        long remainingMinutes = minutes % 60;
        return hours + "h " + remainingMinutes + "m";
    }

    default String generatePassNumber() {
        return "VIS-" + UUID.randomUUID().toString()
                .substring(0, 8).toUpperCase();
    }

    default String generateQRCode(Visitor visitor) {
        // Generate QR code data
        return "VISITOR:" + visitor.getPassNumber() +
                ":" + visitor.getName();
    }
}