package com.alkemy.ong.controller.impl;

import com.alkemy.ong.controller.ActivityController;
import com.alkemy.ong.domain.Activity;
import com.alkemy.ong.dto.ActivityCreationDTO;
import com.alkemy.ong.dto.ActivityDTO;
import com.alkemy.ong.dto.ActivityUpdateDTO;
import com.alkemy.ong.exception.OngRequestException;
import com.alkemy.ong.mapper.ActivityMapper;
import com.alkemy.ong.service.ActivityService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "ActivityResource", tags = {"Activities"})
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
    public ActivityDTO updateActivity(Long id, ActivityUpdateDTO activityUpdateDTO) throws OngRequestException {
        Activity activityDomain = ActivityMapper.mapUpdateDTOToDomain(activityUpdateDTO);
        ActivityDTO activityDTO = ActivityMapper.mapDomainToDTO(activityService.updateActivity(id, activityDomain));
        return activityDTO;
    }
}