package com.sarana_NIS.newTown_backend.repository;

import com.sarana_NIS.newTown_backend.model.entity.core.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    @Query("SELECT c FROM Contact c WHERE c.department = :department")
    List<Contact> findByDepartment(@Param("department") String department);

    @Query("SELECT c FROM Contact c WHERE c.position = :position")
    List<Contact> findByPosition(@Param("position") String position);

    @Query("SELECT c FROM Contact c WHERE c.name LIKE CONCAT('%', :keyword, '%') " +
            "OR c.department LIKE CONCAT('%', :keyword, '%')")
    List<Contact> searchContacts(@Param("keyword") String keyword);

    @Query("SELECT c FROM Contact c WHERE c.isAvailable = true")
    List<Contact> findAvailableContacts();

    @Query("SELECT c.department, COUNT(c) FROM Contact c GROUP BY c.department")
    List<Object[]> countContactsByDepartment();
}