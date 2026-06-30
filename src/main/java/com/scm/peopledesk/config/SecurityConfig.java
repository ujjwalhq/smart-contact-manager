package com.scm.peopledesk.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.AuthenticationException;

import com.scm.peopledesk.helpers.Message;
import com.scm.peopledesk.helpers.MessageType;
import com.scm.peopledesk.services.impl.SecurityCustomUserDetailService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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
    private OAuthAuthenicationSuccessHandler handler;

    // configuraiton of authentication provider for spring security
    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(userDetailsService);

        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        // configuration
        // urls configuration for which one is public and which one is private
        httpSecurity.authorizeHttpRequests(authrize -> {
            // authrize.requestMatchers("/","/signup","/login","/services","/about").permitAll();
            authrize.requestMatchers("/user/**").authenticated();
            authrize.anyRequest().permitAll();
        });

        // form default login configuration
        // if we want to change something in default login page then we can do that here
        httpSecurity.formLogin(formLogin -> {
            formLogin.loginPage("/login");
            formLogin.loginProcessingUrl("/authenticate");
            formLogin.successForwardUrl("/user/dashboard");
            formLogin.failureUrl("/login?error=true");
            // formLogin.defaultSuccessUrl("home");
            formLogin.usernameParameter("email");
            formLogin.passwordParameter("password");

            // formLogin.failureHandler(new AuthenticationFailureHandler() {

            // @Override
            // public void onAuthenticationFailure(HttpServletRequest request,
            // HttpServletResponse response,
            // AuthenticationException exception) throws IOException, ServletException {
            // // TODO Auto-generated method stub
            // throw new UnsupportedOperationException("Unimplemented method
            // 'onAuthenticationFailure'");
            // }

            // });

            // formLogin.successHandler(new AuthenticationSuccessHandler() {

            // @Override
            // public void onAuthenticationSuccess(HttpServletRequest request,
            // HttpServletResponse response,
            // Authentication authentication) throws IOException, ServletException {
            // // TODO Auto-generated method stub
            // throw new UnsupportedOperationException("Unimplemented method
            // 'onAuthenticationSuccess'");
            // }

            // });

            formLogin.failureHandler(new AuthenticationFailureHandler() {

                @Override
                public void onAuthenticationFailure(
                        HttpServletRequest request,
                        HttpServletResponse response,
                        AuthenticationException exception)
                        throws IOException, ServletException {

                    HttpSession session = request.getSession();

                    if (exception instanceof DisabledException) {

                        session.setAttribute(
                                "message",
                                Message.builder()
                                        .content(
                                                "Your account is not verified. Please verify your email before logging in.")
                                        .type(MessageType.red)
                                        .build());

                        response.sendRedirect("/login");

                    } else {

                        session.setAttribute(
                                "message",
                                Message.builder()
                                        .content("Invalid email or password.")
                                        .type(MessageType.red)
                                        .build());

                        response.sendRedirect("/login");
                    }
                }
            });

        });

        httpSecurity.csrf(csrf -> csrf.disable());
        httpSecurity.logout(logout -> {
            logout.logoutUrl("/logout");
            logout.logoutSuccessUrl("/login?logout=true");
        });

        // oauth2 login configuration
        httpSecurity.oauth2Login(oauth2Login -> {
            oauth2Login.loginPage("/login");
            oauth2Login.successHandler(handler);
            // oauth2Login.defaultSuccessUrl("/user/dashboard");

        });

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}