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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // ✅ Pages publiques sans login
                .requestMatchers(
                    new AntPathRequestMatcher("/"),
                    new AntPathRequestMatcher("/accueil"),
                    new AntPathRequestMatcher("/login"),
                    new AntPathRequestMatcher("/register"),
                    new AntPathRequestMatcher("/css/**"),
                    new AntPathRequestMatcher("/js/**"),
                    new AntPathRequestMatcher("/images/**"),
                    // ✅ Confirmation RDV sans login
                    new AntPathRequestMatcher("/rdv/confirmer"),
                    new AntPathRequestMatcher("/rdv/annuler-email")
                ).permitAll()
                // Pages admin seulement
                .requestMatchers(
                    new AntPathRequestMatcher("/admin/**"))
                    .hasRole("ADMIN")
                // Pages médecin et admin
                .requestMatchers(
                    new AntPathRequestMatcher("/medecins/**"))
                    .hasAnyRole("ADMIN", "MEDECIN")
                // Pages secrétaire, médecin et admin
                .requestMatchers(
                    new AntPathRequestMatcher("/patients/**"))
                    .hasAnyRole("ADMIN", "MEDECIN", "SECRETAIRE")
                // RDV accessible à tous les connectés
                .requestMatchers(
                    new AntPathRequestMatcher("/rendezvous/**"))
                    .hasAnyRole("ADMIN", "MEDECIN",
                        "SECRETAIRE", "PATIENT")
                // Tout le reste nécessite connexion
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/dashboard", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutRequestMatcher(
                    new AntPathRequestMatcher("/logout"))
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
