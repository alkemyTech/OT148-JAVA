package com.alkemy.ong.service;

import static com.alkemy.ong.mapper.UserMapper.mapModelToDomain;
import com.alkemy.ong.domain.User;
import com.alkemy.ong.exception.InvalidPasswordException;
import com.alkemy.ong.exception.UserNotFoundException;
import com.alkemy.ong.mapper.RoleMapper;
import com.alkemy.ong.mapper.UserMapper;
import com.alkemy.ong.repository.RoleRepository;
import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.repository.model.RoleModel;
import com.alkemy.ong.repository.model.UserModel;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AmazonService amazonService;
    private final EmailService emailService;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder,
                       AmazonService amazonService,
                       EmailService emailService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.amazonService = amazonService;
        this.emailService = emailService;
    }

    @Transactional
    public User registerUser(User user) {
        RoleModel roleModel = roleRepository.findByName("USER");
        user.setRole(RoleMapper.mapModelToDomain(roleModel));
        UserModel userModel = UserMapper.mapDomainToModel(user);
        userModel.setPassword(encryptPassword(user));
        UserModel save = userRepository.save(userModel);
        User userDomain = mapModelToDomain(save);
        emailService.welcomeEmail(userDomain.getEmail());
        return userDomain;
    }

    private String encryptPassword(User user) {
        String password = user.getPassword();
        return passwordEncoder.encode(password);
    }

    @Transactional(readOnly = true)
    public List<User> getAll() {
        List<UserModel> userModelList = userRepository.findAll();
        return userModelList.stream()
                .map(UserMapper::mapModelToDomain)
                .collect(Collectors.toList());
    }

    @Transactional
    public User updateUser(Integer id,
                           User user,
                           MultipartFile image) throws UserNotFoundException {
        if (userRepository.existsById(Long.valueOf(id))) {
            UserModel userModel = userRepository.findById(Long.valueOf(id)).get();
            userModel.setEmail(user.getEmail());
            userModel.setFirstName(user.getFirstName());
            userModel.setLastName(user.getLastName());
            userModel.setPhoto(uploadImage(image));
            userModel.setPassword(passwordEncoder.encode(user.getPassword()));
            UserModel save = userRepository.save(userModel);
            return mapModelToDomain(save);
        } else {
            throw new UserNotFoundException(String.format("User with ID: %s not found", id));
        }

    }

    private String uploadImage(MultipartFile file) {
        return amazonService.uploadFile(file);
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
            User userDomain = mapModelToDomain(userModel);
            return userDomain;
        } else {
            throw new InvalidPasswordException("The password is invalid");
        }
    }

    private Boolean passwordMatches(String password, String passwordEncrypted) {
        return passwordEncoder.matches(password, passwordEncrypted);
    }

    @Transactional
    public void deleteUser(Long id) throws UserNotFoundException {
        Optional<UserModel> modelOptional = userRepository.findById(id);
        if (!modelOptional.isEmpty()) {
            UserModel userModel = modelOptional.get();
            userRepository.delete(userModel);
        } else {
            throw new UserNotFoundException(String.format("User with this ID " + id + "is not found", id));
        }
    }
}
