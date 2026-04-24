package com.fst.cabinet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(
            CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // Pages publiques sans login
                .requestMatchers("/", "/accueil",
                    "/login", "/register",
                    "/css/**", "/js/**").permitAll()
                // Pages admin seulement
                .requestMatchers("/admin/**")
                    .hasRole("ADMIN")
                // Pages médecin et admin
                .requestMatchers("/medecins/**")
                    .hasAnyRole("ADMIN", "MEDECIN")
                // Pages secrétaire, médecin et admin
                .requestMatchers("/patients/**")
                    .hasAnyRole("ADMIN", "MEDECIN", "SECRETAIRE")
                // RDV accessible à tous les connectés
                .requestMatchers("/rendezvous/**")
                    .hasAnyRole("ADMIN", "MEDECIN",
                        "SECRETAIRE", "PATIENT")
                // Tout le reste nécessite connexion
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                // Redirection selon le rôle après login
                .defaultSuccessUrl("/dashboard", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/accueil")
                .permitAll()
            );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder = http
            .getSharedObject(
                AuthenticationManagerBuilder.class);
        builder
            .userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder());
        return builder.build();
    }
}
