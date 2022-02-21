package com.alkemy.ong.security;

import com.alkemy.ong.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("userSecurity")
public class UserSecurity {
    @Autowired
    UserRepository userRepository;

    public boolean hasUserId(Authentication authentication, Long Id){
        Boolean sameId = false;
        Long ID = userRepository.findByName(authentication.getName()).getId();
        if(ID==Id) {
            sameId = true;
        }
        return sameId;
    }
}
