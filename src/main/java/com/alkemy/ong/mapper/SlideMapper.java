package com.alkemy.ong.mapper;

import com.alkemy.ong.domain.Slide;
import com.alkemy.ong.dto.SlideDTO;
import com.alkemy.ong.dto.SlideUpdateDTO;
import com.alkemy.ong.repository.model.SlideModel;

public class SlideMapper {
    public static Slide mapModelToDomain(SlideModel slideModel) {
        Slide slideDomain = Slide.builder()
                .image(slideModel.getImage())
                .text(slideModel.getText())
                .organizationOrder(slideModel.getOrganizationOrder())
                .build();
        return slideDomain;
    }

    public static SlideModel mapDomainToModel(Slide slideDomain) {
        SlideModel slideModel = SlideModel.builder()
                .image(slideDomain.getImage())
                .text(slideDomain.getText())
                .organizationOrder(slideDomain.getOrganizationOrder())
                .build();
        return slideModel;
    }

    public static SlideDTO mapDomainToDto(Slide slideDomain) {
        SlideDTO slideDTO = SlideDTO.builder()
                .image(slideDomain.getImage())
                .text(slideDomain.getText())
                .order(slideDomain.getOrganizationOrder())
                .build();
        return slideDTO;
    }

    public static Slide mapUpdateDTOToDomain(SlideUpdateDTO slideUpdateDTO) {
        Slide slide = Slide.builder()
                .image(slideUpdateDTO.getImage())
                .text(slideUpdateDTO.getText())
                .organizationOrder(slideUpdateDTO.getOrder())
                .build();
        return slide;
    }
}
