package com.alkemy.ong.controller;

import com.alkemy.ong.domain.Activity;
import com.alkemy.ong.dto.ActivityCreationDTO;
import com.alkemy.ong.dto.ActivityDTO;
import com.alkemy.ong.dto.ErrorDTO;
import com.alkemy.ong.exception.ActivityNotFoundException;
import com.alkemy.ong.mapper.ActivityMapper;
import com.alkemy.ong.service.ActivityService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ActivityController {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @PostMapping("/activities")
    public ResponseEntity<ActivityDTO> createActivity(@Valid @RequestBody ActivityCreationDTO activityCreationDTO) throws ActivityNotFoundException {

        Activity activity = activityService.createActivity(ActivityMapper.mapCreationDTOToDomain(activityCreationDTO));
        ActivityDTO activityDTO = ActivityMapper.mapDomainToDTO(activity);
        return ResponseEntity.status(HttpStatus.CREATED).body(activityDTO);
    }

    @GetMapping("/activities")
    public ResponseEntity<List<ActivityDTO>> getAll() {
        List<ActivityDTO> activityDTOS = activityService.findAll()
                .stream().map(ActivityMapper::mapDomainToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(activityDTOS);
    }

    @ExceptionHandler(ActivityNotFoundException.class)
    private ResponseEntity<ErrorDTO> handleActivityNotFound(ActivityNotFoundException ex) {
        ErrorDTO activityNotFound =
                ErrorDTO.builder()
                        .code(HttpStatus.NOT_FOUND)
                        .message(ex.getMessage()).build();
        return new ResponseEntity(activityNotFound, HttpStatus.NOT_FOUND);
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
