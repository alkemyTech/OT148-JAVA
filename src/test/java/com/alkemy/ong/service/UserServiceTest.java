package com.alkemy.ong.service;

import com.alkemy.ong.domain.User;
import com.alkemy.ong.exception.UserNotFoundException;
import com.alkemy.ong.repository.RoleRepository;
import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.repository.model.RoleModel;
import com.alkemy.ong.repository.model.UserModel;
import com.alkemy.ong.security.JwtProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    private final UserRepository userRepository = mock(UserRepository.class);
    private final RoleRepository roleRepository = mock(RoleRepository.class);
    private final PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
    private final AmazonService amazonService = mock(AmazonService.class);
    private final EmailService emailService = mock(EmailService.class);
    private final JwtProvider jwtProvider = mock(JwtProvider.class);
    private final AuthenticationManager authenticationManager = mock(AuthenticationManager.class);
    private final UserService userService = new UserService(
            userRepository,
            roleRepository,
            passwordEncoder,
            amazonService,
            emailService,
            jwtProvider,
            authenticationManager);

    @Test
    @DisplayName("Should create user")
    void shouldCreateUser() {
        // Given
        User newUser = getTestUser();
        RoleModel roleModel = getRoleModel();
        UserModel userModel = UserModel.builder()
                .id(1L)
                .firstName("santi")
                .lastName("ginabreda")
                .email("email")
                .password("pwk1j3ijf")
                .role(roleModel)
                .build();

        // When
        when(roleRepository.findByName(anyString())).thenReturn(roleModel);
        when(passwordEncoder.encode(anyString())).thenReturn("pwk1j3ijf");
        when(userRepository.save(any())).thenReturn(userModel);

        User userSaved = userService.registerUser(newUser);

        // Then
        assertEquals(userModel.getId(), userSaved.getId());
        assertEquals("pwk1j3ijf", userSaved.getPassword());
    }

    @Test
    void shouldGetListOfUsers() {
        // Given
        RoleModel roleModel = getRoleModel();
        UserModel userModel = UserModel.builder()
                .id(1L)
                .firstName("santi")
                .lastName("ginabreda")
                .email("email")
                .password("pwk1j3ijf")
                .role(roleModel)
                .build();
        List<UserModel> listOfUserModel = new ArrayList<>();
        listOfUserModel.add(userModel);

        // When
        when(userRepository.findAll()).thenReturn(listOfUserModel);

        List<User> listOfUsers = userService.getAll();

        // Then
        assertEquals(1, listOfUsers.size());
        assertEquals("santi", listOfUsers.get(0).getFirstName());
    }

    @Test
    @DisplayName("When updating non existent user, it should throw a UserNotFoundException")
    void updateUserShouldThrowException() {
        // Given
        User user = getTestUser();
        Integer userId = 1;
        MultipartFile file = Mockito.mock(MultipartFile.class);

        // When
        when(userRepository.existsById(any())).thenReturn(false);

        // Then
        assertThrows(UserNotFoundException.class, () -> userService.updateUser(userId, user, file));
    }

    private RoleModel getRoleModel() {
        return RoleModel.builder()
                .id(1)
                .name("USER")
                .description("desc")
                .creationDate(LocalDateTime.now())
                .build();
    }

    private User getTestUser() {
        return User.builder()
                .firstName("test")
                .lastName("user")
                .email("email")
                .password("password")
                .build();
    }
}
