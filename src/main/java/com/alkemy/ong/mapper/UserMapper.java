package com.alkemy.ong.mapper;

import com.alkemy.ong.domain.User;
import com.alkemy.ong.dto.UserCreationDTO;
import com.alkemy.ong.dto.UserDTO;
import com.alkemy.ong.repository.model.UserModel;

public class UserMapper {

    public static User mapDtoCreationToDomain(UserCreationDTO userCreationDTO){
        User userDomain = User.builder().
                email(userCreationDTO.getEmail()).
                name(userCreationDTO.getName()).
                lastname(userCreationDTO.getLastname()).
                password(userCreationDTO.getPassword()).build();
        return userDomain;
    }

    public static UserCreationDTO mapDomianToDtoCreation(User user){
        UserCreationDTO userCreationDTO = UserCreationDTO.builder().
                email(user.getEmail()).
                name(user.getName()).
                lastname(user.getLastname()).
                password(user.getPassword()).build();
        return userCreationDTO;
    }

    public static UserModel mapDomainToModel(User user){
        UserModel userModel = UserModel.builder().
                email(user.getEmail()).
                name(user.getName()).
                lastname(user.getLastname()).
                password(user.getPassword()).build();
        return userModel;
    }

    public static UserCreationDTO mapModelToDtoCreation(UserModel user){
        UserCreationDTO userCreationDTO = UserCreationDTO.builder().
                email(user.getEmail()).
                name(user.getName()).
                lastname(user.getLastname()).
                password(user.getPassword()).build();
        return userCreationDTO;
    }

    public static UserDTO mapModelToDto(UserModel user){
        UserDTO userDTO = UserDTO.builder().
                email(user.getEmail()).
                firstName(user.getName()).
                lastName(user.getLastname()).
                creationDate(null).
                id(user.getId()).
                password(null).
                photo(null).build();
        return userDTO;
    }

}
