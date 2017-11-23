package com.striketec.fanapp.presenter.events;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

/**
 * Created by Sukhbirs on 17-11-2017.
 * This is presenter interface for Create Event Activity screen that holds step 1, 2 and 3 pages.
 */

public interface CreateEventActivityPresenter {
    void setupViewPagerAdapter(ViewPager mViewPager);

    Fragment getCurrentFragment(int position);

    void handleNextClick(int position);

    void handleCancelClick(int position);
}
