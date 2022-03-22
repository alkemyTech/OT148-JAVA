package com.alkemy.ong.controller;

import com.alkemy.ong.dto.SlideCreationDTO;
import com.alkemy.ong.dto.SlideDTO;
import com.alkemy.ong.dto.SlideUpdateDTO;
import com.alkemy.ong.exception.OngRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;
import java.util.List;

public interface SlideController {

    @GetMapping("/slides/{id}")
    @ResponseStatus(HttpStatus.OK)
    SlideDTO slideDetails(@PathVariable("id") Long id) throws OngRequestException;

    @DeleteMapping("/slides/{id}")
    @ResponseStatus(HttpStatus.OK)
    void deleteSlide(@PathVariable("id") Long id) throws OngRequestException;

    @PutMapping("/slides/{id}")
    @ResponseStatus(HttpStatus.OK)
    SlideDTO updateSlide(@PathVariable Long id, @RequestBody SlideUpdateDTO slideUpdateDTO) throws OngRequestException;

    @PostMapping("/slides")
    @ResponseStatus(HttpStatus.CREATED)
    SlideDTO createSlide(@Valid @RequestBody SlideCreationDTO slideCreationDTO);

    @GetMapping("/slides")
    @ResponseStatus(HttpStatus.OK)
    List<SlideDTO> findAll();
}