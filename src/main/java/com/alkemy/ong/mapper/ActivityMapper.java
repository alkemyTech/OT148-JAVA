package com.alkemy.ong.mapper;

import com.alkemy.ong.domain.Activity;
import com.alkemy.ong.repository.model.ActivityModel;
import lombok.Builder;
import org.springframework.stereotype.Component;

@Component
public class ActivityMapper {

    public static Activity mapModelToDomain( ActivityModel activityModel){
       return new Activity (activityModel.getName(), activityModel.getContent(), activityModel.getImage(), activityModel.getCreatedDate(), activityModel.getModifiedDate(),activityModel.getDeletedDate(), activityModel.isActive());
    }
    public static ActivityModel mapDomainToModel (Activity activity){
        return new ActivityModel (activity.getName(), activity.getContent(), activity.getImage(), activity.getCreationDate(),activity.getModifiedDate(),activity.getDeletedDate(), activity.getIsActive());
    }
}
