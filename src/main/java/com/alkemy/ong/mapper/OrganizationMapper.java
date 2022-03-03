package com.alkemy.ong.mapper;

import com.alkemy.ong.domain.Organization;
import com.alkemy.ong.dto.OrganizationDTO;
import com.alkemy.ong.dto.OrganizationUpdateDTO;
import com.alkemy.ong.repository.model.OrganizationModel;

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
                .urlFacebook(organizationModel.getUrlFacebook())
                .urlInstagram(organizationModel.getUrlInstagram())
                .urlLinkedin(organizationModel.getUrlLinkedin())
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
                .urlFacebook(organizationDomain.getUrlFacebook())
                .urlInstagram(organizationDomain.getUrlInstagram())
                .urlLinkedin(organizationDomain.getUrlLinkedin())
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
                .aboutUsText(organizationDomain.getAboutUsText()).build();
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
