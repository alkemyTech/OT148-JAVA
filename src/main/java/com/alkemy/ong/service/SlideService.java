package com.alkemy.ong.service;

import com.alkemy.ong.domain.Slide;
import com.alkemy.ong.exception.SlideNotFoundException;
import com.alkemy.ong.repository.SlideRepository;
import com.alkemy.ong.repository.model.SlideModel;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

public class SlideService {

    private final SlideRepository slideRepository;
    private final AmazonService amazonService;

    public SlideService(SlideRepository slideRepository, AmazonService amazonService) {
        this.slideRepository = slideRepository;
        this.amazonService = amazonService;
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
