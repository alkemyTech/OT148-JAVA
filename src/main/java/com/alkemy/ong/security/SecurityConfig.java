package com.alkemy.ong.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable()
                .cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
                .and()
                .csrf().disable();

        http.authorizeRequests().antMatchers(HttpMethod.POST).hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.GET).hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/users/{Id}").access("@userSecurity.hasUserId(authentication, #Id)")
                .antMatchers(HttpMethod.PUT).hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.DELETE).hasAnyRole("ADMIN");

        http.csrf().disable();
        http.headers().frameOptions().disable();
        http.csrf().disable().authorizeRequests().antMatchers("/auth/register/**", "/h2/**", "/users").permitAll().anyRequest().authenticated();
    }
}
