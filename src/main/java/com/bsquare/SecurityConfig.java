package com.bsquare;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // Marks this class as a configuration class for Spring context
public class SecurityConfig {

    // This method is used to configure password encryption (BCrypt) for user passwords
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Returns a BCryptPasswordEncoder bean for password encryption
    }

    // This method is used to configure HTTP security for the application
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        
        // Configures which HTTP requests should be permitted without authentication
        // In this case, all requests ("/**") are permitted without authentication
        http.authorizeHttpRequests((req) -> req.requestMatchers("/**").permitAll().anyRequest().authenticated());
        
        // Disables CSRF protection (not recommended for production applications without extra precautions)
        http.csrf(csrf -> csrf.disable());
        
        // Builds the security filter chain and returns it
        return http.build();
    }
}
