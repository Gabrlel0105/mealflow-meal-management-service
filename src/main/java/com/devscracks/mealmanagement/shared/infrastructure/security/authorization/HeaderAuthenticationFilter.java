package com.devscracks.mealmanagement.shared.infrastructure.security.authorization;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * Authenticates incoming requests based on the {@code X-User-Id} and {@code X-User-Role} headers.
 *
 * <p>This service sits behind an upstream API Gateway that is responsible for validating the
 * caller's credentials (e.g. a JWT) and forwarding the resolved user identity and role as plain
 * headers. This filter simply trusts those headers and builds the Spring Security context from
 * them so that {@code @PreAuthorize} / {@code hasRole} checks work downstream.</p>
 */
@Component
public class HeaderAuthenticationFilter extends OncePerRequestFilter {

    public static final String USER_ID_HEADER = "X-User-Id";
    public static final String USER_ROLE_HEADER = "X-User-Role";

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        var userId = request.getHeader(USER_ID_HEADER);
        var userRole = request.getHeader(USER_ROLE_HEADER);

        if (userId != null && !userId.isBlank() && userRole != null && !userRole.isBlank()) {
            var authority = userRole.startsWith("ROLE_") ? userRole : "ROLE_" + userRole;
            var authentication = new UsernamePasswordAuthenticationToken(
                    userId,
                    null,
                    List.of(new SimpleGrantedAuthority(authority)));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
