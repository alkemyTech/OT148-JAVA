package com.alkemy.ong.mapper;

import com.alkemy.ong.domain.Organization;
import com.alkemy.ong.repository.model.OrganizationModel;

public class OrganizationMapper {

    public static Organization mapModelToDomain(OrganizationModel organizationModel){
        Organization organizationDomain = Organization.builder().build();
        organizationDomain.setName(organizationModel.getName());
        organizationDomain.setImage(organizationModel.getImage());
        organizationDomain.setAddress(organizationModel.getAddress());
        organizationDomain.setPhone(organizationModel.getPhone());
        organizationDomain.setEmail(organizationModel.getEmail());
        organizationDomain.setWelcomeText(organizationModel.getWelcomeText());
        organizationDomain.setAboutUsText(organizationModel.getAboutUsText());
        return organizationDomain;
    }

    public static OrganizationModel mapDomainToModel(Organization organizationDomain){
        OrganizationModel organizationModel = new OrganizationModel();
        organizationModel.setName(organizationDomain.getName());
        organizationModel.setImage(organizationDomain.getImage());
        organizationModel.setAddress(organizationDomain.getAddress());
        organizationModel.setPhone(organizationDomain.getPhone());
        organizationModel.setEmail(organizationDomain.getEmail());
        organizationModel.setWelcomeText(organizationDomain.getWelcomeText());
        organizationModel.setAboutUsText(organizationDomain.getAboutUsText());
        return organizationModel;
    }


}
