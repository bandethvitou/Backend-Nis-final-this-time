package com.sarana_NIS.newTown_backend.dto.request.hr;

import com.sarana_NIS.newTown_backend.model.enums.Priority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class HiringRequestDTO {

    @NotBlank(message = "Position is required")
    @Size(min = 2, max = 100, message = "Position must be between 2 and 100 characters")
    private String position;

    @NotNull(message = "Department ID is required")
    private Long departmentId;

    @NotNull(message = "Number of positions is required")
    @Positive(message = "Number of positions must be greater than 0")
    private Integer numberOfPositions = 1;

    @NotBlank(message = "Justification is required")
    @Size(min = 10, max = 1000, message = "Justification must be between 10 and 1000 characters")
    private String justification;

    @NotNull(message = "Priority is required")
    private Priority priority = Priority.MEDIUM;

    private Long requestedBy;

    // Constructors
    public HiringRequestDTO() {}

    public HiringRequestDTO(String position, Long departmentId, String justification) {
        this.position = position;
        this.departmentId = departmentId;
        this.justification = justification;
    }

    // Getters and Setters
    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public Long getDepartmentId() { return departmentId; }
    public void setDepartmentId(Long departmentId) { this.departmentId = departmentId; }

    public Integer getNumberOfPositions() { return numberOfPositions; }
    public void setNumberOfPositions(Integer numberOfPositions) { this.numberOfPositions = numberOfPositions; }

    public String getJustification() { return justification; }
    public void setJustification(String justification) { this.justification = justification; }

    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }

    public Long getRequestedBy() { return requestedBy; }
    public void setRequestedBy(Long requestedBy) { this.requestedBy = requestedBy; }
}