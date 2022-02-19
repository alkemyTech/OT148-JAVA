package com.alkemy.ong.mapper;

import com.alkemy.ong.domain.Slide;
import com.alkemy.ong.repository.model.SlideModel;

public class SlideMapper {
    public static Slide mapModelToDomain(SlideModel slideModel){
        Slide slideDomain = Slide.builder()
                .image(slideModel.getImage())
                .text(slideModel.getText())
                .order(slideModel.getOrder())
                .organization(OrganizationMapper.mapModelToDomain(slideModel.getOrganizationModel())).build();
        return slideDomain;
    }

    public static SlideModel mapDomainToModel(Slide slideDomain){
        SlideModel slideModel = SlideModel.builder()
                .image(slideDomain.getImage())
                .text(slideDomain.getText())
                .order(slideDomain.getOrder())
                .organizationModel(OrganizationMapper.mapDomainToModel(slideDomain.getOrganization())).build();
        return slideModel;
    }
}
