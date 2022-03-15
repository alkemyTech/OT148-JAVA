package com.alkemy.ong.controller.impl;

import com.alkemy.ong.controller.UserController;
import com.alkemy.ong.domain.User;
import com.alkemy.ong.dto.ErrorDTO;
import com.alkemy.ong.dto.JwtDTO;
import com.alkemy.ong.dto.UserCreationDTO;
import com.alkemy.ong.dto.UserDTO;
import com.alkemy.ong.dto.UserLoginDTO;
import com.alkemy.ong.dto.UserUpdateDTO;
import com.alkemy.ong.exception.DuplicateEmailException;
import com.alkemy.ong.exception.InvalidPasswordException;
import com.alkemy.ong.exception.UserNotFoundException;
import com.alkemy.ong.mapper.UserMapper;
import static com.alkemy.ong.mapper.UserMapper.mapDomainToDTO;
import static com.alkemy.ong.mapper.UserMapper.mapUpdateDTOToDomain;
import com.alkemy.ong.service.UserService;
import io.swagger.annotations.Api;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(value = "UserResource", tags = {"Users"})
@RestController
public class UserResource implements UserController {

    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @Override
    public JwtDTO createUser(UserCreationDTO userCreationDto) {
        User userDomain = UserMapper.mapDtoCreationToDomain(userCreationDto);
        userService.registerUser(userDomain);
        JwtDTO jwtDto = userService.generateAuthenticationToken(userDomain);
        return jwtDto;
    }

    @Override
    public List<UserDTO> findAll() {
        return userService.getAll();
    }

    @Override
    public UserDTO updateUser(
            Integer userId,
            MultipartFile photo,
            UserUpdateDTO updateDTO) throws UserNotFoundException {
        User user = mapUpdateDTOToDomain(updateDTO);
        UserDTO userDTO = mapDomainToDTO(userService.updateUser(userId, user, photo));
        return userDTO;
    }

    @Override
    public JwtDTO userLogin(UserLoginDTO userLoginDTO) throws UserNotFoundException, InvalidPasswordException {
        User userDomain = UserMapper.mapLoginDTOToDomain(userLoginDTO);
        UserMapper.mapDomainToDTO(userService.loginUser(userDomain));
        JwtDTO jwtDto = userService.generateAuthenticationToken(userDomain);
        return jwtDto;
    }

    @Override
    public void deleteUser(Long userId) throws UserNotFoundException {
        userService.deleteUser(userId);
    }

    @Override
    public UserDTO getUserInfo(String authorizationHeader) throws UserNotFoundException {
        String jwt = authorizationHeader.replace("Bearer ", "");
        return userService.getAuthenticatedUser(jwt);
    }


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleUserNotFoundExceptions(UserNotFoundException ex) {
        ErrorDTO userNotFound =
                ErrorDTO.builder()
                        .code(HttpStatus.NOT_FOUND)
                        .message(ex.getMessage()).build();
        return new ResponseEntity(userNotFound, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ErrorDTO> handleDuplicateEmailExceptions(DuplicateEmailException ex) {
        ErrorDTO emailDuplicate =
                ErrorDTO.builder()
                        .code(HttpStatus.BAD_REQUEST)
                        .message(ex.getMessage()).build();
        return new ResponseEntity(emailDuplicate, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ErrorDTO> handleInvalidPasswordException(InvalidPasswordException ex) {
        ErrorDTO notExistsPassword =
                ErrorDTO.builder()
                        .code(HttpStatus.BAD_REQUEST)
                        .message(ex.getMessage()).build();
        return new ResponseEntity(notExistsPassword, HttpStatus.BAD_REQUEST);
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
}