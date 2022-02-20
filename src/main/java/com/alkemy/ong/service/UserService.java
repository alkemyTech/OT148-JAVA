

package com.alkemy.ong.service;

import com.alkemy.ong.domain.User;
import com.alkemy.ong.dto.UserDTO;
import com.alkemy.ong.mapper.RoleMapper;
import com.alkemy.ong.mapper.UserMapper;
import com.alkemy.ong.repository.RoleRepository;
import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.repository.model.RoleModel;
import com.alkemy.ong.repository.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public UserDTO registerUser(User user) {
        RoleModel roleModel = roleRepository.findByName("Normal");
        user.setRole(RoleMapper.mapModelToDomain(roleModel));
        UserModel userModel = UserMapper.mapDomainToModel(user);
        userModel.setPassword(encryptPassword(user));
        UserModel save = userRepository.save(userModel);
        return UserMapper.mapModelToDto(save);
    }

    private String encryptPassword(User user){
        String password = user.getPassword();
        String encryptedPassword = passwordEncoder.encode(password);
        return encryptedPassword;
    }

    @Transactional(readOnly = true)
    public List<UserDTO> getAll(){
        List<UserModel> userModelList = (List<UserModel>) userRepository.findAll();
        List<UserDTO> userDTOList = new ArrayList<UserDTO>();
        userModelList.forEach(userModel -> {
            UserDTO userDTO = UserMapper.mapModelToDomain(userModel);
            userDTOList.add(userDTO);
        });
        return userDTOList;
    }
}