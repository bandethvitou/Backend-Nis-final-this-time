package com.sarana_NIS.newTown_backend.model.entity.position;

import com.sarana_NIS.newTown_backend.model.entity.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "positions")
public class Position extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // ✅ This field exists

    @Column(unique = true, nullable = false, length = 50)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String category;

    @Column(name = "is_temporary_allowed")
    private Boolean isTemporaryAllowed = true;

    // Constructors
    public Position() {}

    public Position(String name) {
        this.name = name;
    }

    // Getters and Setters
    public Long getId() {  // ✅ This getter exists
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Boolean getIsTemporaryAllowed() {
        return isTemporaryAllowed;
    }

    public void setIsTemporaryAllowed(Boolean isTemporaryAllowed) {
        this.isTemporaryAllowed = isTemporaryAllowed;
    }
}