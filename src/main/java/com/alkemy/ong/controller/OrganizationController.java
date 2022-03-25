package com.alkemy.ong.controller;

import com.alkemy.ong.dto.OrganizationDTO;
import com.alkemy.ong.dto.OrganizationUpdateDTO;
import com.alkemy.ong.exception.OngRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface OrganizationController {

    @GetMapping("/organization/public")
    @ResponseStatus(HttpStatus.OK)
    List<OrganizationDTO> findAll();

    @PatchMapping("/organization/{id}")
    @ResponseStatus(HttpStatus.OK)
    OrganizationDTO updateOrganization(
            @PathVariable Long id,
            @RequestPart(value = "image", required = false) MultipartFile image,
            @RequestPart("organization") OrganizationUpdateDTO organizationUpdateDTO)
            throws OngRequestException;
}
