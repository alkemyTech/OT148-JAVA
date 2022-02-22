package com.alkemy.ong.controller;

import com.alkemy.ong.domain.User;
import com.alkemy.ong.dto.ErrorDTO;
import com.alkemy.ong.dto.UserCreationDTO;
import com.alkemy.ong.dto.UserDTO;
import com.alkemy.ong.dto.UserLoginDTO;
import com.alkemy.ong.exception.UserLoginPasswordInvalid;
import com.alkemy.ong.exception.UserNotFoundException;
import com.alkemy.ong.mapper.UserMapper;
import com.alkemy.ong.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.HttpStatus;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/auth/register")
    public ResponseEntity<UserDTO> userRegister(@Valid @RequestBody UserCreationDTO userCreationDto) {
        User userDomain = UserMapper.mapDtoCreationToDomain(userCreationDto);
        return ResponseEntity.ok(userService.registerUser(userDomain));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @PostMapping("/auth/login")
    public ResponseEntity<UserDTO> userRegister(@Valid @RequestBody UserLoginDTO userLoginDTO) throws UserNotFoundException, UserLoginPasswordInvalid {
        User userDomain = UserMapper.mapLoginDTOToDomain(userLoginDTO);
        return ResponseEntity.ok(userService.loginUser(userDomain));
    }

    @ExceptionHandler(UserLoginPasswordInvalid.class)
    public ResponseEntity<ErrorDTO> handlerUserLoginPasswordInvalid (UserLoginPasswordInvalid ex) {
        ErrorDTO loginUserNotFound =
                ErrorDTO.builder()
                        .code(HttpStatus.BAD_REQUEST)
                        .message(ex.getMessage()).build();
        return new ResponseEntity(loginUserNotFound, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleUserNotFoundExceptions(UserNotFoundException ex) {
        ErrorDTO userNotFound =
                ErrorDTO.builder()
                        .code(HttpStatus.NOT_FOUND)
                        .message(ex.getMessage()).build();
        return new ResponseEntity(userNotFound, HttpStatus.NOT_FOUND);

    }
}
