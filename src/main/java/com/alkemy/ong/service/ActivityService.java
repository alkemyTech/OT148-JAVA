package com.alkemy.ong.service;

import com.alkemy.ong.domain.Activity;
import com.alkemy.ong.mapper.ActivityMapper;
import com.alkemy.ong.repository.ActivityRepository;
import com.alkemy.ong.repository.model.ActivityModel;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

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
    public List<Activity> findAll() {
        List<ActivityModel> activityModelList = (List<ActivityModel>) activityRepository.findAll();
        return activityModelList.stream().map(ActivityMapper::mapModelToDomain)
                .collect(Collectors.toList());
    }

}
