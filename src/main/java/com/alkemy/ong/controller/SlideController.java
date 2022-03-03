package com.alkemy.ong.controller;

import com.alkemy.ong.domain.Slide;
import com.alkemy.ong.dto.SlideCreationDTO;
import com.alkemy.ong.dto.SlideDTO;
import com.alkemy.ong.mapper.SlideMapper;
import com.alkemy.ong.service.SlideService;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SlideController {

    private final SlideService slideService;

    public SlideController(SlideService slideService) {
        this.slideService = slideService;
    }

    @PostMapping("/slides")
    public ResponseEntity<SlideDTO> createSlide(@Valid @RequestBody SlideCreationDTO slideCreationDTO) {
        Slide slideDomain = SlideMapper.mapCreationDTOToDomain(slideCreationDTO);
        SlideDTO slideDTO = SlideMapper.mapDomainToDTO(slideService.createSlide(slideDomain));
        return ResponseEntity.ok(slideDTO);
    }
}
