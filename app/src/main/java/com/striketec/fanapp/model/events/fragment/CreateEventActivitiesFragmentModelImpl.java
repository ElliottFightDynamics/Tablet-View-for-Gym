package com.striketec.fanapp.model.events.fragment;

import com.striketec.fanapp.R;
import com.striketec.fanapp.view.events.CreateEventActivityInfo;
import com.striketec.fanapp.view.events.fragment.CreateEventActivitiesFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sukhbirs on 21-11-2017.
 */

public class CreateEventActivitiesFragmentModelImpl implements CreateEventActivitiesFragmentModel {
    private CreateEventActivitiesFragment mFragment;
    public CreateEventActivitiesFragmentModelImpl(CreateEventActivitiesFragment mFragment) {
        this.mFragment = mFragment;
    }

    @Override
    public List<CreateEventActivityInfo> getActivityInfoList() {
        List<CreateEventActivityInfo> activityInfoList = new ArrayList<>();
        String[] activityNamesArray = mFragment.getResources().getStringArray(R.array.create_event_activity_name_array);
        String[] activityDescriptionArray = mFragment.getResources().getStringArray(R.array.create_event_activity_description_array);
        int[] drawableArray = mFragment.getResources().getIntArray(R.array.create_event_activity_drawable_array);
        for (int i = 0; i < activityNamesArray.length; i++){
            CreateEventActivityInfo activityInfo = new CreateEventActivityInfo();
            activityInfo.setActivityName(activityNamesArray[i]);
            activityInfo.setDescription(activityDescriptionArray[i]);
            activityInfo.setActivityDrawable(drawableArray[i]);
            activityInfoList.add(activityInfo);
        }
        return activityInfoList;
    }
}
