package org.example.sportsuser.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "auth_data")
public class AuthData {
    @Id
    private String username;
    private String password;

    @PrimaryKeyJoinColumn(name = "username", referencedColumnName = "username")
    @OneToOne(fetch = FetchType.LAZY)
    private User user;

}