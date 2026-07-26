package com.sarana_NIS.newTown_backend.repository;

import com.sarana_NIS.newTown_backend.model.entity.admissions.Tour;
import com.sarana_NIS.newTown_backend.model.enums.TourStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {

    @Query("SELECT t FROM Tour t WHERE t.status = :status")
    List<Tour> findByStatus(@Param("status") TourStatus status);

    @Query("SELECT t FROM Tour t WHERE t.tourDate = :date")
    List<Tour> findByTourDate(@Param("date") LocalDate date);

    @Query("SELECT t FROM Tour t WHERE t.tourDate BETWEEN :startDate AND :endDate")
    List<Tour> findByTourDateBetween(@Param("startDate") LocalDate startDate,
                                     @Param("endDate") LocalDate endDate);

    @Query("SELECT t FROM Tour t WHERE t.parentName LIKE CONCAT('%', :name, '%') " +
            "OR t.studentName LIKE CONCAT('%', :name, '%')")
    List<Tour> searchTours(@Param("name") String name);

    @Query("SELECT t FROM Tour t WHERE t.level = :level")
    List<Tour> findByLevel(@Param("level") String level);

    @Query("SELECT t FROM Tour t WHERE t.tourDate = :date AND t.status = 'SCHEDULED'")
    List<Tour> findScheduledToursForDate(@Param("date") LocalDate date);

    @Query("SELECT COUNT(t) FROM Tour t WHERE t.tourDate = :date")
    Long countByTourDate(@Param("date") LocalDate date);

    @Query("SELECT t FROM Tour t WHERE t.tourDate >= :date AND t.status = 'SCHEDULED'")
    List<Tour> findUpcomingTours(@Param("date") LocalDate date);

    @Query("SELECT t FROM Tour t WHERE t.tourDate < :date AND t.status = 'SCHEDULED'")
    List<Tour> findPastScheduledTours(@Param("date") LocalDate date);

    @Query("SELECT COUNT(t) FROM Tour t WHERE t.status = 'SCHEDULED'")
    Long countScheduledTours();

    @Query("SELECT COUNT(t) FROM Tour t WHERE t.status = 'COMPLETED'")
    Long countCompletedTours();
}