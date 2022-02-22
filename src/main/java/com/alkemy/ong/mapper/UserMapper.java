package com.alkemy.ong.mapper;

import com.alkemy.ong.domain.User;
import com.alkemy.ong.dto.UserCreationDTO;
import com.alkemy.ong.dto.UserDTO;
import com.alkemy.ong.dto.UserUpdateDTO;
import com.alkemy.ong.repository.model.UserModel;

public class UserMapper {

    public static User mapDtoCreationToDomain(UserCreationDTO userCreationDTO){
        User userDomain = User.builder().
                email(userCreationDTO.getEmail()).
                firstName(userCreationDTO.getName()).
                lastName(userCreationDTO.getLastName()).
                password(userCreationDTO.getPassword()).build();
        return userDomain;
    }

    public static User mapModelToDomain(UserModel userModel){
        User userDomain = User.builder()
                .firstName(userModel.getFirstName())
                .lastName(userModel.getLastName())
                .email(userModel.getEmail())
                .password(userModel.getPassword())
                .photo(userModel.getPhoto())
                .role(RoleMapper.mapModelToDomain(userModel.getRole())).build();
        return userDomain;
    }

    public static UserModel mapDomainToModel(User userDomain){
        UserModel userModel = UserModel.builder()
                .firstName(userDomain.getFirstName())
                .lastName(userDomain.getLastName())
                .email(userDomain.getEmail())
                .password(userDomain.getPassword())
                .photo(userDomain.getPhoto())
                .role(RoleMapper.mapDomainToModel(userDomain.getRole())).build();
        return userModel;
    }

    public static UserDTO mapDomainToDTO(User userDomain){
        UserDTO userDTO = UserDTO.builder().
                lastName(userDomain.getLastName()).
                firstName(userDomain.getFirstName()).
                id(userDomain.getId()).
                creationDate(userDomain.getCreationDate()).
                email(userDomain.getEmail()).
                photo(userDomain.getPhoto()).build();
        return userDTO;
    }

    public static User mapUpdateDTOToDomain(UserUpdateDTO updateDTO){
        User userDomain = User.builder()
                .firstName(updateDTO.getFirstName())
                .lastName(updateDTO.getLastName())
                .email(updateDTO.getEmail())
                .password(updateDTO.getPassword())
                .photo(updateDTO.getPhoto()).build();
        return userDomain;
    }
}
