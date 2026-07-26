package com.sarana_NIS.newTown_backend.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenValidator {

    private static final Logger log = LoggerFactory.getLogger(JwtTokenValidator.class);

    @Value("${jwt.secret:defaultSecretKeyForNIS_School_ERP_System_2024_ShouldBeLongEnough}")
    private String secret;

    public JwtTokenValidator() {
        // Default constructor
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(keyBytes, "HmacSHA512");
    }

    public ValidationResult validate(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            Date expiration = claims.getExpiration();
            if (expiration.before(new Date())) {
                return ValidationResult.expired();
            }

            return ValidationResult.valid(claims);

        } catch (ExpiredJwtException e) {
            log.warn("JWT token expired: {}", e.getMessage());
            return ValidationResult.expired();
        } catch (UnsupportedJwtException e) {
            log.warn("Unsupported JWT token: {}", e.getMessage());
            return ValidationResult.invalid("Unsupported JWT token");
        } catch (MalformedJwtException e) {
            log.warn("Malformed JWT token: {}", e.getMessage());
            return ValidationResult.invalid("Invalid JWT token");
        } catch (SignatureException e) {
            log.warn("Invalid JWT signature: {}", e.getMessage());
            return ValidationResult.invalid("Invalid JWT signature");
        } catch (IllegalArgumentException e) {
            log.warn("JWT claims string is empty: {}", e.getMessage());
            return ValidationResult.invalid("JWT claims string is empty");
        }
    }

    public static class ValidationResult {
        private final boolean valid;
        private final String errorMessage;
        private final Claims claims;

        private ValidationResult(boolean valid, String errorMessage, Claims claims) {
            this.valid = valid;
            this.errorMessage = errorMessage;
            this.claims = claims;
        }

        public static ValidationResult valid(Claims claims) {
            return new ValidationResult(true, null, claims);
        }

        public static ValidationResult invalid(String errorMessage) {
            return new ValidationResult(false, errorMessage, null);
        }

        public static ValidationResult expired() {
            return new ValidationResult(false, "Token has expired", null);
        }

        public boolean isValid() { return valid; }
        public String getErrorMessage() { return errorMessage; }
        public Claims getClaims() { return claims; }

        public String getUsername() {
            return claims != null ? claims.getSubject() : null;
        }

        public Long getUserId() {
            return claims != null ? claims.get("userId", Long.class) : null;
        }
    }
}