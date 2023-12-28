/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.configs.security;

import com.tuantran.IMPROOK_CARE.components.password.PasswordComponent;
import com.tuantran.IMPROOK_CARE.configs.cors.CorsConfig;

import com.tuantran.IMPROOK_CARE.configs.jwt.AuthEntryPointJwt;
import com.tuantran.IMPROOK_CARE.configs.jwt.AuthTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

/**
 *
 * @author Administrator
 */
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

//    @Autowired
//    private UserService userService;
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordComponent passwordService;

    @Autowired
    private CorsConfig corsConfig;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(this.passwordService.PasswordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    private static final String[] AUTH_WHITELIST = {
        "/authenticate",
        "/swagger-resources/**",
        "/swagger-ui/**",
        "/v3/api-docs/**",
        "/api/v1/app/user/auth/"
    };

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.csrf(csrf -> csrf.disable())
//                .cors(cors -> {
//                    cors.configurationSource(request -> {
//                        CorsConfiguration config = new CorsConfiguration();
//                        config.setAllowedOrigins(Arrays.asList(CLIENT_WHITELIST)); // Thay đổi địa chỉ nguồn của bạn tại đây
//                        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
//                        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
//                        config.setAllowCredentials(true); // Không có dòng này mơ mà register được kênh của websocket | Enable sending credentials (e.g., cookies)
//                        return config;
//                    });
//                })
//                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests(auth
//                        -> auth.requestMatchers(AUTH_WHITELIST).permitAll().
//                        requestMatchers("/v3/api-docs").permitAll()
//                        .requestMatchers("/api/public/**").permitAll()
//                        .requestMatchers("/api/auth/admin/**").hasRole("ADMIN")
//                        .requestMatchers("/api/auth/doctor/**").hasRole("DOCTOR")
//                        .requestMatchers("/api/auth/**").hasAnyRole("ADMIN", "DOCTOR", "USER")
//                        .anyRequest().authenticated()
//                );
//
//        http.authenticationProvider(authenticationProvider());
//
//        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
//        return http.build();
//    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, CorsConfigurationSource corsConfigurationSource) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .cors(cors -> {
                    cors.configurationSource(request -> corsConfig.corsConfiguration());
                })
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth
                        -> auth.requestMatchers(AUTH_WHITELIST).permitAll()
                        .requestMatchers("/v3/api-docs").permitAll()
                        .requestMatchers("/api/public/**").permitAll()
                        .requestMatchers("/api/auth/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/auth/doctor/**").hasRole("DOCTOR")
                        .requestMatchers("/api/auth/**").hasAnyRole("ADMIN", "DOCTOR", "USER")
                        .anyRequest().authenticated()
                );

        http.authenticationProvider(authenticationProvider());

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
