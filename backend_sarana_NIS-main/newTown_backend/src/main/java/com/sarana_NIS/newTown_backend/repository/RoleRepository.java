package com.sarana_NIS.newTown_backend.repository;

import com.sarana_NIS.newTown_backend.model.entity.core.Role;
import com.sarana_NIS.newTown_backend.model.enums.RoleCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);

    @Query("SELECT r FROM Role r LEFT JOIN FETCH r.users WHERE r.id = :id")
    Optional<Role> findByIdWithUsers(@Param("id") Long id);

    @Query("SELECT r FROM Role r ORDER BY r.name ASC")
    List<Role> findAllOrdered();

    // ✅ Repository methods should accept String for category
    @Query("SELECT r FROM Role r WHERE r.category = :category ORDER BY r.name ASC")
    List<Role> findRolesByCategoryOrdered(@Param("category") String category);

    @Query("SELECT r FROM Role r JOIN r.users u WHERE u.id = :userId")
    List<Role> findRolesByUserId(@Param("userId") Long userId);

    @Query("SELECT r.name FROM Role r")
    List<String> findAllRoleNames();

    @Query("SELECT COUNT(r) FROM Role r WHERE r.category = :category")
    long countByCategory(@Param("category") String category);

    @Query("SELECT r FROM Role r WHERE r.id IN :roleIds")
    List<Role> findAllByIdIn(@Param("roleIds") List<Long> roleIds);

    boolean existsByName(String name);
}