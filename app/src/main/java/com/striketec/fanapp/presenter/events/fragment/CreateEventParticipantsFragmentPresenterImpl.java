package com.striketec.fanapp.presenter.events.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.striketec.fanapp.model.api.RestUrl;
import com.striketec.fanapp.model.api.response.ResponseArray;
import com.striketec.fanapp.model.api.response.ResponseObject;
import com.striketec.fanapp.model.events.dto.CreateEventInfo;
import com.striketec.fanapp.model.events.dto.CreateEventResInfo;
import com.striketec.fanapp.model.events.fragment.CreateEventParticipantsFragmentModel;
import com.striketec.fanapp.model.events.fragment.CreateEventParticipantsFragmentModelImpl;
import com.striketec.fanapp.model.users.dto.UserInfo;
import com.striketec.fanapp.utils.constants.ErrorConstants;
import com.striketec.fanapp.view.events.CreateEventActivity;
import com.striketec.fanapp.view.events.fragment.CreateEventParticipantsFragment;
import com.striketec.fanapp.view.events.fragment.CreateEventParticipantsFragmentInteractor;
import com.striketec.fanapp.view.users.adapter.UsersAdapter;

import java.util.List;

/**
 * Created by Sukhbirs on 23-11-2017.
 * This is presenter implementation class for Create Event Step 3 Add Participants Screen.
 */

public class CreateEventParticipantsFragmentPresenterImpl implements CreateEventParticipantsFragmentPresenter, CreateEventParticipantsFragmentModel.OnLoadParticipantsListener {

    private CreateEventParticipantsFragmentInteractor mParticipantsFragmentInteractor;
    private CreateEventParticipantsFragment mParticipantsFragment;
    private CreateEventParticipantsFragmentModel mParticipantsFragmentModel;
    private UsersAdapter mUsersAdapter;

    public CreateEventParticipantsFragmentPresenterImpl(CreateEventParticipantsFragmentInteractor mParticipantsFragmentInteractor) {
        this.mParticipantsFragmentInteractor = mParticipantsFragmentInteractor;
        mParticipantsFragment = (CreateEventParticipantsFragment) mParticipantsFragmentInteractor;
        mParticipantsFragmentModel = new CreateEventParticipantsFragmentModelImpl(this);
    }

    @Override
    public void loadUsersListFromServer(String token) {
        if (mParticipantsFragmentInteractor != null) {
            mParticipantsFragmentInteractor.showProgressBar();
        }
        mParticipantsFragmentModel.loadUsersListFromServer(token);
    }

    @Override
    public void onDestroy() {
        mParticipantsFragmentInteractor = null;
    }

    @Override
    public void showUsersListOnUI(RecyclerView mParticipantsRecyclerView, List<UserInfo> mUserInfoList) {
        mUsersAdapter = new UsersAdapter(mParticipantsFragment.getActivity(), mUserInfoList);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(mParticipantsFragment.getActivity(), 3);
        mParticipantsRecyclerView.setLayoutManager(mGridLayoutManager);
        mParticipantsRecyclerView.setAdapter(mUsersAdapter);
    }

    @Override
    public void createEvent(String token) {
        try {
            if (mParticipantsFragmentInteractor != null) {
                mParticipantsFragmentInteractor.showProgressDialog();
            }
            CreateEventInfo createEventInfo = new CreateEventInfo();
            ((CreateEventActivity) mParticipantsFragment.getActivity()).getCompleteEventDetails(createEventInfo);
            if (mUsersAdapter != null) {
                List<UserInfo> selectedUserInfoList = mUsersAdapter.getmSelectedUserInfoList();
                StringBuffer userIds = new StringBuffer();
                if (selectedUserInfoList != null && selectedUserInfoList.size() > 0) {
                    for (int i = 0; i < selectedUserInfoList.size(); i++) {
                        UserInfo userInfo = selectedUserInfoList.get(i);
                        if (userInfo != null) {
                            if (i != 0) {
                                userIds.append(",");
                            }
                            userIds.append(userInfo.getId());
                        }
                    }
                }
                createEventInfo.setUserIds(userIds.toString());
            }
            mParticipantsFragmentModel.createEvent(token, createEventInfo);
        } catch (Exception e) {
            e.printStackTrace();
            mParticipantsFragmentInteractor.hideProgressDialog();
            mParticipantsFragmentInteractor.setWebApiError(ErrorConstants.SOMETHING_WENT_WRONG);
        }
    }

    @Override
    public void onResponseSuccess(Object responseObject, String whichApi) {
        if (mParticipantsFragmentInteractor != null) {
            mParticipantsFragmentInteractor.hideProgressBar();
            // check whether it is response of event locations list data web API or other web API.
            if (whichApi != null) {
                if (whichApi.equals(RestUrl.GET_USERS_LIST)) {
                    if (responseObject != null) {
                        ResponseArray<UserInfo> responseArray = (ResponseArray<UserInfo>) responseObject;
                        List<UserInfo> userInfoList = responseArray.getmData();
                        mParticipantsFragmentInteractor.setUsersList(userInfoList);
                    }
                } else if (whichApi.equals(RestUrl.CREATE_EVENT)) {
                    if (responseObject != null) {
                        ResponseObject<CreateEventResInfo> createEventResObject = (ResponseObject<CreateEventResInfo>) responseObject;
                        if (createEventResObject != null) {
//                        CreateEventResInfo createEventResInfo = createEventResObject.getmData();
                            mParticipantsFragmentInteractor.navigateToPreviousScreen(createEventResObject.getmMessage());
                        } else {
                            mParticipantsFragmentInteractor.setWebApiError(null);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onResponseError(String errorMessage) {
        if (mParticipantsFragmentInteractor != null) {
            mParticipantsFragmentInteractor.hideProgressBar();
            mParticipantsFragmentInteractor.setWebApiError(errorMessage);
        }
    }
}
