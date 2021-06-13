package com.kamronbek.auth.config;

import com.kamronbek.auth.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Authentication is HTTP Basic
        http.httpBasic();

        // Configuring CORS
        http.cors().configurationSource(httpServletRequest -> {
           CorsConfiguration config = new CorsConfiguration();

           // Setting Allowed Origins
           config.setAllowedOrigins(List.of("*"));

           // Setting Allowed Methods
           config.setAllowedMethods(List.of("*"));
           config.setAllowCredentials(true);
           config.setAllowedHeaders(List.of("*"));
           config.setMaxAge(3600L);
           return config;
        });

        // Disabling CSRF
        http.csrf().disable();

        // Permitted URLs
        http.authorizeRequests()
                .mvcMatchers(HttpMethod.POST, "/api/v*/users/signup/").permitAll()
                .antMatchers("/api/v*/users/confirm/**").anonymous();

        // Restricted URLs
        http.authorizeRequests().anyRequest().authenticated().and().formLogin();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userService);
        return provider;
    }
}
