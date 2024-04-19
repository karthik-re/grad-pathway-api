package org.example.gradpathway.security;

import org.example.gradpathway.filter.CsrfCookieFilter;
import org.example.gradpathway.filter.JWTTokenGeneratorFilter;
import org.example.gradpathway.filter.JWTTokenValidatorFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain customSecurityFilterChain(HttpSecurity http) throws Exception {
        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();

        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(HttpMethod.GET, "/api/company/**", "/api/review/**", "/api/jobs/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/company/**", "/api/jobs/**").hasAnyAuthority("EMPLOYER", "ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/company/**", "/api/jobs/**").hasAnyAuthority("EMPLOYER", "ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/company/**", "/api/jobs/**").hasAnyAuthority("EMPLOYER", "ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/review/**").hasAnyAuthority("STUDENT", "ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/review/**").hasAnyAuthority("STUDENT", "ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/review/**").hasAnyAuthority("STUDENT", "ADMIN")
                                .requestMatchers("/api/auth/register").permitAll()
                                .requestMatchers("/api/auth/all", "/api/auth/delete/**","/api/auth/user/**").hasAuthority("ADMIN")
                                .requestMatchers("/api/auth/login", "/api/userdata/**","/api/auth/profile").authenticated()
                                .requestMatchers("/api/auth/addCompany/**").hasAnyAuthority("EMPLOYER", "ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/userdata/**").hasAuthority("ADMIN")
                                .anyRequest().permitAll()
                )
                .cors(cors -> cors
                        .configurationSource(request -> {
                            CorsConfiguration corsConfiguration = new CorsConfiguration();
                            corsConfiguration.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
                            corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
                            corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
                            corsConfiguration.setAllowCredentials(true);
                            corsConfiguration.setExposedHeaders(List.of("Authorization"));
                            corsConfiguration.setMaxAge(3600L);
                            return corsConfiguration;
                        })
                )
                .csrf(csrf -> csrf.csrfTokenRequestHandler(requestHandler)
                        .ignoringRequestMatchers("/api/auth/register")
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)//add csrf cookie to response before sending
                .addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)//add jwt token to response before sending
                .addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)//validate jwt token before processing request
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
