package com.alkemy.ong.controller;

import com.alkemy.ong.dto.*;
import com.alkemy.ong.exception.InvalidPasswordException;
import com.alkemy.ong.exception.UserNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Users", description = "Operations related to Users")
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

    @Operation(
            summary = "Find all users",
            description = "To fetch all users, you must access this endpoint"
    )
    @ApiResponse(responseCode = "200",
            description = "Find all users",
            content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = List.class))
            })
    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    List<UserDTO> findAll();

    @Operation(summary = "Update User by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update User by id",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserDTO.class))}),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content)})
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


    @Operation(summary = "Delete a User by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete User by id",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content)})
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
