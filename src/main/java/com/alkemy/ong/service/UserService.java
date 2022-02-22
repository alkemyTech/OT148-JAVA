package com.alkemy.ong.service;

import com.alkemy.ong.domain.User;
import com.alkemy.ong.dto.UserDTO;
import com.alkemy.ong.exception.UserNotFoundException;
import com.alkemy.ong.exception.InvalidPasswordException;
import com.alkemy.ong.mapper.RoleMapper;
import com.alkemy.ong.mapper.UserMapper;
import com.alkemy.ong.repository.RoleRepository;
import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.repository.model.RoleModel;
import com.alkemy.ong.repository.model.UserModel;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    @Transactional
    public UserDTO updateUser(Integer id, User user) throws UserNotFoundException {
        if (userRepository.existsById(Long.valueOf(id))) {
            UserModel userModel = userRepository.findById(Long.valueOf(id)).get();
            userModel.setEmail(user.getEmail());
            userModel.setFirstName(user.getFirstName());
            userModel.setLastName(user.getLastName());
            userModel.setPhoto(user.getPhoto());
            userModel.setPassword(passwordEncoder.encode(user.getPassword()));
            UserModel save = userRepository.save(userModel);
            return UserMapper.mapDomainToDTO(UserMapper.mapModelToDomain(save));
        } else {
            throw new UserNotFoundException(String.format("User with ID: %s not found", id));
        }

    }

    public User loginUser(User user) throws UserNotFoundException, InvalidPasswordException {
        if (userRepository.existsByEmail(user.getEmail())) {
            String email = user.getEmail();
            String password = user.getPassword();
            UserModel userModel = userRepository.findByEmail(email);
            return getUserPasswordChecked(password, userModel);
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    private User getUserPasswordChecked
            (String password, UserModel userModel) throws InvalidPasswordException {
        if (passwordMatches(password, userModel.getPassword())) {
            User userDomain = UserMapper.mapModelToDomain(userModel);
            return userDomain;
        } else {
            throw new InvalidPasswordException("The password is invalid");
        }
    }

    private Boolean passwordMatches(String password, String passwordEncrypted) {
        return passwordEncoder.matches(password, passwordEncrypted);
    }

}
