package com.alkemy.ong.service;

import com.alkemy.ong.domain.Activity;
import com.alkemy.ong.dto.ErrorDTO;
import com.alkemy.ong.exception.ActivityNotFoundException;
import com.alkemy.ong.mapper.ActivityMapper;
import com.alkemy.ong.mapper.CategoryMapper;
import com.alkemy.ong.repository.ActivityRepository;
import com.alkemy.ong.repository.model.ActivityModel;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class ActivityService {

    private final ActivityRepository activityRepository;

    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Transactional
    public Activity createActivity(Activity activity) {
        ActivityModel activityModel = ActivityMapper.mapDomainToModel(activity);
        ActivityModel saveModel = activityRepository.save(activityModel);
        return CategoryMapper.mapModelToDomain(saveModel);
    }

    @Transactional
    public List<Activity> findAll() {
        List<ActivityModel> activityModelList = (List<ActivityModel>) activityRepository.findAll();
        return activityModelList.stream().map(ActivityMapper::mapModelToDomain)
                .collect(Collectors.toList());
    }

    @ExceptionHandler(ActivityNotFoundException.class)
    private ResponseEntity<ErrorDTO> handleOrganizationNotFound(ActivityNotFoundException ex) {
        ErrorDTO activityNotFound =
                ErrorDTO.builder()
                        .code(HttpStatus.NOT_FOUND)
                        .message(ex.getMessage()).build();
        return new ResponseEntity(activityNotFound, HttpStatus.NOT_FOUND);
    }

}
