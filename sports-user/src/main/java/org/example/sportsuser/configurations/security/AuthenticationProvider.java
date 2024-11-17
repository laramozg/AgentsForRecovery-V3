package org.example.sportsuser.configurations.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.example.sportsuser.models.enums.Role;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Component
public class AuthenticationProvider {

    private final AuthenticationProperties props;
    private SecretKey key;

    public AuthenticationProvider(AuthenticationProperties props) {
        this.props = props;
    }

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(props.getSecret().getBytes());
    }

    public boolean isValid(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        return claims.getExpiration().after(new Date());
    }


    public String createAccessToken(UUID userId, String username, Role role) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("id", userId);
        claims.put("role", role.name());

        Instant validity = Instant.now().plus(props.getAccess(), ChronoUnit.HOURS);
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(Date.from(validity))
                .signWith(key)
                .compact();
    }

    public String createRefreshToken(UUID userId, String username) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("id", userId);

        Instant validity = Instant.now().plus(props.getRefresh(), ChronoUnit.HOURS);
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(Date.from(validity))
                .signWith(key)
                .compact();
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        String id = getIdFromToken(token);
        String username = getUsernameFromToken(token);
        GrantedAuthority authority = getRoleFromToken(token);
        return new UsernamePasswordAuthenticationToken(id, username, Collections.singletonList(authority));
    }

    public String getIdFromToken(String token) {
        return parseClaims(token).get("id", String.class);
    }

    public String getUsernameFromToken(String token) {
        return parseClaims(token).getSubject();
    }

    private GrantedAuthority getRoleFromToken(String token) {
        String role = parseClaims(token).get("role", String.class);
        return new SimpleGrantedAuthority(role);
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
}