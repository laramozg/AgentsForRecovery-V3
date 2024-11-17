package org.example.sportsuser.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.example.sportsuser.models.enums.Role;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    private UUID id;

    @Column("username")
    private String username;
    @Column("password")
    private String password;
    @Column("nick")
    private String nick;
    @Column("telegram")
    private String telegram;
    @Column("confirmed")
    private boolean confirmed = false;
    @Column("confirmed_username")
    private boolean confirmedUsername = false;
    @Column("blocked")
    private boolean blocked = false;
    @Column("role")
    private Role role = Role.CUSTOMER;

}