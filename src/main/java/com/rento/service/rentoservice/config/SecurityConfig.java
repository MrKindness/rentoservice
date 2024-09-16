package com.rento.service.rentoservice.config;

import com.rento.service.rentoservice.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    @Autowired
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter,
                          AuthenticationProvider authenticationProvider
    ) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(
                        req -> req
                                .requestMatchers(HttpMethod.POST, Constants.API.AUTH.ROOT).permitAll()
                                .requestMatchers(HttpMethod.POST, Constants.API.AUTH.ROOT + Constants.API.AUTH.REGISTER).permitAll()
                                .requestMatchers(HttpMethod.GET, Constants.API.USER.ROOT).hasAnyAuthority("admin", "user")
                                .requestMatchers(HttpMethod.GET, Constants.API.USER.ROOT + Constants.API.USER.ADMIN).hasAuthority("admin")
                                .requestMatchers(HttpMethod.POST, Constants.API.USER.ROOT + Constants.API.USER.ADMIN).hasAuthority("admin")
                                .requestMatchers(HttpMethod.PUT, Constants.API.USER.ROOT + Constants.API.USER.USER_UPDATE).hasAuthority("user")
                                .requestMatchers(HttpMethod.DELETE, Constants.API.USER.ROOT + Constants.API.USER.USER_BY_USERNAME).hasAuthority("admin")
                                .requestMatchers(HttpMethod.GET, Constants.API.TRANSPORT.ROOT + Constants.API.TRANSPORT.AVAILABLE_TRANSPORTS).permitAll()
                                .requestMatchers(HttpMethod.GET, Constants.API.TRANSPORT.ROOT + Constants.API.TRANSPORT.OWNER_TRANSPORTS).hasAuthority("user")
                                .requestMatchers(HttpMethod.GET, Constants.API.TRANSPORT.ROOT + Constants.API.TRANSPORT.RENTED_TRANSPORTS).hasAuthority("user")
                                .requestMatchers(HttpMethod.PUT, Constants.API.TRANSPORT.ROOT + Constants.API.TRANSPORT.RENT_TRANSPORT).hasAuthority("user")
                                .requestMatchers(HttpMethod.POST, Constants.API.TRANSPORT.ROOT).hasAuthority("user")
                                .requestMatchers(HttpMethod.PUT, Constants.API.TRANSPORT.ROOT).hasAuthority("user")
                                .requestMatchers(HttpMethod.DELETE, Constants.API.TRANSPORT.ROOT).hasAnyAuthority("user", "admin")
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(this.authenticationProvider)
                .addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
