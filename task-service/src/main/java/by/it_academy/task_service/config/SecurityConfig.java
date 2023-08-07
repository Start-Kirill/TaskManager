package by.it_academy.task_service.config;

import by.it_academy.task_service.endpoints.web.filters.JwtFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtFilter filter) throws Exception {

        // Enable CORS and disable CSRF
        http = http.csrf(AbstractHttpConfigurer::disable);


        // Set session management to stateless
        http = http
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));


        // Set unauthorized requests exception handler
        http = http
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling
                                .authenticationEntryPoint(
                                        (request, response, ex) -> {
                                            response.setStatus(
                                                    HttpServletResponse.SC_UNAUTHORIZED
                                            );
                                        }
                                )
                                .accessDeniedHandler((request, response, ex) -> {
                                    response.setStatus(
                                            HttpServletResponse.SC_FORBIDDEN
                                    );
                                }));

        // Set permissions on endpoints
//        http.authorizeHttpRequests(requests -> requests
//                .requestMatchers("/users/login").permitAll()
//                .requestMatchers("users/registration").permitAll()
//                .requestMatchers("users/verification/*").permitAll()
//                .requestMatchers("users/verification").permitAll()
//                .requestMatchers("/users/me").authenticated()
//                .requestMatchers("/users").hasAnyAuthority("ROLE_ADMIN")
//                .requestMatchers("/users/*/dt_update/*").hasAnyAuthority("ROLE_ADMIN")
//                .requestMatchers("users/*").authenticated()
//                .anyRequest().authenticated()
//        );

        // Add JWT token filter
        http.addFilterBefore(
                filter,
                UsernamePasswordAuthenticationFilter.class
        );

        return http.build();
    }
}
