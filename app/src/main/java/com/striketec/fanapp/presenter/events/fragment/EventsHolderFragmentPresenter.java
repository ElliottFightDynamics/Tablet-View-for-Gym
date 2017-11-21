package com.striketec.fanapp.presenter.events.fragment;

import android.support.v4.view.ViewPager;

/**
 * Created by Sukhbirs on 17-11-2017.
 */

public interface EventsHolderFragmentPresenter {
    void setupViewPagerAdapter(ViewPager mViewPager);

    void onDetach();
}
