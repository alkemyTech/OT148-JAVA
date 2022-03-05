package com.alkemy.ong.service;

import com.alkemy.ong.domain.Slide;
import com.alkemy.ong.exception.OrganizationNotFoundException;
import com.alkemy.ong.mapper.OrganizationMapper;
import com.alkemy.ong.mapper.SlideMapper;
import com.alkemy.ong.repository.OrganizationRepository;
import com.alkemy.ong.repository.SlideRepository;
import com.alkemy.ong.repository.model.SlideModel;
import com.alkemy.ong.utils.Base64DecodedMultiPartFile;
import java.util.Base64;
import javax.transaction.Transactional;
import org.springframework.web.multipart.MultipartFile;

public class SlideService {

    private final SlideRepository slideRepository;
    private final OrganizationRepository organizationRepository;

    public SlideService(SlideRepository slideRepository, OrganizationRepository organizationRepository) {
        this.slideRepository = slideRepository;
        this.organizationRepository = organizationRepository;
    }

    @Transactional
    public Slide createSlide(Slide slide) {
        SlideModel slideModel = SlideMapper.mapDomainToModel(slide);
        if (organizationRepository.existsById(slideModel.getOrganizationModel().getId())) {
            slideModel.setOrganizationModel(OrganizationMapper.mapDomainToModel(slide.getOrganization()));
            if (slideModel.getOrder() == null) {
                SlideModel slideModelMaxOrder = slideRepository.findFirstByOrganization_IdOrderByOrganizationOrderDesc(slideModel.getOrganizationModel().getId());
                slideModel.setOrder(slideModelMaxOrder.getOrder() + 1);
            }
        } else {
            throw new OrganizationNotFoundException(String.format("Organization with ID: %s not found", slideModel.getOrganizationModel().getId()));
        }
        uploadImage(decodeImage(slideModel.getImage()));
        slideModel.setImage(slide.getImage());
        SlideModel save = slideRepository.save(slideModel);
        return SlideMapper.mapModelToDomain(save);
    }

    private Base64DecodedMultiPartFile decodeImage(String image) {
        byte[] decodeBytes = Base64.getDecoder().decode(image);
        return new Base64DecodedMultiPartFile(decodeBytes);
    }

    private void uploadImage(MultipartFile file) {
    }
}
