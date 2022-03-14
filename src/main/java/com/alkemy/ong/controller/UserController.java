package com.alkemy.ong.controller;

import com.alkemy.ong.dto.JwtDTO;
import com.alkemy.ong.dto.UserCreationDTO;
import com.alkemy.ong.dto.UserDTO;
import com.alkemy.ong.dto.UserLoginDTO;
import com.alkemy.ong.dto.UserUpdateDTO;
import com.alkemy.ong.exception.InvalidPasswordException;
import com.alkemy.ong.exception.UserNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Users", description = "Register, login and auth me Users")
public interface UserController {

    @Operation(
            summary = "Register a new User",
            description = "To register, this endpoint must be accessed"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Register user",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = JwtDTO.class))
                    }),
            @ApiResponse(responseCode = "400",
                    description = "The fields must not be empty",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "This email is in use",
                    content = @Content)
    })
    @PostMapping("/auth/register")
    @ResponseStatus(HttpStatus.CREATED)
    JwtDTO createUser(@Valid @RequestBody UserCreationDTO userCreationDto);

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    List<UserDTO> findAll();

    @PatchMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    UserDTO updateUser(
            @PathVariable Integer userId,
            @RequestPart("photo") MultipartFile photo,
            @RequestPart("user") UserUpdateDTO updateDTO) throws UserNotFoundException;

    @Operation(
            summary = "User login",
            description = "To log in, you must access this endpoint"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "User login",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = JwtDTO.class))
                    }),
            @ApiResponse(responseCode = "400",
                    description = "The password is invalid",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "User not found",
                    content = @Content)
    })
    @PostMapping("/auth/login")
    @ResponseStatus(HttpStatus.OK)
    JwtDTO userLogin(@Valid @RequestBody UserLoginDTO userLoginDTO) throws UserNotFoundException, InvalidPasswordException;

    @DeleteMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    void deleteUser(@PathVariable Long userId) throws UserNotFoundException;

    @Operation(
            summary = "Auth me",
            description = "Returns the user data belonging to the provided token"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Auth me",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserDTO.class))
                    }),
            @ApiResponse(responseCode = "403",
                    description = "Bad form token",
                    content = @Content)
    })
    @GetMapping("/auth/me")
    @ResponseStatus(HttpStatus.OK)
    UserDTO getUserInfo(@RequestHeader(value = "Authorization") String authorizationHeader)
            throws UserNotFoundException;

}
