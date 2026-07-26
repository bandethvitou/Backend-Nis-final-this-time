package com.sarana_NIS.newTown_backend.dto.request.position;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PositionRequest {

    @NotBlank(message = "Position name is required")
    @Size(min = 2, max = 50, message = "Position name must be between 2 and 50 characters")
    private String name;

    @Size(max = 500, message = "Description must be less than 500 characters")
    private String description;

    @Size(max = 50, message = "Category must be less than 50 characters")
    private String category;

    private Boolean isTemporaryAllowed;

    // Constructors
    public PositionRequest() {}

    public PositionRequest(String name) {
        this.name = name;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Boolean getIsTemporaryAllowed() { return isTemporaryAllowed; }
    public void setIsTemporaryAllowed(Boolean isTemporaryAllowed) { this.isTemporaryAllowed = isTemporaryAllowed; }
}