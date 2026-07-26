package com.sarana_NIS.newTown_backend.model.entity.user;

import com.sarana_NIS.newTown_backend.model.enums.Relationship;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "student_parents")
@IdClass(StudentParent.StudentParentId.class)
public class StudentParent {

    @Id
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @Id
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Parent parent;

    @Enumerated(EnumType.STRING)
    private Relationship relationship;

    // Constructors
    public StudentParent() {}

    public StudentParent(Student student, Parent parent, Relationship relationship) {
        this.student = student;
        this.parent = parent;
        this.relationship = relationship;
    }

    // Getters and Setters
    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public Parent getParent() { return parent; }
    public void setParent(Parent parent) { this.parent = parent; }

    public Relationship getRelationship() { return relationship; }
    public void setRelationship(Relationship relationship) { this.relationship = relationship; }

    // Inner class for composite key
    public static class StudentParentId implements Serializable {
        private Long student;
        private Long parent;

        public StudentParentId() {}

        public StudentParentId(Long student, Long parent) {
            this.student = student;
            this.parent = parent;
        }

        public Long getStudent() { return student; }
        public void setStudent(Long student) { this.student = student; }

        public Long getParent() { return parent; }
        public void setParent(Long parent) { this.parent = parent; }
    }
}