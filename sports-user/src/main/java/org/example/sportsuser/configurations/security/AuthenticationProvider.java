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
import java.util.stream.Collectors;

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

    public String createAccessToken(UUID userId, String username, Set<Role> roles) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userId.toString());
        claims.put("roles", roles);

        Instant validity = Instant.now().plus(props.getAccess(), ChronoUnit.HOURS);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setExpiration(Date.from(validity))
                .signWith(key)
                .compact();
    }

    public String createRefreshToken(UUID userId, String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userId.toString());

        Instant validity = Instant.now().plus(props.getRefresh(), ChronoUnit.HOURS);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setExpiration(Date.from(validity))
                .signWith(key)
                .compact();
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        String id = getIdFromToken(token);
        String username = getUsernameFromToken(token);
        List<GrantedAuthority> authorities = getRolesFromToken(token);
        return new UsernamePasswordAuthenticationToken(id, username, authorities);
    }

    public String getIdFromToken(String token) {
        return parseClaims(token).get("id", String.class);
    }

    private String getUsernameFromToken(String token) {
        return parseClaims(token).getSubject();
    }

    private List<GrantedAuthority> getRolesFromToken(String token) {
        List<?> roles = parseClaims(token).get("roles", List.class);
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.toString()))
                .collect(Collectors.toList());
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
}