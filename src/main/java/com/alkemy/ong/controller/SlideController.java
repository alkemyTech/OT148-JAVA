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
import com.alkemy.ong.dto.ErrorDTO;
import com.alkemy.ong.dto.SlideDTO;
import com.alkemy.ong.exception.SlideNotFoundException;
import com.alkemy.ong.mapper.SlideMapper;
import com.alkemy.ong.service.SlideService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/slides/{id}")
    public ResponseEntity<SlideDTO> slideDetails(@PathVariable("id") Long id) throws SlideNotFoundException {
        return ResponseEntity.ok(SlideMapper.mapDomainToDto(slideService.slideDetails(id)));
    }


    @ExceptionHandler(SlideNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleUserNotFoundExceptions(SlideNotFoundException ex) {
        ErrorDTO slideNotFound =
                ErrorDTO.builder()
                        .code(HttpStatus.NOT_FOUND)
                        .message(ex.getMessage()).build();
        return new ResponseEntity(slideNotFound, HttpStatus.NOT_FOUND);

    }
}