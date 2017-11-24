package com.striketec.fanapp.presenter.events.fragment;


import android.support.v7.widget.RecyclerView;

import com.striketec.fanapp.model.users.dto.UserInfo;

import java.util.List;

/**
 * Created by Sukhbirs on 23-11-2017.
 * This is presenter interface for Create event Step 3 add participants screen.
 */

public interface CreateEventParticipantsFragmentPresenter {
    void loadUsersListFromServer(String token);

    void onDestroy();

    void showUsersListOnUI(RecyclerView mRecyclerView, List<UserInfo> mUserInfoList);

    void createEvent(String token);
}
