package com.alkemy.ong.mapper;

import com.alkemy.ong.domain.Slide;
import com.alkemy.ong.dto.SlideCreationDTO;
import com.alkemy.ong.dto.SlideDTO;
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

    public static Slide mapDTOToDomain(SlideDTO dto) {
        Slide slide = Slide.builder()
                .id(dto.getId())
                .image(dto.getImage())
                .text(dto.getText())
                .order(dto.getOrder())
                .build();
        return slide;
    }

    public static SlideDTO mapDomainToDTO(Slide slideDomain) {
        SlideDTO slideDTO = SlideDTO.builder()
                .id(slideDomain.getId())
                .image(slideDomain.getImage())
                .text(slideDomain.getText())
                .order(slideDomain.getOrder())
                .build();
        return slideDTO;
    }

    public static Slide mapCreationDTOToDomain(SlideCreationDTO slideCreationDTO) {
        Slide slide = Slide.builder()
                .image(slideCreationDTO.getImage())
                .text(slideCreationDTO.getText())
                .order(slideCreationDTO.getOrder())
                .organization(OrganizationMapper.mapDTOToDomain(slideCreationDTO.getOrganization()))
                .build();
        return slide;
    }
}
