package org.example.sportsfight.configurations.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class AuthFeignInterceptor implements RequestInterceptor {

    private static final String USER_ID = "UserId";
    private static final String USERNAME = "Username";
    private static final String USER_ROLES = "UserRoles";

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header(USER_ID, UUID.randomUUID().toString());
        requestTemplate.header(USERNAME, "sports-fight");
        requestTemplate.header(USER_ROLES, List.of("SUPERVISOR", "EXECUTOR", "CUSTOMER").toString());
    }
}