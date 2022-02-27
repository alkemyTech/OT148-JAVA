package com.alkemy.ong.config;

import com.alkemy.ong.repository.OrganizationRepository;
import com.alkemy.ong.repository.RoleRepository;
import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.security.UserDetailsServiceImpl;
import com.alkemy.ong.service.EmailService;
import com.alkemy.ong.service.OrganizationService;
import com.alkemy.ong.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {

    @Bean
    public UserService userService(UserRepository userRepository,
                                   RoleRepository roleRepository,
                                   PasswordEncoder passwordEncoder) {
        return new UserService(userRepository,
                roleRepository,
                passwordEncoder);
    }

    @Bean
    public EmailService emailService(@Value("${sendgrid.api.key}") String apiKey, @Value("${alkemy.ong.email.sender}") String emailSender) {
        return new EmailService(apiKey, emailSender);
    }

    @Bean
    public OrganizationService organizationService(OrganizationRepository organizationRepository) {
        return new OrganizationService(organizationRepository);
    }

    @Bean
    public UserDetailsServiceImpl userDetailsServiceImpl(UserRepository userRepository) {
        return new UserDetailsServiceImpl(userRepository);
    }
}

