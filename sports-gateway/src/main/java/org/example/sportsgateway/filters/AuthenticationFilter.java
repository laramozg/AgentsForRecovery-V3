package org.example.sportsgateway.filters;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.sportsgateway.configurations.ServiceUrlsProperties;
import org.example.sportsgateway.dto.AuthorizationDetails;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final WebClient.Builder client;
    private final ServiceUrlsProperties props;

    public AuthenticationFilter(WebClient.Builder client, ServiceUrlsProperties props) {
        super(Config.class);
        this.client = client;
        this.props = props;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String bearerToken = exchange.getRequest().getHeaders().getFirst(Config.AUTHORIZATION);

            if (bearerToken != null) {
                return client.build().get()
                        .uri("lb://" + props.getSportsUser() + "/auth")
                        .accept(MediaType.APPLICATION_JSON)
                        .header(Config.AUTHORIZATION, bearerToken)
                        .retrieve()
                        .bodyToMono(AuthorizationDetails.class)
                        .flatMap(response -> {
                            ServerWebExchange mutatedExchange = exchange.mutate()
                                    .request(builder -> {
                                        try {
                                            builder
                                                    .header(Config.USERNAME, response.getUsername())
                                                    .header(Config.USER_ROLE, new ObjectMapper().writeValueAsString(response.getRole()));
                                        } catch (JsonProcessingException e) {
                                            throw new RuntimeException("Failed to convert object to JSON", e);
                                        }
                                    })
                                    .build();
                            return chain.filter(mutatedExchange);
                        });
            } else {
                return chain.filter(exchange);
            }
        };
    }

    public static class Config {
        public static final String USERNAME = "Username";
        public static final String USER_ROLE = "UserRole";
        public static final String AUTHORIZATION = "Authorization";

        public Config() {}
    }
}