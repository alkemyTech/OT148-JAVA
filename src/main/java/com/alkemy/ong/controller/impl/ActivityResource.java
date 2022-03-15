package com.alkemy.ong.controller.impl;

import com.alkemy.ong.controller.ActivityController;
import com.alkemy.ong.domain.Activity;
import com.alkemy.ong.dto.ActivityCreationDTO;
import com.alkemy.ong.dto.ActivityDTO;
import com.alkemy.ong.dto.ActivityUpdateDTO;
import com.alkemy.ong.exception.ActivityNotFoundException;
import com.alkemy.ong.mapper.ActivityMapper;
import com.alkemy.ong.service.ActivityService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ActivityResource implements ActivityController {

    private final ActivityService activityService;

    public ActivityResource(ActivityService activityService) {
        this.activityService = activityService;
    }

    @Override
    public ActivityDTO createActivity(ActivityCreationDTO activityCreationDTO) {
        Activity activity = activityService.createActivity(ActivityMapper.mapCreationDTOToDomain(activityCreationDTO));
        ActivityDTO activityDTO = ActivityMapper.mapDomainToDTO(activity);
        return activityDTO;
    }

    @Override
    public ActivityDTO updateActivity(Long id, ActivityUpdateDTO activityUpdateDTO) throws ActivityNotFoundException {
        Activity activityDomain = ActivityMapper.mapUpdateDTOToDomain(activityUpdateDTO);
        ActivityDTO activityDTO = ActivityMapper.mapDomainToDTO(activityService.updateActivity(id, activityDomain));
        return activityDTO;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}