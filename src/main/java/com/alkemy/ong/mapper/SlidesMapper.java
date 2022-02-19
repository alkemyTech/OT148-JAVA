package com.alkemy.ong.mapper;

import com.alkemy.ong.domain.Slides;
import com.alkemy.ong.repository.model.SlidesModel;

public class SlidesMapper {
    public static Slides mapModelToDomain(SlidesModel slidesModel){
        Slides slidesDomain = Slides.builder()
                .image(slidesModel.getImage())
                .text(slidesModel.getText())
                .order(slidesModel.getOrder())
                .organization(slidesModel.getOrganization()).build();
        return slidesDomain;
    }

    public static SlidesModel mapDomainToModel(Slides slidesDomain){
        SlidesModel slidesModel = SlidesModel.builder()
                .image(slidesDomain.getImage())
                .text(slidesDomain.getText())
                .order(slidesDomain.getOrder())
                .organization(slidesDomain.getOrganization()).build();
        return slidesModel;
    }
}
