package com.alkemy.ong.controller;

import com.alkemy.ong.domain.User;
import com.alkemy.ong.dto.UserDTO;
import com.alkemy.ong.mapper.UserMapper;
import com.alkemy.ong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(name = "/auth")
public class UserController {

    @Autowired
    private UserService userService;

    private UserMapper mapper;

    @PostMapping("/register")
    public ResponseEntity<?> userRegister(@Valid @RequestBody UserDTO userDto, BindingResult result){
        Map<String, Object> validations = new HashMap<>();
        if(result.hasErrors()){
            result.getFieldErrors().
                    forEach(error -> validations.put(error.getField(),error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(validations);
        }
        User userDomain = mapper.mapDtoToDomain(userDto);
        return ResponseEntity.ok(userService.registerUser(userDomain));
    }



}
