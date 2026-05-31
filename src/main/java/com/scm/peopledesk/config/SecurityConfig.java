package com.scm.peopledesk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig {
    //user create and login using java code with in memory service


    @Bean
    public UserDetailsService userDetailsService(){

      //  DefaultPasswordEncoder is not recommended for production use, but it's fine for testing and development purposes. It uses a simple hashing algorithm that is not as secure as more modern password encoders like BCryptPasswordEncoder or Argon2PasswordEncoder. In production, you should consider using a stronger password encoder to ensure better security for your users' passwords.
       UserDetails user1 = User.withDefaultPasswordEncoder().username("admin").password("admin123").roles("ADMIN","USER").build();

       UserDetails user2 = User.withDefaultPasswordEncoder().username("user").password("user123").roles("USER").build();

        //InMemoryUserDetailsManager is a simple implementation of the UserDetailsService interface that stores user details in memory. It is useful for testing and development purposes, but it is not recommended for production use because it does not provide any persistence mechanism for user data. In a production application, you would typically use a more robust implementation of UserDetailsService that retrieves user details from a database or another external source.
        var inMemoryDetailsManager = new InMemoryUserDetailsManager(user1, user2);
        return inMemoryDetailsManager;
        
    }


}
