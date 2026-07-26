package com.sarana_NIS.newTown_backend.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sarana_NIS.newTown_backend.dto.response.auth.LoginResponse;
import com.sarana_NIS.newTown_backend.security.jwt.JwtTokenProvider;
import com.sarana_NIS.newTown_backend.security.service.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationSuccessHandler.class);
    private final JwtTokenProvider tokenProvider;
    private final ObjectMapper objectMapper;

    public AuthenticationSuccessHandler(JwtTokenProvider tokenProvider, ObjectMapper objectMapper) {
        this.tokenProvider = tokenProvider;
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        String accessToken = tokenProvider.generateToken(authentication);
        String refreshToken = tokenProvider.generateRefreshToken(authentication);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        LoginResponse loginResponse = new LoginResponse();
        LoginResponse.UserInfo userInfo = new LoginResponse.UserInfo();
        userInfo.setId(userDetails.getId());
        userInfo.setUsername(userDetails.getUsername());
        userInfo.setEmail(userDetails.getEmail());
        userInfo.setFullName(userDetails.getFullName());
        userInfo.setRoles(roles);
        userInfo.setUserStatus(userDetails.getUserStatus().name());
        userInfo.setIsEmailVerified(userDetails.getIsEmailVerified());

        loginResponse.setAccessToken(accessToken);
        loginResponse.setRefreshToken(refreshToken);
        loginResponse.setTokenType("Bearer");
        loginResponse.setExpiresIn(86400000L);
        loginResponse.setUser(userInfo);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(loginResponse));
        response.getWriter().flush();

        log.info("User logged in successfully: {}", userDetails.getUsername());
    }
}