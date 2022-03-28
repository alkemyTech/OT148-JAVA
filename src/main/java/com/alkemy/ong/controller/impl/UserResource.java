package com.alkemy.ong.controller.impl;

import com.alkemy.ong.controller.UserController;
import com.alkemy.ong.domain.User;
import com.alkemy.ong.dto.JwtDTO;
import com.alkemy.ong.dto.UserCreationDTO;
import com.alkemy.ong.dto.UserDTO;
import com.alkemy.ong.dto.UserLoginDTO;
import com.alkemy.ong.dto.UserUpdateDTO;
import com.alkemy.ong.exception.OngRequestException;
import com.alkemy.ong.mapper.UserMapper;
import com.alkemy.ong.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.alkemy.ong.mapper.UserMapper.mapDomainToDTO;
import static com.alkemy.ong.mapper.UserMapper.mapUpdateDTOToDomain;

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
            UserUpdateDTO updateDTO) throws OngRequestException {
        User user = mapUpdateDTOToDomain(updateDTO);
        UserDTO userDTO = mapDomainToDTO(userService.updateUser(userId, user, photo));
        return userDTO;
    }

    @Override
    public JwtDTO userLogin(UserLoginDTO userLoginDTO) throws OngRequestException {
        User userDomain = UserMapper.mapLoginDTOToDomain(userLoginDTO);
        UserMapper.mapDomainToDTO(userService.loginUser(userDomain));
        JwtDTO jwtDto = userService.generateAuthenticationToken(userDomain);
        return jwtDto;
    }

    @Override
    public void deleteUser(Long userId) throws OngRequestException {
        userService.deleteUser(userId);
    }

    @Override
    public UserDTO getUserInfo(String authorizationHeader) throws OngRequestException {
        String jwt = authorizationHeader.replace("Bearer ", "");
        return userService.getAuthenticatedUser(jwt);
    }
}