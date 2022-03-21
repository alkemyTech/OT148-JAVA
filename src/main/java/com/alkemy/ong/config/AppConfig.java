package com.alkemy.ong.config;

import com.alkemy.ong.repository.ActivityRepository;
import com.alkemy.ong.repository.CategoryRepository;
import com.alkemy.ong.repository.CommentRepository;
import com.alkemy.ong.repository.ContactRepository;
import com.alkemy.ong.repository.MemberRepository;
import com.alkemy.ong.repository.NewsRepository;
import com.alkemy.ong.repository.OrganizationRepository;
import com.alkemy.ong.repository.RoleRepository;
import com.alkemy.ong.repository.SlideRepository;
import com.alkemy.ong.repository.TestimonialRepository;
import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.security.JwtProvider;
import com.alkemy.ong.security.UserDetailsServiceImpl;
import com.alkemy.ong.service.ActivityService;
import com.alkemy.ong.service.AmazonService;
import com.alkemy.ong.service.CategoryService;
import com.alkemy.ong.service.CommentService;
import com.alkemy.ong.service.ContactService;
import com.alkemy.ong.service.EmailService;
import com.alkemy.ong.service.MemberService;
import com.alkemy.ong.service.NewsService;
import com.alkemy.ong.service.OrganizationService;
import com.alkemy.ong.service.SlideService;
import com.alkemy.ong.service.TestimonialService;
import com.alkemy.ong.service.UserService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ResourceUtils;

@Configuration
public class AppConfig {

    @Bean
    public UserService userService(UserRepository userRepository,
                                   RoleRepository roleRepository,
                                   PasswordEncoder passwordEncoder,
                                   AmazonService amazonService,
                                   EmailService emailTemplate,
                                   JwtProvider jwtProvider,
                                   AuthenticationManager authenticationManager) {
        return new UserService(userRepository,
                roleRepository,
                passwordEncoder,
                amazonService,
                emailTemplate,
                jwtProvider,
                authenticationManager);
    }

    @Bean
    public String emailTemplate() throws IOException {
        File template = ResourceUtils.getFile("classpath:template/plantilla_email.html");
        return new String(Files.readAllBytes(template.toPath()));
    }

    @Bean
    public String emailTemplateContact() throws IOException {
        File templateC = ResourceUtils.getFile("classpath:template/plantilla_email_contacto.html");
        return new String(Files.readAllBytes(templateC.toPath()));
    }

    @Bean
    public EmailService emailService(
            @Value("${sendgrid.api.key}") String apiKey,
            @Value("${alkemy.ong.email.sender}") String emailSender,
            String emailTemplate, String emailTemplateContact) {
        return new EmailService(apiKey, emailSender, emailTemplate, emailTemplateContact);
    }

    @Bean
    public OrganizationService organizationService(
            OrganizationRepository organizationRepository,
            SlideRepository slideRepository,
            AmazonService amazonService) {
        return new OrganizationService(organizationRepository, slideRepository, amazonService);
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

    @Bean
    public ActivityService activityService(ActivityRepository activityRepository) {
        return new ActivityService(activityRepository);
    }

    @Bean
    public ContactService contactService(ContactRepository contactRepository, EmailService emailService) {
        return new ContactService(contactRepository, emailService);
    }

    @Bean
    public MemberService memberService(MemberRepository memberRepository) {
        return new MemberService(memberRepository);
    }

    @Bean
    public TestimonialService testimonialService(TestimonialRepository testimonialRepository) {
        return new TestimonialService(testimonialRepository);
    }

    @Bean
    public SlideService slideService(SlideRepository slideRepository, AmazonService amazonService, OrganizationRepository organizationRepository) {
        return new SlideService(slideRepository, amazonService, organizationRepository);
    }

    @Bean
    public CommentService commentService(CommentRepository commentRepository,
                                         UserRepository userRepository,
                                         NewsRepository newsRepository) {
        return new CommentService(commentRepository,
                userRepository,
                newsRepository);
    }

    @Bean
    public JwtProvider jwtProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") int expiration) {
        return new JwtProvider(secret, expiration);
    }
}
