package com.striketec.fanapp.presenter.events.fragment;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Sukhbirs on 21-11-2017.
 */

public interface CreateEventActivitiesFragmentPresenter {
    void setActivityListData(RecyclerView recyclerView);

    void onDetach();
}
