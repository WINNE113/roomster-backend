package com.roomster.roomsterbackend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;

    private final AuthenticationProvider authenticationProvider;

    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS,"/**").permitAll()
                        .requestMatchers(
                                "/api/v1/auth/**",
                                "/swagger-ui/**",
                                "/api/v1/auth/**",
                                "/v2/api-docs",
                                "/v3/api-docs",
                                "/v3/api-docs/**",
                                "/swagger-resources",
                                "/swagger-resources/**",
                                "/webjars/**",
                                "/api/v1/post/**",
                                "/swagger-ui.html")
                            .permitAll()
                            .anyRequest()
                            .authenticated()
                )
                .sessionManagement((sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout()
                .logoutUrl("/api/v1/auth/logout")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler(((request, response, authentication)
                        -> SecurityContextHolder.clearContext()));
        return http.build();
    }
//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(List.of("http://localhost:5175"));
//        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PUT","OPTIONS","PATCH", "DELETE"));
//        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type", "Origin"));
//        configuration.setAllowCredentials(true);
//        configuration.setExposedHeaders(List.of("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }

}
