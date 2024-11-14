package org.example.sportsuser.mappers;

import org.example.sportsuser.controllers.auth.dto.AuthorizeUserRequest;
import org.example.sportsuser.controllers.auth.dto.RegisterUserRequest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AuthMapperTest {
    private final AuthMapper authMapper = new AuthMapper();

    @Test
    void convertRegisterUserRequestShouldExecuteSuccessfully() {
        RegisterUserRequest request = new RegisterUserRequest("username", "password", "user", "@user", "CUSTOMER");

        var result = authMapper.convert(request);

        assertThat(result.getUsername()).isEqualTo(request.username());
        assertThat(result.getPassword()).isEqualTo(request.password());
        assertThat(result.getRole().name()).isEqualTo(request.role());
    }

    @Test
    void convertAuthorizeUserRequestShouldExecuteSuccessfully() {
        AuthorizeUserRequest request = new AuthorizeUserRequest("username", "password");

        var result = authMapper.convert(request);

        assertThat(result.getUsername()).isEqualTo(request.username());
        assertThat(result.getPassword()).isEqualTo(request.password());
    }
}
