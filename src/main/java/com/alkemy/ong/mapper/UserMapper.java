package com.alkemy.ong.mapper;

import com.alkemy.ong.domain.User;
import com.alkemy.ong.repository.model.UserModel;

public class UserMapper {
    public static User mapModelToDomain(UserModel userModel){
        User userDomain = User.builder().
                firstName(userModel.getFirstName()).
                lastName(userModel.getLastName()).
                email(userModel.getEmail()).
                password(userModel.getPassword()).
                photo(userModel.getPhoto()).
                role(userModel.getRole()).build();
        return userDomain;
    }
    public static UserModel mapDomainToModel(User userDomain){
        UserModel userModel = UserModel.builder().
                firstName(userDomain.getFirstName()).
                lastName(userDomain.getLastName()).
                email(userDomain.getEmail()).
                password(userDomain.getPassword()).
                photo(userDomain.getPhoto()).
                role(userDomain.getRole()).build();
        return userModel;
    }
}
