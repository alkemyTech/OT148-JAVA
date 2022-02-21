package com.alkemy.ong.config;

import com.alkemy.ong.repository.RoleRepository;
import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.service.EmailService;
import com.alkemy.ong.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserService userService(UserRepository userRepository,
                                   RoleRepository roleRepository,
                                   PasswordEncoder passwordEncoder){
        return new UserService(userRepository,
                roleRepository,
                passwordEncoder);
    }

    @Bean
    public EmailService emailService(String to){
        return new EmailService(to);
    }

}

