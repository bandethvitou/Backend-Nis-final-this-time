package com.sarana_NIS.newTown_backend.model.entity.user;

import com.sarana_NIS.newTown_backend.model.entity.BaseEntity;
import com.sarana_NIS.newTown_backend.model.entity.core.User;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "parents")
public class Parent extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "full_name", nullable = false, length = 200)
    private String fullName;

    @Column(length = 20)
    private String phone;

    @Column(length = 100)
    private String email;

    @Column(length = 255)
    private String address;

    @Column(length = 50)
    private String relationship;

    @Column(length = 100)
    private String occupation;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "student_parents",
            joinColumns = @JoinColumn(name = "parent_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<Student> students = new HashSet<>();

    // Constructors
    public Parent() {
        this.students = new HashSet<>();
    }

    public Parent(String fullName, String phone, String email) {
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.students = new HashSet<>();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        if (user != null) {
            this.fullName = user.getFullName();
            this.email = user.getEmail();
            this.phone = user.getPhone();
        }
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    // Student management methods
    public void addStudent(Student student) {
        if (this.students == null) {
            this.students = new HashSet<>();
        }
        this.students.add(student);
        // Also add this parent to the student's parents set
        if (!student.getParents().contains(this)) {
            student.getParents().add(this);
        }
    }

    public void removeStudent(Student student) {
        if (this.students != null) {
            this.students.remove(student);
            // Also remove this parent from the student's parents set
            if (student.getParents() != null) {
                student.getParents().remove(this);
            }
        }
    }

    public boolean hasStudent(Student student) {
        return this.students != null && this.students.contains(student);
    }

    // Helper methods
    public boolean isActive() {
        return this.students != null && !this.students.isEmpty();
    }
}