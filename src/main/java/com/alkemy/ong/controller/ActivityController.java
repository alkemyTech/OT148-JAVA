package com.alkemy.ong.controller;

import com.alkemy.ong.dto.ActivityCreationDTO;
import com.alkemy.ong.dto.ActivityDTO;
import com.alkemy.ong.dto.ActivityUpdateDTO;
import com.alkemy.ong.exception.OngRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;

public interface ActivityController {

    @PostMapping("/activities")
    @ResponseStatus(HttpStatus.CREATED)
    ActivityDTO createActivity(@Valid @RequestBody ActivityCreationDTO activityCreationDTO);

    @PutMapping("/activities/{id}")
    @ResponseStatus(HttpStatus.OK)
    ActivityDTO updateActivity(@PathVariable Long id, @RequestBody ActivityUpdateDTO activityUpdateDTO) throws OngRequestException;

}
