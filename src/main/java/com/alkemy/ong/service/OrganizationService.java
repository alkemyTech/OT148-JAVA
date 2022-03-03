package com.alkemy.ong.service;

import com.alkemy.ong.domain.Organization;
import com.alkemy.ong.exception.OrganizationNotFoundException;
import com.alkemy.ong.mapper.OrganizationMapper;
import com.alkemy.ong.repository.OrganizationRepository;
import com.alkemy.ong.repository.SlideRepository;
import com.alkemy.ong.repository.model.OrganizationModel;
import com.alkemy.ong.repository.model.SlideModel;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.web.multipart.MultipartFile;

public class OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final SlideRepository slideRepository;
    private final AmazonService amazonService;

    public OrganizationService(OrganizationRepository organizationRepository,
                               SlideRepository slideRepository,
                               AmazonService amazonService) {
        this.organizationRepository = organizationRepository;
        this.slideRepository = slideRepository;
        this.amazonService = amazonService;
    }

    @Transactional
    public List<Organization> findAll() {
        List<OrganizationModel> organizationModelList = organizationRepository.findAll();
        organizationModelList.stream().forEach(organizationModel -> {
            List<SlideModel> slides = slideRepository.
                    findByOrganizationModel_IdOrderByOrder(organizationModel.getId());
            organizationModel.setSlides((Set<SlideModel>) slides);
        });
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
        return OrganizationMapper.mapModelToDomain(organizationRepository.save(organizationOld));
    }

    private String uploadImage(MultipartFile file) {
        return amazonService.uploadFile(file);
    }
}
