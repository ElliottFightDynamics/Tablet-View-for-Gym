package com.striketec.fanapp.presenter.events.fragment;

import android.support.v4.view.ViewPager;

import com.striketec.fanapp.R;
import com.striketec.fanapp.view.events.adapter.ViewPagerAdapter;
import com.striketec.fanapp.view.events.fragment.EventsFragment;
import com.striketec.fanapp.view.events.fragment.EventsHolderFragment;
import com.striketec.fanapp.view.events.fragment.EventsHolderFragmentInteractor;

/**
 * Created by Sukhbirs on 17-11-2017.
 * This is EventsHolderFragment Presenter implementation.
 */

public class EventsHolderFragmentPresenterImpl implements EventsHolderFragmentPresenter {

    private EventsHolderFragmentInteractor mEventsHolderFragmentInteractor;
    private EventsHolderFragment mEventsHolderFragment;

    public EventsHolderFragmentPresenterImpl(EventsHolderFragmentInteractor mEventsHolderFragmentInteractor) {
        this.mEventsHolderFragmentInteractor = mEventsHolderFragmentInteractor;
        mEventsHolderFragment = (EventsHolderFragment) mEventsHolderFragmentInteractor;
    }

    @Override
    public void setupViewPagerAdapter(ViewPager mViewPager) {
        ViewPagerAdapter mAdapter = new ViewPagerAdapter(mEventsHolderFragment.getActivity().getSupportFragmentManager());
        mAdapter.addFragment(new EventsFragment(), mEventsHolderFragment.getString(R.string.tab_title_my_events));
        mAdapter.addFragment(new EventsFragment(), mEventsHolderFragment.getString(R.string.tab_title_all_events));
        mViewPager.setAdapter(mAdapter);

        // set ViewPager to TabLayout.
        mEventsHolderFragmentInteractor.setTabLayoutWithViewPager();
    }

    @Override
    public void onDestroy() {
        mEventsHolderFragmentInteractor = null;
    }
}
