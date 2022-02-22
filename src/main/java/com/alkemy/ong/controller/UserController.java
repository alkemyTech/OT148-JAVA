package com.alkemy.ong.controller;

import com.alkemy.ong.domain.User;
import com.alkemy.ong.dto.UserCreationDTO;
import com.alkemy.ong.dto.UserDTO;
import com.alkemy.ong.dto.UserUpdateDTO;
import com.alkemy.ong.exception.BadRequestException;
import com.alkemy.ong.mapper.UserMapper;
import com.alkemy.ong.service.UserService;
import javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpClientErrorException.NotFound;
import org.springframework.web.server.ResponseStatusException;

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
    public ResponseEntity<UserDTO> userRegister(@Valid @RequestBody UserCreationDTO userCreationDto){
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
    public ResponseEntity<List<UserDTO>> getAll(){
        return ResponseEntity.ok(userService.getAll());
    }

    @PatchMapping("/users/{userId}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable Integer userId,
            @Valid @RequestBody UserUpdateDTO updateDTO) {
        if(!userService.existById(userId)){
            return ResponseEntity.notFound().build();
        }
        User toDomain = UserMapper.mapUpdateDTOToDomain(updateDTO);
        return ResponseEntity.ok(userService.updateUser(userId, toDomain));
    }
}
