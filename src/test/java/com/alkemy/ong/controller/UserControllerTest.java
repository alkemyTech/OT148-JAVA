package com.alkemy.ong.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import com.alkemy.ong.domain.User;
import com.alkemy.ong.dto.UserCreationDTO;
import com.alkemy.ong.dto.UserDTO;
import com.alkemy.ong.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UserControllerTest {

    private final UserService userService = mock(UserService.class);
    private final UserController userController = new UserController(userService);

    @Test
    void testRegisterUser() {
        // Given
        UserCreationDTO userCreationDTO = UserCreationDTO.builder()
                .name("santi")
                .lastName("ginabreda")
                .email("santi")
                .password("hola")
                .build();


        User user = getTestUser();

        // When
        when(userService.registerUser(any())).thenReturn(user);

        ResponseEntity<UserDTO> newUser = userController.userRegister(userCreationDTO);

        // Then
        assertEquals(HttpStatus.OK, newUser.getStatusCode());
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
