package com.sarana_NIS.newTown_backend.security.aspect;

import com.sarana_NIS.newTown_backend.security.annotation.PublicEndpoint;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresPermission;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresRole;
import com.sarana_NIS.newTown_backend.security.resolver.MultiPositionResolver;
import com.sarana_NIS.newTown_backend.security.service.CustomUserDetails;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
@Component
public class AuthorizationAspect {

    private static final Logger log = LoggerFactory.getLogger(AuthorizationAspect.class);

    private final MultiPositionResolver positionResolver;

    public AuthorizationAspect(MultiPositionResolver positionResolver) {
        this.positionResolver = positionResolver;
    }

    @Around("@annotation(com.sarana_NIS.newTown_backend.security.annotation.RequiresRole) || " +
            "@annotation(com.sarana_NIS.newTown_backend.security.annotation.RequiresPermission) || " +
            "@annotation(com.sarana_NIS.newTown_backend.security.annotation.PublicEndpoint)")
    public Object authorize(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        // Check if public endpoint
        PublicEndpoint publicEndpoint = method.getAnnotation(PublicEndpoint.class);
        if (publicEndpoint != null && publicEndpoint.value()) {
            log.debug("Public endpoint accessed: {}", method.getName());
            return joinPoint.proceed();
        }

        // Check class-level public endpoint
        Class<?> targetClass = joinPoint.getTarget().getClass();
        PublicEndpoint classPublic = targetClass.getAnnotation(PublicEndpoint.class);
        if (classPublic != null && classPublic.value()) {
            log.debug("Public class endpoint accessed: {}", targetClass.getSimpleName());
            return joinPoint.proceed();
        }

        // Get current authentication
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            log.warn("User not authenticated");
            throw new AccessDeniedException("User not authenticated");
        }

        Object principal = authentication.getPrincipal();
        if (!(principal instanceof CustomUserDetails)) {
            log.warn("Invalid user principal type: {}", principal.getClass().getName());
            throw new AccessDeniedException("Invalid user principal");
        }

        CustomUserDetails userDetails = (CustomUserDetails) principal;
        Long userId = userDetails.getId();
        log.debug("Authorizing user: {} (ID: {})", userDetails.getUsername(), userId);

        // Check requires role
        RequiresRole requiresRole = method.getAnnotation(RequiresRole.class);
        if (requiresRole != null) {
            log.debug("Checking roles: {}, requireAll: {}",
                    Arrays.toString(requiresRole.value()), requiresRole.requireAll());
            checkRoles(userId, requiresRole.value(), requiresRole.requireAll());
        }

        // Check requires permission
        RequiresPermission requiresPermission = method.getAnnotation(RequiresPermission.class);
        if (requiresPermission != null) {
            log.debug("Checking permissions: {}, requireAll: {}",
                    Arrays.toString(requiresPermission.value()), requiresPermission.requireAll());
            checkPermissions(userId, requiresPermission.value(), requiresPermission.requireAll());
        }

        log.debug("Authorization successful for user: {}", userDetails.getUsername());
        return joinPoint.proceed();
    }

    private void checkRoles(Long userId, String[] requiredRoles, boolean requireAll) {
        if (requireAll) {
            for (String role : requiredRoles) {
                if (!positionResolver.hasRole(userId, role)) {
                    log.warn("User {} missing required role: {}", userId, role);
                    throw new AccessDeniedException("Missing required role: " + role);
                }
            }
        } else {
            boolean hasRole = false;
            for (String role : requiredRoles) {
                if (positionResolver.hasRole(userId, role)) {
                    hasRole = true;
                    break;
                }
            }
            if (!hasRole) {
                log.warn("User {} missing any of required roles: {}", userId, Arrays.toString(requiredRoles));
                throw new AccessDeniedException("Required role not found: " + Arrays.toString(requiredRoles));
            }
        }
    }

    private void checkPermissions(Long userId, String[] requiredPermissions, boolean requireAll) {
        if (requireAll) {
            for (String permission : requiredPermissions) {
                if (!positionResolver.hasPermission(userId, permission)) {
                    log.warn("User {} missing required permission: {}", userId, permission);
                    throw new AccessDeniedException("Missing required permission: " + permission);
                }
            }
        } else {
            boolean hasPermission = false;
            for (String permission : requiredPermissions) {
                if (positionResolver.hasPermission(userId, permission)) {
                    hasPermission = true;
                    break;
                }
            }
            if (!hasPermission) {
                log.warn("User {} missing any of required permissions: {}", userId, Arrays.toString(requiredPermissions));
                throw new AccessDeniedException("Required permission not found: " + Arrays.toString(requiredPermissions));
            }
        }
    }
}