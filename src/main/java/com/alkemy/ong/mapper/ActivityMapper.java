package com.alkemy.ong.mapper;

import com.alkemy.ong.domain.Activity;
import com.alkemy.ong.repository.model.ActivityModel;

public class ActivityMapper {
    public static Activity mapModelToDomain(ActivityModel activityModel) {
        Activity activity = Activity.builder()
                .id(activityModel.getId())
                .name(activityModel.getName())
                .content(activityModel.getContent())
                .image(activityModel.getImage())
                .creationDate(activityModel.getCreationDate())
                .build();
        return activity;
    }

    public static ActivityModel mapDomainToModel(Activity activity) {
        ActivityModel activityModel = ActivityModel.builder()
                .name(activity.getName())
                .content(activity.getContent())
                .image(activity.getImage())
                .creationDate(activity.getCreationDate())
                .build();
        return activityModel;
    }
}
