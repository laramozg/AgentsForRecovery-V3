package org.example.sportsuser.mappers;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.sportsuser.utils.Models.USER;

class UserMapperTest {
    private final UserMapper userMapper = new UserMapper();

    @Test
    void convertShouldExecuteSuccessfully() {

        var result = userMapper.convert(USER);

        assertThat(result.username()).isEqualTo(USER.getUsername());
        assertThat(result.role()).isEqualTo(USER.getRole().name());
    }
}
