package com.njcoder.apigateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoders;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebFluxSecurity
@Slf4j
public class SecurityConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer.uri}")
    private String issuerUri;

    @Bean
    public ReactiveJwtDecoder reactiveJwtDecoder() {
        // Customize this method based on your JWT decoding requirements
        return ReactiveJwtDecoders.fromOidcIssuerLocation(issuerUri);
    }

    @Bean
    public SecurityFilterChain springSecurityFilterChain(ServerHttpSecurity serverHttpSecurity) {
//        log.info("issuer Uri Value {} : ",issuerUri);
        System.out.println("hello this -------------------------------- : " + issuerUri);
        serverHttpSecurity
                .csrf(csrfSpec -> csrfSpec.disable())
                .authorizeExchange(exchange -> exchange.pathMatchers("/eureka/**")
                        .permitAll()
                        .anyExchange()
                        .authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtDecoder(reactiveJwtDecoder())))
                .oauth2Login(withDefaults());
        return (SecurityFilterChain) serverHttpSecurity.build();
    }

}
