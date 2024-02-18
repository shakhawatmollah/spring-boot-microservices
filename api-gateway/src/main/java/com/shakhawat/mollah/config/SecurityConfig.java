package com.shakhawat.mollah.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    SecurityWebFilterChain apiHttpSecurity(ServerHttpSecurity http) {
        http.csrf(ServerHttpSecurity.CsrfSpec::disable)
//                .securityMatcher(new PathPatternParserServerWebExchangeMatcher("/eureka/**"))
//                .authorizeExchange((exchanges) -> exchanges
//                        .anyExchange().authenticated()
//                )
                .authorizeExchange(exchange ->
                        exchange.pathMatchers("/eureka/**")
                                .permitAll()
                                .anyExchange()
                                .authenticated())
                .oauth2ResourceServer((oauth2) -> oauth2
                    .jwt(Customizer.withDefaults())
                );
               // .oauth2ResourceServer(ServerHttpSecurity.OAuth2ResourceServerSpec::jwt);
        return http.build();
    }

}
