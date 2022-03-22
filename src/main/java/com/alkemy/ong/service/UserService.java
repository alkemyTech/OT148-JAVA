package com.alkemy.ong.service;

import com.alkemy.ong.domain.User;
import com.alkemy.ong.dto.JwtDTO;
import com.alkemy.ong.dto.UserDTO;
import com.alkemy.ong.exception.OngRequestException;
import com.alkemy.ong.mapper.RoleMapper;
import com.alkemy.ong.mapper.UserMapper;
import com.alkemy.ong.repository.RoleRepository;
import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.repository.model.RoleModel;
import com.alkemy.ong.repository.model.UserModel;
import com.alkemy.ong.security.JwtProvider;
import com.alkemy.ong.security.MainUser;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.alkemy.ong.mapper.UserMapper.mapModelToDomain;

public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AmazonService amazonService;
    private final EmailService emailService;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder,
                       AmazonService amazonService,
                       EmailService emailService,
                       JwtProvider jwtProvider,
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.amazonService = amazonService;
        this.emailService = emailService;
        this.jwtProvider = jwtProvider;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    public UserDTO registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new OngRequestException("This email is already registered", "duplicated.mail", HttpStatus.BAD_REQUEST);
        }
        RoleModel roleModel = roleRepository.findByName("USER");
        user.setRole(RoleMapper.mapModelToDomain(roleModel));
        UserModel userModel = UserMapper.mapDomainToModel(user);
        userModel.setPassword(encryptPassword(user));
        UserModel save = userRepository.save(userModel);
        User userDomain = mapModelToDomain(save);
        emailService.welcomeEmail(userDomain.getEmail());
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
    public User updateUser(Integer id,
                           User user,
                           MultipartFile image) throws OngRequestException {
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
            throw new OngRequestException("User not found", "not.found", HttpStatus.NOT_FOUND);
        }

    }

    private String uploadImage(MultipartFile file) {
        return amazonService.uploadFile(file);
    }

    public User loginUser(User user) throws OngRequestException {
        if (userRepository.existsByEmail(user.getEmail())) {
            String email = user.getEmail();
            String password = user.getPassword();
            UserModel userModel = userRepository.findByEmail(email);
            return getUserPasswordChecked(password, userModel);
        } else {
            throw new OngRequestException("Wrong username or password", "invalid.access", HttpStatus.UNAUTHORIZED);
        }
    }

    private User getUserPasswordChecked
            (String password, UserModel userModel) throws OngRequestException {
        if (passwordMatches(password, userModel.getPassword())) {
            User userDomain = mapModelToDomain(userModel);
            return userDomain;
        } else {
            throw new OngRequestException("Wrong username or password", "invalid.access", HttpStatus.UNAUTHORIZED);
        }
    }

    private Boolean passwordMatches(String password, String passwordEncrypted) {
        return passwordEncoder.matches(password, passwordEncrypted);
    }

    @Transactional
    public void deleteUser(Long id) throws OngRequestException {
        Optional<UserModel> modelOptional = userRepository.findById(id);
        if (!modelOptional.isEmpty()) {
            UserModel userModel = modelOptional.get();
            userRepository.delete(userModel);
        } else {
            throw new OngRequestException("User not found", "not.found", HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    public UserDTO getAuthenticatedUser(String jwt) throws OngRequestException {
        String email = jwtProvider.getEmailFromToken(jwt);
        UserModel userModel = userRepository.findByEmail(email);
        User user = UserMapper.mapModelToDomain(userModel);
        return UserMapper.mapDomainToDTO(user);
    }

    public JwtDTO generateAuthenticationToken(User userDomain) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDomain.getEmail(), userDomain.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        MainUser userLog = (MainUser) authentication.getPrincipal();
        return new JwtDTO(jwt, userLog.getEmail());
    }

}
