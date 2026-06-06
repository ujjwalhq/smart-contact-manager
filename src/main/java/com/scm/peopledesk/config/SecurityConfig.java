package com.scm.peopledesk.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.scm.peopledesk.services.impl.SecurityCustomUserDetailService;

/*
// Uncomment these imports when using In-Memory Authentication

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
*/

@Configuration
public class SecurityConfig {

    // User create and login using Java code with In-Memory Service

    /*
     * @Bean
     * public UserDetailsService userDetailsService() {
     * 
     * UserDetails user1 = User.withDefaultPasswordEncoder()
     * .username("admin")
     * .password("admin123")
     * .roles("ADMIN", "USER")
     * .build();
     * 
     * UserDetails user2 = User.withDefaultPasswordEncoder()
     * .username("user")
     * .password("user123")
     * .roles("USER")
     * .build();
     * 
     * return new InMemoryUserDetailsManager(user1, user2);
     * }
     */

    @Autowired
    private SecurityCustomUserDetailService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(userDetailsService);

        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);

        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}