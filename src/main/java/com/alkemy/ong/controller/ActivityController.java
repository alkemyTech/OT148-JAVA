package com.alkemy.ong.controller;

import com.alkemy.ong.domain.Activity;
import com.alkemy.ong.dto.ActivityCreationDTO;
import com.alkemy.ong.dto.ActivityDTO;
import com.alkemy.ong.exception.ActivityNotFoundException;
import com.alkemy.ong.mapper.ActivityMapper;
import com.alkemy.ong.service.ActivityService;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ActivityController {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @PostMapping("/activities")
    public ResponseEntity<ActivityDTO> createActivity(@Valid @RequestBody ActivityCreationDTO activityCreationDTO)
            throws ActivityNotFoundException {
        Activity activityDomain = ActivityMapper.mapCreationDTOToDomain(activityCreationDTO);
        ActivityDTO activityDTO = ActivityMapper.mapDomainToDTO(activityService.createActivity(activityDomain));
        return ResponseEntity.ok(activityDTO);
    }

    @GetMapping("/activities")
    public ResponseEntity<List<ActivityDTO>> getAll() {
        List<ActivityDTO> activityDTOS = activityService.findAll()
                .stream().map(ActivityMapper::mapDomainToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(activityDTOS);
    }
}
