package com.alkemy.ong.service;

import com.alkemy.ong.domain.Organization;
import com.alkemy.ong.dto.OrganizationDTO;
import com.alkemy.ong.exception.OrganizationNotFoundException;
import com.alkemy.ong.mapper.OrganizationMapper;
import com.alkemy.ong.repository.OrganizationRepository;
import com.alkemy.ong.repository.model.OrganizationModel;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

public class OrganizationService {

    private OrganizationRepository organizationRepository;

    public OrganizationService(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @Transactional
    public List<OrganizationDTO> findAll() {
        List<OrganizationModel> organizationModelList = organizationRepository.findAll();
        return organizationModelList.stream().map(OrganizationMapper::mapModelToDomain)
                .map(OrganizationMapper::mapDomainToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public Organization updateOrganization(Integer id, Organization organization)
            throws OrganizationNotFoundException {
        if (organizationRepository.existsById(Long.valueOf(id))) {
            OrganizationModel organizationOld = organizationRepository.findById(Long.valueOf(id)).get();
            organizationOld.setAddress(organization.getAddress());
            organizationOld.setAboutUsText(organization.getAboutUsText());
            organizationOld.setCreationDate(organization.getCreationDate());
            organizationOld.setEmail(organization.getEmail());
            organizationOld.setImage(organization.getImage());
            organizationOld.setName(organization.getName());
            organizationOld.setPhone(organization.getPhone());
            OrganizationModel updatedOrganization = organizationRepository.save(organizationOld);
            return OrganizationMapper.mapModelToDomain(updatedOrganization);
        } else {
            throw new OrganizationNotFoundException(
                    String.format("Organization with ID: %s not found", id));
        }
    }
}
