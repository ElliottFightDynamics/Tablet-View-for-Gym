package com.striketec.fanapp.presenter.events;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.striketec.fanapp.model.users.dto.UserInfo;

import java.util.List;

/**
 * Created by Sukhbirs on 17-11-2017.
 */

public interface CreateEventActivityPresenter {
    void setupViewPagerAdapter(ViewPager mViewPager);
    Fragment getCurrentFragment(int position);
    void handleNextClick(int position);
    void handleCancelClick(int position);
}
