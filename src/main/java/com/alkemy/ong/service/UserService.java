package com.alkemy.ong.service;

import com.alkemy.ong.domain.User;
import com.alkemy.ong.dto.UserDTO;
import com.alkemy.ong.exception.UserLoginPasswordInvalid;
import com.alkemy.ong.exception.UserNotFoundException;
import com.alkemy.ong.mapper.RoleMapper;
import com.alkemy.ong.mapper.UserMapper;
import com.alkemy.ong.repository.RoleRepository;
import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.repository.model.RoleModel;
import com.alkemy.ong.repository.model.UserModel;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserDTO registerUser(User user) {
        RoleModel roleModel = roleRepository.findByName("USER");
        user.setRole(RoleMapper.mapModelToDomain(roleModel));
        UserModel userModel = UserMapper.mapDomainToModel(user);
        userModel.setPassword(encryptPassword(user));
        UserModel save = userRepository.save(userModel);
        User userDomain = UserMapper.mapModelToDomain(save);
        return UserMapper.mapDomainToDTO(userDomain);
    }

    private String encryptPassword(User user) {
        String password = user.getPassword();
        String encryptedPassword = passwordEncoder.encode(password);
        return encryptedPassword;
    }

    @Transactional(readOnly = true)
    public List<UserDTO> getAll() {
        List<UserModel> userModelList = userRepository.findAll();
        return userModelList.stream().map(UserMapper::mapModelToDomain)
                .map(UserMapper::mapDomainToDTO)
                .collect(Collectors.toList());
    }

    public UserDTO loginUser(User user) throws UserNotFoundException, UserLoginPasswordInvalid {
        if (userRepository.existsByEmail(user.getEmail())) {
            String email = user.getEmail();
            String password = user.getPassword();
            UserModel userModel = userRepository.findByEmail(email);
            return getUserDTO(password, userModel);
        }else{
            throw new UserNotFoundException("User not found");
        }
    }

    private UserDTO getUserDTO
            (String password, UserModel userModel) throws UserLoginPasswordInvalid {
        if (matchEncryptedPassword(password, userModel.getPassword())) {
        User userDomain = UserMapper.mapModelToDomain(userModel);
        UserDTO userDTO = UserMapper.mapDomainToDTO(userDomain);
            return userDTO;

        }else{
            throw new UserLoginPasswordInvalid("The password is invalid");
        }
    }

    private Boolean matchEncryptedPassword(String password, String passwordEncrypted) {
        return passwordEncoder.matches(password, passwordEncrypted);
    }

}
