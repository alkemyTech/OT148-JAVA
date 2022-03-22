package com.alkemy.ong.service;

import com.alkemy.ong.domain.Activity;
import com.alkemy.ong.exception.OngRequestException;
import com.alkemy.ong.mapper.ActivityMapper;
import com.alkemy.ong.repository.ActivityRepository;
import com.alkemy.ong.repository.model.ActivityModel;

import javax.transaction.Transactional;
import java.util.Optional;

public class ActivityService {

    private final ActivityRepository activityRepository;

    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Transactional
    public Activity createActivity(Activity activity) {
        ActivityModel activityModel = activityRepository.save(ActivityMapper.mapDomainToModel(activity));
        Activity activitySave = ActivityMapper.mapModelToDomain(activityModel);
        return activitySave;
    }

    @Transactional
    public Activity updateActivity(Long id, Activity activity) throws OngRequestException {
        Optional<ActivityModel> optionalActivityModel = activityRepository.findById(id);
        if (optionalActivityModel.isEmpty()) {
            throw new OngRequestException("Activity not found", "not.found");
        }
        ActivityModel activityModel = optionalActivityModel.get();
        activityModel.setName(activity.getName());
        activityModel.setContent(activity.getContent());
        activityModel.setImage(activity.getImage());
        return ActivityMapper.mapModelToDomain(activityRepository.save(activityModel));
    }

}
