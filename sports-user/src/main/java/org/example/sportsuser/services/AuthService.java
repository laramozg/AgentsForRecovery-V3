package org.example.sportsuser.services;

public class AuthService {
    private final UserService userService;
    private final PasswordEncoder encoder;
    private final AuthenticationProvider authProvider;
}
