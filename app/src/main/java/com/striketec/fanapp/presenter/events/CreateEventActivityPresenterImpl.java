package com.striketec.fanapp.presenter.events;

import android.support.v4.view.ViewPager;

import com.striketec.fanapp.R;
import com.striketec.fanapp.view.events.CreateEventActivity;
import com.striketec.fanapp.view.events.CreateEventActivityInteractor;
import com.striketec.fanapp.view.events.adapter.ViewPagerAdapter;
import com.striketec.fanapp.view.events.fragment.CreateEventActivitiesFragment;
import com.striketec.fanapp.view.events.fragment.CreateEventInfoFragment;
import com.striketec.fanapp.view.events.fragment.CreateEventParticipantsFragment;

/**
 * Created by Sukhbirs on 20-11-2017.
 */

public class CreateEventActivityPresenterImpl implements CreateEventActivityPresenter {

    private CreateEventActivityInteractor mCreateEventActivityInteractor;
    private CreateEventActivity mCreateEventActivity;

    public CreateEventActivityPresenterImpl(CreateEventActivityInteractor mCreateEventActivityInteractor) {
        this.mCreateEventActivityInteractor = mCreateEventActivityInteractor;
        mCreateEventActivity = (CreateEventActivity) mCreateEventActivityInteractor;
    }

    @Override
    public void setupViewPagerAdapter(ViewPager mViewPager) {
        ViewPagerAdapter mAdapter = new ViewPagerAdapter(mCreateEventActivity.getSupportFragmentManager());
        mAdapter.addFragment(new CreateEventInfoFragment(), mCreateEventActivity.getString(R.string.fragment_create_event_step_1_title));
        mAdapter.addFragment(new CreateEventActivitiesFragment(), mCreateEventActivity.getString(R.string.fragment_create_event_step_2_title));
        mAdapter.addFragment(new CreateEventParticipantsFragment(), mCreateEventActivity.getString(R.string.fragment_create_event_step_3_title));
        mViewPager.setAdapter(mAdapter);
    }

}
