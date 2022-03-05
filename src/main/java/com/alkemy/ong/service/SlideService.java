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
import com.alkemy.ong.exception.SlideNotFoundException;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;


public class SlideService {

    private final SlideRepository slideRepository;
    private final OrganizationRepository organizationRepository;
    private final AmazonService amazonService;
  
    public SlideService(SlideRepository slideRepository, OrganizationRepository organizationRepository, AmazonService amazonService) {
        this.slideRepository = slideRepository;
        this.organizationRepository = organizationRepository;
        this.amazonService = amazonService;
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

    @Transactional(readOnly = true)
    public Slide slideDetails(Long idSlide) throws SlideNotFoundException {
        Optional<SlideModel> slideModel = slideRepository.findById(idSlide);
        if (!slideModel.isEmpty()) {
            Slide slide = Slide.builder()
                    .image(slideModel.get().getImage())
                    .organizationOrder(slideModel.get().getOrganizationOrder())
                    .text(slideModel.get().getText())
                    .build();
            return slide;
        } else {
            throw new SlideNotFoundException(String.format("Slide with id: %s not found", idSlide));
        }
    }
}