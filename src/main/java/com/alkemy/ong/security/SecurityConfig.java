package com.alkemy.ong.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    JwtEntryPoint jwtEntryPoint;

    @Bean
    public JwtTokenFilter jwtTokenFilter() {
        return new JwtTokenFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private static final String[] AUTH_WHITELIST = {
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v2/api-docs",
            "/webjars/**",
            "/api/docs"
    };

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(AUTH_WHITELIST);
    }

    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/auth/register", "/auth/login", "/h2/**", "/swagger-ui/**").permitAll()
                .and()
                .addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/users").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/user/{userId}").hasAnyAuthority("ADMIN", "USER")
                .antMatchers(HttpMethod.PATCH, "/user/{userId}").hasAnyAuthority("ADMIN", "USER")
                .antMatchers(HttpMethod.POST, "/contacts").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/slides/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/testimonials/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/organization/{id}").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/categories/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/categories/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/categories/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/categories/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/members/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/members/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/members/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/members/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/news/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/news/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/news/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/news/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/activities").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/comments").hasAnyAuthority("ADMIN")
                .antMatchers("/organization/**", "/auth/me").authenticated()
                .anyRequest().authenticated();
    }
}
