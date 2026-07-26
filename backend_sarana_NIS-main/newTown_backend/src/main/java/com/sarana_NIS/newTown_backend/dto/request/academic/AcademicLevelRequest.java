package com.sarana_NIS.newTown_backend.dto.request.academic;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class AcademicLevelRequest {

    @NotBlank(message = "Level name is required")
    @Size(min = 2, max = 50, message = "Level name must be between 2 and 50 characters")
    private String name;

    @NotBlank(message = "Level code is required")
    @Pattern(regexp = "^[A-Z0-9]+$", message = "Level code can only contain uppercase letters and numbers")
    @Size(min = 2, max = 10, message = "Level code must be between 2 and 10 characters")
    private String code;

    @Min(value = 0, message = "Min age must be greater than or equal to 0")
    @Max(value = 99, message = "Min age must be less than 99")
    private Integer minAge;

    @Min(value = 0, message = "Max age must be greater than or equal to 0")
    @Max(value = 99, message = "Max age must be less than 99")
    private Integer maxAge;

    @Min(value = 0, message = "Sequence order must be greater than or equal to 0")
    private Integer sequenceOrder;

    // Constructors
    public AcademicLevelRequest() {}

    public AcademicLevelRequest(String name, String code) {
        this.name = name;
        this.code = code;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public Integer getMinAge() { return minAge; }
    public void setMinAge(Integer minAge) { this.minAge = minAge; }

    public Integer getMaxAge() { return maxAge; }
    public void setMaxAge(Integer maxAge) { this.maxAge = maxAge; }

    public Integer getSequenceOrder() { return sequenceOrder; }
    public void setSequenceOrder(Integer sequenceOrder) { this.sequenceOrder = sequenceOrder; }
}