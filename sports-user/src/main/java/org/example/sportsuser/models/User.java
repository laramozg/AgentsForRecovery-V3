package org.example.sportsuser.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.sportsuser.models.enums.Role;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    private String username;
    @Column(nullable = false)
    private String nick;
    @Column(nullable = false)
    private String telegram;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private AuthData authorizationData;
}
