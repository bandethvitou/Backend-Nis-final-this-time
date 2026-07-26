package com.sarana_NIS.newTown_backend.repository;

import com.sarana_NIS.newTown_backend.model.entity.position.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {

    Optional<Position> findByName(String name);

    boolean existsByName(String name);

    List<Position> findByCategory(String category);

    @Query("SELECT p FROM Position p WHERE p.isTemporaryAllowed = true")
    List<Position> findTemporaryAllowedPositions();

    // ❌ DELETE THIS METHOD:
    // @Query("SELECT p FROM Position p LEFT JOIN FETCH p.staff WHERE p.id = :id")
    // Optional<Position> findByIdWithStaff(@Param("id") Long id);

    @Query("SELECT p FROM Position p WHERE p.category = :category AND p.isTemporaryAllowed = true")
    List<Position> findTemporaryAllowedByCategory(@Param("category") String category);

    @Query("SELECT COUNT(sp) FROM StaffPosition sp WHERE sp.position.id = :positionId")
    Long countStaffByPositionId(@Param("positionId") Long positionId);

    @Query("SELECT p FROM Position p WHERE p.name LIKE CONCAT('%', :keyword, '%')")
    List<Position> searchPositions(@Param("keyword") String keyword);

    @Query("SELECT DISTINCT p.category FROM Position p")
    List<String> findAllCategories();
}