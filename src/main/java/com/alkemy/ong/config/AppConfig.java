package com.alkemy.ong.config;

import com.alkemy.ong.repository.NewsRepository;
import com.alkemy.ong.repository.CategoryRepository;
import com.alkemy.ong.repository.OrganizationRepository;
import com.alkemy.ong.repository.RoleRepository;
import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.security.UserDetailsServiceImpl;
import com.alkemy.ong.service.AmazonService;
import com.alkemy.ong.service.CategoryService;
import com.alkemy.ong.service.EmailService;
import com.alkemy.ong.service.NewsService;
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
                                   PasswordEncoder passwordEncoder,
                                   AmazonService amazonService,
                                   EmailService emailTemplate) {
        return new UserService(userRepository,
                roleRepository,
                passwordEncoder,
                amazonService,
                emailTemplate);
    }

    @Bean
    public String emailTemplate() throws IOException {
        File template = ResourceUtils.getFile("classpath:template/plantilla_email.html");
        return new String(Files.readAllBytes(template.toPath()));
    }

    @Bean
    public EmailService emailService(
            @Value("${sendgrid.api.key}") String apiKey,
            @Value("${alkemy.ong.email.sender}") String emailSender,
            String emailTemplate) {
        return new EmailService(apiKey, emailSender, emailTemplate);
    }

    @Bean
    public OrganizationService organizationService(
            OrganizationRepository organizationRepository,
            AmazonService amazonService) {
        return new OrganizationService(organizationRepository, amazonService);
    }

    @Bean
    public CategoryService categoryService(CategoryRepository categoryRepository) {
        return new CategoryService(categoryRepository);
    }

    @Bean
    public AmazonService amazonService(
            @Value("${aws.s3.bucketName}") String bucketName,
            @Value("${aws.s3.accessKey}") String accessKey,
            @Value("${aws.s3.secretKey}") String secretKey,
            @Value("${aws.s3.endpointUrl}") String endpointUrl
    ) {
        return new AmazonService(bucketName, accessKey, secretKey, endpointUrl);
    }

    @Bean
    public UserDetailsServiceImpl userDetailsServiceImpl(UserRepository userRepository) {
        return new UserDetailsServiceImpl(userRepository);
    }
    @Bean
    public NewsService newsService(NewsRepository newsRepository) {
        return new NewsService(newsRepository);
    }
}