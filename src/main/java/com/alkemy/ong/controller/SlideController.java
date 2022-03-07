package com.alkemy.ong.controller;

import com.alkemy.ong.domain.Slide;
import com.alkemy.ong.dto.ErrorDTO;
import com.alkemy.ong.dto.SlideCreationDTO;
import com.alkemy.ong.dto.SlideDTO;
import com.alkemy.ong.dto.SlideUpdateDTO;
import com.alkemy.ong.exception.SlideNotFoundException;
import com.alkemy.ong.mapper.SlideMapper;
import com.alkemy.ong.service.SlideService;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SlideController {

    private final SlideService slideService;

    public SlideController(SlideService slideService) {
        this.slideService = slideService;
    }

    @GetMapping("/slides/{id}")
    public ResponseEntity<SlideDTO> slideDetails(@PathVariable("id") Long id) throws SlideNotFoundException {
        return ResponseEntity.ok(SlideMapper.mapDomainToDto(slideService.slideDetails(id)));
    }

    @DeleteMapping("/slides/{id}")
    public ResponseEntity deleteSlide(@PathVariable("id") Long id) throws SlideNotFoundException {
        slideService.deleteSlide(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler(SlideNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleUserNotFoundExceptions(SlideNotFoundException ex) {
        ErrorDTO slideNotFound =
                ErrorDTO.builder()
                        .code(HttpStatus.NOT_FOUND)
                        .message(ex.getMessage()).build();
        return new ResponseEntity(slideNotFound, HttpStatus.NOT_FOUND);

    }

    @PutMapping("/slides/{id}")
    public ResponseEntity<SlideDTO> updateSlide(@PathVariable Long id, @RequestBody SlideUpdateDTO slideUpdateDTO) throws SlideNotFoundException {
        Slide slide = SlideMapper.mapUpdateDTOToDomain(slideUpdateDTO);
        SlideDTO slideDTO = SlideMapper.mapDomainToDto(slideService.updateSlide(id, slide));
        return ResponseEntity.ok(slideDTO);
    }

    @PostMapping("/slides")
    public ResponseEntity<SlideDTO> createSlide(@Valid @RequestBody SlideCreationDTO slideCreationDTO) {
        Slide slideDomain = SlideMapper.mapCreationDTOToDomain(slideCreationDTO);
        SlideDTO slideDTO = SlideMapper.mapDomainToDto(slideService.createSlide(slideDomain));
        return ResponseEntity.ok(slideDTO);
    }

    @GetMapping("/slides")
    public ResponseEntity<List<SlideDTO>> getAll() {
        List<SlideDTO> slidesDTOS = slideService.getAll()
                .stream().map(SlideMapper::mapDomainToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(slidesDTOS);
    }
}