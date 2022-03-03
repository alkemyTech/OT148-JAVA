package com.alkemy.ong.service;

import com.alkemy.ong.domain.Slide;
import com.alkemy.ong.mapper.OrganizationMapper;
import com.alkemy.ong.mapper.SlideMapper;
import com.alkemy.ong.repository.SlideRepository;
import com.alkemy.ong.repository.model.SlideModel;
import java.util.Base64;
import javax.transaction.Transactional;
import org.springframework.web.multipart.MultipartFile;

public class SlideService {

    private final SlideRepository slideRepository;
    private final AmazonService amazonService;

    public SlideService(SlideRepository slideRepository, AmazonService amazonService) {
        this.slideRepository = slideRepository;
        this.amazonService = amazonService;
    }

    @Transactional
    public Slide createSlide(Slide slide) {
        SlideModel slideModel = SlideMapper.mapDomainToModel(slide);
        uploadImage(decodeImage(slideModel.getImage()));
        if (slideModel.getOrder() == null) {
            slideModel.setOrder(slide.getOrder() + 1);
        }
        slideModel.setOrganizationModel(OrganizationMapper.mapDomainToModel(slide.getOrganization()));
        SlideModel save = slideRepository.save(slideModel);
        return SlideMapper.mapModelToDomain(save);
    }

    private Base64DecodedMultiPartFileService decodeImage(String image) {
        byte[] decodeBytes = Base64.getDecoder().decode(image);
        Base64DecodedMultiPartFileService decodedMultipart = new Base64DecodedMultiPartFileService(decodeBytes);
        return decodedMultipart;
    }

    private String uploadImage(MultipartFile file) {
        return amazonService.uploadFile(file);
    }
}
