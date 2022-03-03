package com.alkemy.ong.service;

import com.alkemy.ong.domain.Organization;
import com.alkemy.ong.exception.OrganizationNotFoundException;
import com.alkemy.ong.mapper.OrganizationMapper;
import com.alkemy.ong.repository.OrganizationRepository;
import com.alkemy.ong.repository.model.OrganizationModel;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final AmazonService amazonService;

    public OrganizationService(OrganizationRepository organizationRepository, AmazonService amazonService) {
        this.organizationRepository = organizationRepository;
        this.amazonService = amazonService;
    }

    @Transactional
    public List<Organization> findAll() {
        List<OrganizationModel> organizationModelList = organizationRepository.findAll();
        return organizationModelList.stream().map(OrganizationMapper::mapModelToDomain)
                .collect(Collectors.toList());
    }

    @Transactional
    public Organization updateOrganization(Long id, Organization organization, MultipartFile image)
            throws OrganizationNotFoundException {
        Optional<OrganizationModel> optionalOrg = organizationRepository.findById(id);
        if (optionalOrg.isEmpty()) {
            throw new OrganizationNotFoundException(
                    String.format("Organization with ID: %s not found", id));
        }
        OrganizationModel organizationOld = optionalOrg.get();
        organizationOld.setAddress(organization.getAddress());
        organizationOld.setAboutUsText(organization.getAboutUsText());
        organizationOld.setCreationDate(organization.getCreationDate());
        organizationOld.setEmail(organization.getEmail());
        organizationOld.setImage(uploadImage(image));
        organizationOld.setName(organization.getName());
        organizationOld.setPhone(organization.getPhone());
        organizationOld.setUrlFacebook(organization.getUrlFacebook());
        organizationOld.setUrlInstagram(organization.getUrlInstagram());
        organizationOld.setUrlLinkedin(organization.getUrlLinkedin());
        return OrganizationMapper.mapModelToDomain(organizationRepository.save(organizationOld));
    }

    private String uploadImage(MultipartFile file) {
        return amazonService.uploadFile(file);
    }
}
