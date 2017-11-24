package com.striketec.fanapp.presenter.events.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.striketec.fanapp.model.events.fragment.CreateEventActivitiesFragmentModel;
import com.striketec.fanapp.model.events.fragment.CreateEventActivitiesFragmentModelImpl;
import com.striketec.fanapp.view.events.CreateEventActivityInfo;
import com.striketec.fanapp.view.events.adapter.CreateEventActivityAdapter;
import com.striketec.fanapp.view.events.fragment.CreateEventActivitiesFragment;
import com.striketec.fanapp.view.events.fragment.CreateEventActivitiesFragmentInteractor;

import java.util.List;

import static android.support.v7.widget.LinearLayoutManager.HORIZONTAL;

/**
 * Created by Sukhbirs on 21-11-2017.
 * This is presenter implementation class for Create Event Step 2 Select Activity screen.
 */

public class CreateEventActivitiesFragmentPresenterImpl implements CreateEventActivitiesFragmentPresenter {

    private CreateEventActivitiesFragmentInteractor mActivitiesFragmentInteractor;
    private CreateEventActivitiesFragmentModel mCreateEventActivitiesModel;
    private CreateEventActivitiesFragment mFragment;

    public CreateEventActivitiesFragmentPresenterImpl(CreateEventActivitiesFragmentInteractor mActivitiesFragmentInteractor) {
        this.mActivitiesFragmentInteractor = mActivitiesFragmentInteractor;
        mFragment = (CreateEventActivitiesFragment) mActivitiesFragmentInteractor;
        mCreateEventActivitiesModel = new CreateEventActivitiesFragmentModelImpl(mFragment);
    }

    @Override
    public void setActivityListData(RecyclerView recyclerView) {
        List<CreateEventActivityInfo> activityInfoList = mCreateEventActivitiesModel.getActivityInfoList();
        CreateEventActivityAdapter mActivityAdapter = new CreateEventActivityAdapter(mFragment.getActivity(), activityInfoList, null, new CreateEventActivityAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, CreateEventActivityInfo activityInfo) {
                mActivitiesFragmentInteractor.setSelectedActivity(activityInfo.getActivityName());
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mFragment.getActivity(), HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mActivityAdapter);
    }

    @Override
    public void validateEventSelectedActivity(String selectedActivity) {
        if (selectedActivity == null) {
            mActivitiesFragmentInteractor.setSelectActivityError();
        } else {
            // navigate to next step on Create Event Screen
            mActivitiesFragmentInteractor.navigateToCreateEventStep3();
        }
    }

    @Override
    public void onDestroy() {
        mCreateEventActivitiesModel = null;
        mActivitiesFragmentInteractor = null;
    }
}
