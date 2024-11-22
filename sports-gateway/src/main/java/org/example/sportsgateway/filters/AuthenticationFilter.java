package org.example.sportsgateway.filters;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.sportsgateway.configurations.ServiceUrlsProperties;
import org.example.sportsgateway.dto.AuthorizationDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);
    private final WebClient.Builder client;
    private final ServiceUrlsProperties props;
    private final ObjectMapper objectMapper;

    public AuthenticationFilter(WebClient.Builder client, ServiceUrlsProperties props, ObjectMapper objectMapper) {
        super(Config.class);
        this.client = client;
        this.props = props;
        this.objectMapper = objectMapper;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            logger.info("Processing auth request - {}", exchange.getRequest().getURI().getPath());
            String bearerToken = exchange.getRequest().getHeaders().getFirst(Config.AUTHORIZATION);
            logger.info(bearerToken);
            if (bearerToken != null) {
                return client.build()
                        .get()
                        .uri("lb://" + props.getSportsUser() + "/auth")
                        .accept(MediaType.APPLICATION_JSON)
                        .header(Config.AUTHORIZATION, bearerToken)
                        .retrieve()
                        .bodyToMono(AuthorizationDetails.class)
                        .flatMap(response -> {
                            logger.info("id   {}", response.id().toString());
                            logger.info(response.username());
                            logger.info(response.roles().get(0));
                            logger.info("пупупум");
                            ServerHttpRequest mutableRequest = null;
                            try {
                                mutableRequest = exchange.getRequest().mutate()
                                        .header(Config.USER_ID, String.valueOf(response.id()))
                                        .header(Config.USERNAME, response.username())
                                        .header(Config.USER_ROLES, objectMapper.writeValueAsString(response.roles()))
                                        .build();
                            } catch (JsonProcessingException e) {
                                return reactor.core.publisher.Mono.error(new RuntimeException(e));
                            }

                            return chain.filter(exchange.mutate().request(mutableRequest).build());
                        });
            } else {
                return chain.filter(exchange);
            }
        };
    }

    public static class Config {
        public static final String USER_ID = "UserId";
        public static final String USERNAME = "Username";
        public static final String USER_ROLES = "UserRoles";
        public static final String AUTHORIZATION = "Authorization";

        public Config() {}
    }
}