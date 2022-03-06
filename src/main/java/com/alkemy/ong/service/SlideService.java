package com.alkemy.ong.service;

import com.alkemy.ong.domain.Slide;
import com.alkemy.ong.exception.SlideNotFoundException;
import com.alkemy.ong.mapper.SlideMapper;
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

    @Transactional
    public void deleteSlide(Long idSlide) throws SlideNotFoundException {
        Optional<SlideModel> slide = slideRepository.findById(idSlide);
        if (!slide.isEmpty()) {
            slideRepository.delete(slide.get());
        } else {
            throw new SlideNotFoundException(String.format("Slide with id: %s not found", idSlide));
        }
    }

    @Transactional
    public Slide updateSlide(Long id, Slide slide) throws SlideNotFoundException {
        Optional<SlideModel> optionalSlideModel = slideRepository.findById(id);
        if (optionalSlideModel.isEmpty()) {
            throw new SlideNotFoundException(String.format("Slide with ID: %s not found", id));
        }
        SlideModel slideModel = optionalSlideModel.get();
        slideModel.setImage(slide.getImage());
        slideModel.setText(slide.getText());
        slideModel.setOrganizationOrder(slide.getOrganizationOrder());
        return SlideMapper.mapModelToDomain(slideRepository.save(slideModel));
    }
}
