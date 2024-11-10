package com.Task.MiniProject_2.secirityConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService(); // Custom service that loads users by username or email
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(configurer -> configurer.disable())

                .authorizeRequests(authorize -> authorize

                        .requestMatchers("/", "/register", "/login").permitAll() // Allow access to register/login
                        .anyRequest().authenticated() // Require authentication for all other requests
                )
                .formLogin(formLogin -> formLogin

                        .loginPage("/login")  // Custom login page
                        .loginProcessingUrl("/login")  // Handle login form submission
                        .usernameParameter("email")  // Use email as the username
                        .defaultSuccessUrl("/home", true)  // Redirect to /home on successful login
                        .permitAll()  // Allow all to access login page
                )
                .logout(logout -> logout

                        .invalidateHttpSession(true)  // Invalidate session on logout
                        .clearAuthentication(true)
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))  // Define logout URL
                        .logoutSuccessUrl("/login")  // Redirect to login after logout
                        .permitAll()  // Allow all to access logout
                );
        return http.build();
    }

}
