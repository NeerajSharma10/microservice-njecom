package com.njcoder.discoveryserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private static final String USERNAME = "user";
    private static final String PASSWORD = "password";

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
//        http
//                .httpBasic(Customizer.withDefaults())
//                .authorizeExchange(exchanges -> exchanges
//                        .anyExchange().authenticated())
//                .userDetails()
//                .build();
//
//        return http.build();
        return new SecurityWebFilterChain() {
            @Override
            public Mono<Boolean> matches(ServerWebExchange exchange) {
                return null;
            }

            @Override
            public Flux<WebFilter> getWebFilters() {
                return null;
            }
        };
    }

    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        UserDetails user = User.withUsername(USERNAME).password(PASSWORD).roles("USER").build();
        return new MapReactiveUserDetailsService(user);
    }

}