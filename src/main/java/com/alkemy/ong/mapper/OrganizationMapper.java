package com.alkemy.ong.mapper;

import com.alkemy.ong.domain.Organization;
import com.alkemy.ong.dto.OrganizationDTO;
import com.alkemy.ong.dto.OrganizationUpdateDTO;
import com.alkemy.ong.repository.model.OrganizationModel;
import java.util.stream.Collectors;

public class OrganizationMapper {

    public static Organization mapModelToDomain(OrganizationModel organizationModel) {
        Organization organizationDomain = Organization.builder()
                .name(organizationModel.getName())
                .image(organizationModel.getImage())
                .address(organizationModel.getAddress())
                .phone(organizationModel.getPhone())
                .email(organizationModel.getEmail())
                .welcomeText(organizationModel.getWelcomeText())
                .aboutUsText(organizationModel.getAboutUsText())
                .facebookUrl(organizationModel.getFacebookUrl())
                .instagramUrl(organizationModel.getInstagramUrl())
                .linkedinUrl(organizationModel.getLinkedinUrl())
                .slides(organizationModel
                        .getSlides().stream()
                        .map(SlideMapper::mapModelToDomain)
                        .collect(Collectors.toList()))
                .build();
        return organizationDomain;
    }

    public static OrganizationModel mapDomainToModel(Organization organizationDomain) {
        OrganizationModel organizationModel = OrganizationModel.builder()
                .name(organizationDomain.getName())
                .image(organizationDomain.getImage())
                .address(organizationDomain.getAddress())
                .phone(organizationDomain.getPhone())
                .email(organizationDomain.getEmail())
                .welcomeText(organizationDomain.getWelcomeText())
                .aboutUsText(organizationDomain.getAboutUsText())
                .facebookUrl(organizationDomain.getFacebookUrl())
                .instagramUrl(organizationDomain.getInstagramUrl())
                .linkedinUrl(organizationDomain.getLinkedinUrl())
                .build();
        return organizationModel;
    }

    public static OrganizationDTO mapDomainToDTO(Organization organizationDomain) {
        OrganizationDTO organizationDTO = OrganizationDTO.builder()
                .name(organizationDomain.getName())
                .image(organizationDomain.getImage())
                .address(organizationDomain.getAddress())
                .phone(organizationDomain.getPhone())
                .email(organizationDomain.getEmail())
                .welcomeText(organizationDomain.getWelcomeText())
                .aboutUsText(organizationDomain.getAboutUsText())
                .slides(organizationDomain
                        .getSlides().stream()
                        .map(SlideMapper::mapDomainToDto)
                        .collect(Collectors.toList()))
                .build();
        return organizationDTO;
    }

    public static Organization mapUpdateDTOToDomain(OrganizationUpdateDTO updateDTO) {
        Organization organization = Organization.builder()
                .name(updateDTO.getName())
                .email(updateDTO.getEmail())
                .phone(updateDTO.getPhone())
                .welcomeText(updateDTO.getWelcomeText())
                .aboutUsText(updateDTO.getAboutUsText())
                .address(updateDTO.getAddress()).build();
        return organization;
    }
}
