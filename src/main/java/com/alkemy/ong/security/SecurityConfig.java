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

    private static final String[] PERMIT_ALL = {"/auth/register", "/auth/login", "/h2/**", "/swagger-ui/**"};

    private static final String[] GET_USER = {"/posts/**", "/auth/me", "/posts/**"};

    private static final String[] GET_ADMIN = {"/users", "/categories/**", "/members/**", "/news/**", "/comments","/testimonials/**"};

    private static final String[] DELETE_USER = {"/user/{userId}", "/comments/**"};

    private static final String[] DELETE_ADMIN = {"/categories/**", "/members/**", "/news/**"};

    private static final String[] PATCH_USER = {"/user/{userId}"};

    private static final String[] PATCH_ADMIN = {"/user/{userId}"};

    private static final String[] POST_USER = {"/comments/**"};

    private static final String[] POST_ADMIN = {"/contacts", "/slides/**", "/testimonials/**", "/categories/**", "/members/**", "/news/**", "/activities"};

    private static final String[] PUT_USER = {"/comments/**"};

    private static final String[] PUT_ADMIN = {"/categories/**", "/members/**", "/news/**"};

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
                .antMatchers(PERMIT_ALL).permitAll()
                .and()
                .addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, GET_USER).hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.DELETE, DELETE_USER).hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.PATCH, PATCH_USER).hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, POST_USER).hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.PUT, PUT_USER).hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.GET, GET_ADMIN).hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, DELETE_ADMIN).hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.PATCH, PATCH_ADMIN).hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, POST_ADMIN).hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, PUT_ADMIN).hasAnyAuthority("ADMIN")
                .anyRequest().authenticated();
    }
}

