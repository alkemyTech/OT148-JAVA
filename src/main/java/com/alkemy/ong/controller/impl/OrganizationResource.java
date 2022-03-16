package com.alkemy.ong.controller.impl;

import com.alkemy.ong.controller.OrganizationController;
import com.alkemy.ong.domain.Organization;
import com.alkemy.ong.dto.OrganizationDTO;
import com.alkemy.ong.dto.OrganizationUpdateDTO;
import com.alkemy.ong.exception.ApiErrorDTO;
import com.alkemy.ong.exception.OrganizationNotFoundException;
import com.alkemy.ong.mapper.OrganizationMapper;
import com.alkemy.ong.service.OrganizationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class OrganizationResource implements OrganizationController {

    private final OrganizationService organizationService;

    public OrganizationResource(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @Override
    public List<OrganizationDTO> findAll() {
        List<OrganizationDTO> organizationDTOS = organizationService.findAll()
                .stream().map(OrganizationMapper::mapDomainToDTO)
                .collect(Collectors.toList());
        return organizationDTOS;
    }

    @Override
    public OrganizationDTO updateOrganization(
            Long id,
            MultipartFile image,
            OrganizationUpdateDTO organizationUpdateDTO)
            throws OrganizationNotFoundException {
        Organization organization = OrganizationMapper
                .mapUpdateDTOToDomain(organizationUpdateDTO);
        OrganizationDTO organizationDTO =
                OrganizationMapper.mapDomainToDTO(
                        organizationService.updateOrganization(id, organization, image));
        return organizationDTO;
    }

    @ExceptionHandler(OrganizationNotFoundException.class)
    private ResponseEntity<ApiErrorDTO> handleOrganizationNotFound(OrganizationNotFoundException ex) {
        ApiErrorDTO organizationNotFound =
                ApiErrorDTO.builder()
                        .code(HttpStatus.NOT_FOUND)
                        .message(ex.getMessage()).build();
        return new ResponseEntity(organizationNotFound, HttpStatus.NOT_FOUND);
    }

}

