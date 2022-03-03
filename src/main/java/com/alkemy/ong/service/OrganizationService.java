package com.alkemy.ong.service;

import com.alkemy.ong.domain.Organization;
import com.alkemy.ong.exception.OrganizationNotFoundException;
import static com.alkemy.ong.mapper.OrganizationMapper.mapModelToDomain;
import com.alkemy.ong.repository.OrganizationRepository;
import com.alkemy.ong.repository.SlideRepository;
import com.alkemy.ong.repository.model.OrganizationModel;
import com.alkemy.ong.repository.model.SlideModel;
import org.springframework.web.multipart.MultipartFile;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        return organizationRepository.findAll()
                .stream()
                .parallel()
                .map(organizationModel -> {
                    List<SlideModel> slides = slideRepository.
                            findByOrganizationModel_IdOrderByOrganizationOrder(organizationModel.getId());
                    organizationModel.setSlides(slides);
                    return mapModelToDomain(organizationModel);
                })
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
        organizationOld.setFacebookUrl(organization.getFacebookUrl());
        organizationOld.setInstagramUrl(organization.getInstagramUrl());
        organizationOld.setLinkedinUrl(organization.getLinkedinUrl());
        return mapModelToDomain(organizationRepository.save(organizationOld));
    }

    private String uploadImage(MultipartFile file) {
        return amazonService.uploadFile(file);
    }
}
