package com.scotiabank.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.scotiabank.demo.filters.JwtAuthentificationFilter;
import com.scotiabank.demo.util.JwtUtil;




@Configuration
@EnableWebSecurity
public class SecurityConfig implements WebMvcConfigurer {

    private final JwtUtil jwtUtil;

    public SecurityConfig(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()  // Disable CSRF if needed (e.g., for non-browser clients)
            .authorizeRequests()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers("/user/register", "/user/login").permitAll()  // Allow public access to register
                .requestMatchers("/user").authenticated() // Requiere autenticación
                .requestMatchers("/user/{id}").authenticated() // Requiere autenticación
                .requestMatchers("/user/email/{email}").authenticated() // Requiere autenticación
                .requestMatchers("/housing").authenticated() // Requiere autenticación
                .requestMatchers("/housing/{id}").authenticated() // Requiere autenticación
                .requestMatchers("/housing/user/{userId}").authenticated() // Requiere autenticación
                .anyRequest().authenticated()  // Other endpoints require authentication
            .and()
            .addFilterBefore(new JwtAuthentificationFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // Example user details service using in-memory authentication
        User.UserBuilder users = User.withDefaultPasswordEncoder();
        return new InMemoryUserDetailsManager(
            users.username("user").password("password").roles("USER").build()
        );
    }
}