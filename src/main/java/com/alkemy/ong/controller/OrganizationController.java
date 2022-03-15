package com.alkemy.ong.controller;

import com.alkemy.ong.dto.ErrorDTO;
import com.alkemy.ong.dto.OrganizationDTO;
import com.alkemy.ong.dto.OrganizationUpdateDTO;
import com.alkemy.ong.exception.OrganizationNotFoundException;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

public interface OrganizationController {

    @GetMapping("/organization/public")
    @ResponseStatus(HttpStatus.OK)
    List<OrganizationDTO> findAll();

    @PatchMapping("/organization/{id}")
    @ResponseStatus(HttpStatus.OK)
    OrganizationDTO updateOrganization(
            @PathVariable Long id,
            @RequestPart("image") MultipartFile image,
            @RequestPart("organization") OrganizationUpdateDTO organizationUpdateDTO)
            throws OrganizationNotFoundException;

    @ExceptionHandler(OrganizationNotFoundException.class)
    private ResponseEntity<ErrorDTO> handleOrganizationNotFound(OrganizationNotFoundException ex) {
        ErrorDTO organizationNotFound =
                ErrorDTO.builder()
                        .code(HttpStatus.NOT_FOUND)
                        .message(ex.getMessage()).build();
        return new ResponseEntity(organizationNotFound, HttpStatus.NOT_FOUND);
    }

}
