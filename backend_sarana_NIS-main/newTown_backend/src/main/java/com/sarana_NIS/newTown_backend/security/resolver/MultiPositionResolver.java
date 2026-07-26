package com.sarana_NIS.newTown_backend.security.resolver;

import com.sarana_NIS.newTown_backend.model.entity.position.StaffPosition;
import com.sarana_NIS.newTown_backend.repository.StaffPositionRepository;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class MultiPositionResolver {

    private final StaffPositionRepository staffPositionRepository;

    public MultiPositionResolver(StaffPositionRepository staffPositionRepository) {
        this.staffPositionRepository = staffPositionRepository;
    }

    /**
     * Get all permissions for a staff member based on all their positions
     */
    public Set<String> resolvePermissions(Long staffId) {
        Set<String> permissions = new HashSet<>();

        List<StaffPosition> staffPositions = staffPositionRepository.findActiveByStaffId(staffId);

        for (StaffPosition staffPosition : staffPositions) {
            // Get permissions from each position
            // This could be a field in Position entity or a mapping
            String positionName = staffPosition.getPosition().getName();
            permissions.addAll(getPermissionsForPosition(positionName));
        }

        return permissions;
    }

    /**
     * Check if staff has a specific permission
     */
    public boolean hasPermission(Long staffId, String permission) {
        Set<String> permissions = resolvePermissions(staffId);
        return permissions.contains(permission);
    }

    /**
     * Check if staff has any of the given permissions
     */
    public boolean hasAnyPermission(Long staffId, String... permissions) {
        Set<String> userPermissions = resolvePermissions(staffId);
        for (String permission : permissions) {
            if (userPermissions.contains(permission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if staff has all of the given permissions
     */
    public boolean hasAllPermissions(Long staffId, String... permissions) {
        Set<String> userPermissions = resolvePermissions(staffId);
        for (String permission : permissions) {
            if (!userPermissions.contains(permission)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Get all roles for a staff member based on their positions
     */
    public Set<String> resolveRoles(Long staffId) {
        Set<String> roles = new HashSet<>();

        List<StaffPosition> staffPositions = staffPositionRepository.findActiveByStaffId(staffId);

        for (StaffPosition staffPosition : staffPositions) {
            String positionName = staffPosition.getPosition().getName();
            // Convert position to role format
            roles.add("ROLE_" + positionName.toUpperCase());
        }

        return roles;
    }

    /**
     * Check if staff has a specific role
     */
    public boolean hasRole(Long staffId, String role) {
        Set<String> roles = resolveRoles(staffId);
        return roles.contains("ROLE_" + role.toUpperCase()) || roles.contains(role);
    }

    private Set<String> getPermissionsForPosition(String positionName) {
        Set<String> permissions = new HashSet<>();

        // Position -> Permissions mapping based on your system
        switch (positionName.toUpperCase()) {
            case "DIRECTOR":
                permissions.addAll(Set.of(
                        "USER_MANAGE", "ROLE_MANAGE", "GOAL_MANAGE", "TASK_MANAGE",
                        "MEETING_MANAGE", "BILL_APPROVE", "REPORT_VIEW", "LOG_VIEW",
                        "SETTINGS_EDIT", "ALL_PERMISSIONS"
                ));
                break;
            case "TOP_MANAGER":
                permissions.addAll(Set.of(
                        "SCORE_VIEW", "SCORE_APPROVE", "ENROLLMENT_VIEW",
                        "PERFORMANCE_VIEW", "REPORT_REVIEW", "TASK_ASSIGN"
                ));
                break;
            case "MANAGER":
                permissions.addAll(Set.of(
                        "TEAM_VIEW", "LEAVE_APPROVE", "HIRING_REQUEST",
                        "PROPOSAL_CREATE", "REPORT_REVIEW", "STAFF_REPORT"
                ));
                break;
            case "HR_ADMIN":
                permissions.addAll(Set.of(
                        "STAFF_MANAGE", "POSITION_MANAGE", "SALARY_VIEW",
                        "LEAVE_MANAGE", "HIRING_MANAGE"
                ));
                break;
            case "FINANCE_ADMIN":
                permissions.addAll(Set.of(
                        "FEE_MANAGE", "PAYMENT_MANAGE", "BILL_APPROVE",
                        "EXPENSE_MANAGE", "REPORT_VIEW"
                ));
                break;
            case "TEACHER":
                permissions.addAll(Set.of(
                        "LESSON_PLAN_MANAGE", "SCORE_ENTRY", "ATTENDANCE_TAKE",
                        "HOMEWORK_MANAGE", "MESSAGE_SEND"
                ));
                break;
            case "STUDENT":
                permissions.addAll(Set.of(
                        "SCORE_VIEW", "ATTENDANCE_VIEW", "HOMEWORK_VIEW",
                        "CALENDAR_VIEW", "GALLERY_VIEW"
                ));
                break;
            case "PARENT":
                permissions.addAll(Set.of(
                        "CHILD_VIEW", "SCORE_VIEW", "ATTENDANCE_VIEW",
                        "FEE_VIEW", "MESSAGE_SEND"
                ));
                break;
            default:
                permissions.add("BASIC_ACCESS");
                break;
        }

        return permissions;
    }
}