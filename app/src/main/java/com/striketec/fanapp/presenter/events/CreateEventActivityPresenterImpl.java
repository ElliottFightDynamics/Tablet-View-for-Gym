package com.striketec.fanapp.presenter.events;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.striketec.fanapp.R;
import com.striketec.fanapp.utils.constants.Constants;
import com.striketec.fanapp.view.events.CreateEventActivity;
import com.striketec.fanapp.view.events.CreateEventActivityInteractor;
import com.striketec.fanapp.view.events.adapter.ViewPagerAdapter;
import com.striketec.fanapp.view.events.fragment.CreateEventActivitiesFragment;
import com.striketec.fanapp.view.events.fragment.CreateEventInfoFragment;
import com.striketec.fanapp.view.events.fragment.CreateEventParticipantsFragment;

/**
 * Created by Sukhbirs on 20-11-2017.
 * This is presenter implementation class for CreateEventActivity (CreateEventActivity contains three steps as fragments to create event).
 */

public class CreateEventActivityPresenterImpl implements CreateEventActivityPresenter {

    private CreateEventActivityInteractor mCreateEventActivityInteractor;
    private CreateEventActivity mCreateEventActivity;
    private ViewPagerAdapter mAdapter;

    public CreateEventActivityPresenterImpl(CreateEventActivityInteractor mCreateEventActivityInteractor) {
        this.mCreateEventActivityInteractor = mCreateEventActivityInteractor;
        mCreateEventActivity = (CreateEventActivity) mCreateEventActivityInteractor;
    }

    @Override
    public void setupViewPagerAdapter(ViewPager mViewPager) {
        mAdapter = new ViewPagerAdapter(mCreateEventActivity.getSupportFragmentManager());
        mAdapter.addFragment(new CreateEventInfoFragment(), mCreateEventActivity.getString(R.string.fragment_create_event_step_1_title));
        mAdapter.addFragment(new CreateEventActivitiesFragment(), mCreateEventActivity.getString(R.string.fragment_create_event_step_2_title));
        mAdapter.addFragment(new CreateEventParticipantsFragment(), mCreateEventActivity.getString(R.string.fragment_create_event_step_3_title));
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(mAdapter);
    }

    @Override
    public Fragment getCurrentFragment(int position) {
        if (mAdapter != null) {
            return mAdapter.getItem(position);
        } else {
            return null;
        }
    }

    @Override
    public void handleNextClick(int position) {
        if (position == Constants.STEP_1_EVENT_INFO) {
            CreateEventInfoFragment mCreateEventInfoFragment = (CreateEventInfoFragment) getCurrentFragment(Constants.STEP_1_EVENT_INFO);
            mCreateEventInfoFragment.handleOnNextClick();
        } else if (position == Constants.STEP_2_SELECT_ACTIVITY) {
            CreateEventActivitiesFragment mCreateEventActivitiesFragment = (CreateEventActivitiesFragment) getCurrentFragment(Constants.STEP_2_SELECT_ACTIVITY);
            mCreateEventActivitiesFragment.handleOnNextClick();
        } else if (position == Constants.STEP_3_ADD_PARTICIPANTS) {
            CreateEventParticipantsFragment mCreateEventParticipantsFragment = (CreateEventParticipantsFragment) getCurrentFragment(Constants.STEP_3_ADD_PARTICIPANTS);
            mCreateEventParticipantsFragment.handleOnDoneClick();
        }
    }

    @Override
    public void handleCancelClick(int position) {
        if (position == Constants.STEP_2_SELECT_ACTIVITY) {
            mCreateEventActivityInteractor.navigateToCreateEventStep1();
        } else if (position == Constants.STEP_3_ADD_PARTICIPANTS) {
            mCreateEventActivityInteractor.navigateToCreateEventStep2();
        }
    }
}
