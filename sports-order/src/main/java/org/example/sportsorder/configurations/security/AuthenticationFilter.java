package org.example.sportsorder.configurations.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws IOException, ServletException {
        String userId = request.getHeader(USER_ID);
        String username = request.getHeader(USERNAME);
        String userRole  = request.getHeader(USER_ROLES);

        if (userId != null && username != null && !username.isBlank() && userRole  != null) {
            SimpleGrantedAuthority authority  = new SimpleGrantedAuthority(userRole);

            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(userId, username, List.of(authority))
            );
        }

        filterChain.doFilter(request, response);
    }

    private static final String USER_ID = "UserId";
    private static final String USERNAME = "Username";
    private static final String USER_ROLES = "UserRoles";
}