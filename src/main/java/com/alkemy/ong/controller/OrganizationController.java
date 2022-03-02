package com.alkemy.ong.controller;

import com.alkemy.ong.domain.Organization;
import com.alkemy.ong.dto.ErrorDTO;
import com.alkemy.ong.dto.OrganizationDTO;
import com.alkemy.ong.dto.OrganizationUpdateDTO;
import com.alkemy.ong.exception.OrganizationNotFoundException;
import com.alkemy.ong.mapper.OrganizationMapper;
import com.alkemy.ong.service.OrganizationService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class OrganizationController {

    private final OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @GetMapping("/organization/public")
    public ResponseEntity<List<OrganizationDTO>> getAll() {
        List<OrganizationDTO> organizationDTOS = organizationService.findAll()
                .stream().map(OrganizationMapper::mapDomainToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(organizationDTOS);
    }

    @PatchMapping("/organization/{id}")
    public ResponseEntity<OrganizationDTO> updateOrganization(
            @PathVariable Long id,
            @RequestPart("image") MultipartFile image,
            @RequestPart("organization") OrganizationUpdateDTO organizationUpdateDTO)
            throws OrganizationNotFoundException {

        Organization organization = OrganizationMapper
                .mapUpdateDTOToDomain(organizationUpdateDTO);
        OrganizationDTO organizationDTO =
                OrganizationMapper.mapDomainToDTO(
                        organizationService.updateOrganization(id, organization, image));
        return ResponseEntity.ok(organizationDTO);
    }

    @ExceptionHandler(OrganizationNotFoundException.class)
    private ResponseEntity<ErrorDTO> handleOrganizationNotFound(OrganizationNotFoundException ex) {
        ErrorDTO organizationNotFound =
                ErrorDTO.builder()
                        .code(HttpStatus.NOT_FOUND)
                        .message(ex.getMessage()).build();
        return new ResponseEntity(organizationNotFound, HttpStatus.NOT_FOUND);
    }

}
