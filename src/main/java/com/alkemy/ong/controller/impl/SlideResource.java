package com.alkemy.ong.controller.impl;

import com.alkemy.ong.controller.SlideController;
import com.alkemy.ong.domain.Slide;
import com.alkemy.ong.dto.SlideCreationDTO;
import com.alkemy.ong.dto.SlideDTO;
import com.alkemy.ong.dto.SlideUpdateDTO;
import com.alkemy.ong.exception.ApiErrorDTO;
import com.alkemy.ong.exception.SlideNotFoundException;
import com.alkemy.ong.mapper.SlideMapper;
import com.alkemy.ong.service.SlideService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SlideResource implements SlideController {

    private final SlideService slideService;

    public SlideResource(SlideService slideService) {
        this.slideService = slideService;
    }

    @Override
    public SlideDTO slideDetails(Long id) throws SlideNotFoundException {
        return SlideMapper.mapDomainToDto(slideService.slideDetails(id));
    }

    @Override
    public void deleteSlide(Long id) throws SlideNotFoundException {
        slideService.deleteSlide(id);
    }

    @Override
    public SlideDTO updateSlide(Long id, SlideUpdateDTO slideUpdateDTO) throws SlideNotFoundException {
        Slide slide = SlideMapper.mapUpdateDTOToDomain(slideUpdateDTO);
        SlideDTO slideDTO = SlideMapper.mapDomainToDto(slideService.updateSlide(id, slide));
        return slideDTO;
    }

    @Override
    public SlideDTO createSlide(SlideCreationDTO slideCreationDTO) {
        Slide slideDomain = SlideMapper.mapCreationDTOToDomain(slideCreationDTO);
        SlideDTO slideDTO = SlideMapper.mapDomainToDto(slideService.createSlide(slideDomain));
        return slideDTO;
    }

    @Override
    public List<SlideDTO> findAll() {
        List<SlideDTO> slidesDTOS = slideService.getAll()
                .stream().map(SlideMapper::mapDomainToDto)
                .collect(Collectors.toList());
        return slidesDTOS;
    }

    @ExceptionHandler(SlideNotFoundException.class)
    public ResponseEntity<ApiErrorDTO> handleUserNotFoundExceptions(SlideNotFoundException ex) {
        ApiErrorDTO slideNotFound =
                ApiErrorDTO.builder()
                        .code(HttpStatus.NOT_FOUND)
                        .message(ex.getMessage()).build();
        return new ResponseEntity(slideNotFound, HttpStatus.NOT_FOUND);

    }
}