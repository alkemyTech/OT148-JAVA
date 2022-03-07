package com.alkemy.ong.controller;

import com.alkemy.ong.domain.User;
import com.alkemy.ong.dto.ErrorDTO;
import com.alkemy.ong.dto.JwtDTO;
import com.alkemy.ong.dto.UserCreationDTO;
import com.alkemy.ong.dto.UserDTO;
import com.alkemy.ong.dto.UserLoginDTO;
import com.alkemy.ong.dto.UserUpdateDTO;
import com.alkemy.ong.exception.InvalidPasswordException;
import com.alkemy.ong.exception.UserNotFoundException;
import com.alkemy.ong.mapper.UserMapper;
import com.alkemy.ong.security.JwtProvider;
import com.alkemy.ong.security.MainUser;
import com.alkemy.ong.service.UserService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import static com.alkemy.ong.mapper.UserMapper.mapDomainToDTO;
import static com.alkemy.ong.mapper.UserMapper.mapUpdateDTOToDomain;

@RestController
public class UserController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtProvider jwtProvider;

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/auth/register")
    public ResponseEntity<JwtDTO> userRegister(@Valid @RequestBody UserCreationDTO userCreationDto) throws UserNotFoundException {
        User userDomain = UserMapper.mapDtoCreationToDomain(userCreationDto);
        userService.registerUser(userDomain);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDomain.getEmail(), userDomain.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        MainUser userLog = (MainUser) authentication.getPrincipal();
        JwtDTO jwtDto = new JwtDTO(jwt, userLog.getEmail(), userLog.getAuthorities());
        return ResponseEntity.ok(jwtDto);
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

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleUserNotFoundExceptions(UserNotFoundException ex) {
        ErrorDTO userNotFound =
                ErrorDTO.builder()
                        .code(HttpStatus.NOT_FOUND)
                        .message(ex.getMessage()).build();
        return new ResponseEntity(userNotFound, HttpStatus.NOT_FOUND);

    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @PatchMapping("/users/{userId}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable Integer userId,
            @RequestPart("photo") MultipartFile photo,
            @RequestPart("user") UserUpdateDTO updateDTO) throws UserNotFoundException {
        User user = mapUpdateDTOToDomain(updateDTO);
        UserDTO userDTO = mapDomainToDTO(userService.updateUser(userId, user, photo));
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<JwtDTO> userLogin(@Valid @RequestBody UserLoginDTO userLoginDTO) throws UserNotFoundException, InvalidPasswordException {
        User userDomain = UserMapper.mapLoginDTOToDomain(userLoginDTO);
        UserMapper.mapDomainToDTO(userService.loginUser(userDomain));
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginDTO.getEmail(), userLoginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        MainUser userLog = (MainUser) authentication.getPrincipal();
        JwtDTO jwtDto = new JwtDTO(jwt, userLog.getEmail(), userLog.getAuthorities());
        return new ResponseEntity(jwtDto, HttpStatus.OK);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ErrorDTO> handleInvalidPasswordException(InvalidPasswordException ex) {
        ErrorDTO notExistsPassword =
                ErrorDTO.builder()
                        .code(HttpStatus.BAD_REQUEST)
                        .message(ex.getMessage()).build();
        return new ResponseEntity(notExistsPassword, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) throws UserNotFoundException {
        userService.deleteUser(userId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/auth/me")
    public ResponseEntity<UserDTO> infoUser(@RequestHeader(value = "Authorization") String authorizationHeader) throws UserNotFoundException {
        String jwt = authorizationHeader.replace("Bearer ", "");
        String email = jwtProvider.getEmailFromToken(jwt);
        return ResponseEntity.ok(userService.getAuthenticatedUser(email));
    }


}
