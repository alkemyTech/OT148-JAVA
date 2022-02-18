package com.alkemy.ong.mapper;

import com.alkemy.ong.domain.User;
import com.alkemy.ong.dto.UserDTO;
import com.alkemy.ong.repository.model.UserModel;

public class UserMapper {

    public User mapDtoToDomain(UserDTO userDTO){
        User userDomain = User.builder().
                email(userDTO.getEmail()).
                name(userDTO.getName()).
                lastname(userDTO.getLastname()).
                password(userDTO.getPassword()).build();
        return userDomain;
    }

    public UserDTO mapDomianToDto(User user){
        UserDTO userDTO = UserDTO.builder().
                email(user.getEmail()).
                name(user.getName()).
                lastname(user.getLastname()).
                password(user.getPassword()).build();
        return userDTO;
    }

    public UserModel mapDomainToModel(User user){
        UserModel userModel = UserModel.builder().
                email(user.getEmail()).
                name(user.getName()).
                lastname(user.getLastname()).
                password(user.getPassword()).build();
        return userModel;
    }

    public UserDTO mapModelToDto(UserModel user){
        UserDTO userDTO = UserDTO.builder().
                email(user.getEmail()).
                name(user.getName()).
                lastname(user.getLastname()).
                password(user.getPassword()).build();
        return userDTO;
    }

}
