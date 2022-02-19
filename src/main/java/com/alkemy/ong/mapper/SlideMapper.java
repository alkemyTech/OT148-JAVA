package com.alkemy.ong.mapper;

import com.alkemy.ong.domain.Slide;
import com.alkemy.ong.repository.model.SlideModel;

public class SlideMapper {
    public static Slide mapModelToDomain(SlideModel slideModel){
        Slide slideDomain = Slide.builder()
                .image(slideModel.getImage())
                .text(slideModel.getText())
                .order(slideModel.getOrder())
                .organization(slideModel.getOrganization()).build();
        return slideDomain;
    }

    public static SlideModel mapDomainToModel(Slide slideDomain){
        SlideModel slideModel = SlideModel.builder()
                .image(slideDomain.getImage())
                .text(slideDomain.getText())
                .order(slideDomain.getOrder())
                .organization(slideDomain.getOrganization()).build();
        return slideModel;
    }
}
