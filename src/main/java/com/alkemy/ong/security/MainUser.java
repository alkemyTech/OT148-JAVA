package com.alkemy.ong.security;

import com.alkemy.ong.domain.Role;
import com.alkemy.ong.repository.model.UserModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MainUser  implements UserDetails {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String photo;
    private Collection<?extends GrantedAuthority> authorities;
    private LocalDateTime creationDate;

    public static MainUser build( UserModel userModel) {
        List<GrantedAuthority> authorities =
                userModel.getRole().stream().map(role -> {
                    return new
                            SimpleGrantedAuthority((role.getRoleName()).name());
                }).collect(Collectors.toList());
        return new MainUser(userModel.getFirstName(), userModel.getLastName()
                , userModel.getEmail(), userModel.getPassword(),
                userModel.getPhoto(),authorities,userModel.getCreationDate());
    }

    public MainUser(String firstName, String lastName, String email, String password, String photo,Collection<? extends GrantedAuthority> authorities,LocalDateTime creationDate ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password= password;
        this.photo= photo;
        this.authorities= authorities;
        this.creationDate = creationDate;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoto() {
        return photo;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }
}
