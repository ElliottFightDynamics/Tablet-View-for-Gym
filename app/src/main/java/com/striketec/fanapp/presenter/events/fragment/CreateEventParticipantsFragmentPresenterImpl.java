package com.striketec.fanapp.presenter.events.fragment;

import com.striketec.fanapp.model.api.RestUrl;
import com.striketec.fanapp.model.api.response.ResponseArray;
import com.striketec.fanapp.model.events.fragment.CreateEventParticipantsFragmentModel;
import com.striketec.fanapp.model.events.fragment.CreateEventParticipantsFragmentModelImpl;
import com.striketec.fanapp.model.users.dto.UserInfo;
import com.striketec.fanapp.view.events.fragment.CreateEventParticipantsFragment;
import com.striketec.fanapp.view.events.fragment.CreateEventParticipantsFragmentInteractor;

import java.util.List;

/**
 * Created by Sukhbirs on 23-11-2017.
 * This is presenter implementation class for Create Event Step 3 Add Participants Screen.
 */

public class CreateEventParticipantsFragmentPresenterImpl implements CreateEventParticipantsFragmentPresenter, CreateEventParticipantsFragmentModel.OnLoadParticipantsListener {

    private CreateEventParticipantsFragmentInteractor mParticipantsFragmentInteractor;
    private CreateEventParticipantsFragment mParticipantsFragment;
    private CreateEventParticipantsFragmentModel mParticipantsFragmentModel;

    public CreateEventParticipantsFragmentPresenterImpl(CreateEventParticipantsFragmentInteractor mParticipantsFragmentInteractor) {
        this.mParticipantsFragmentInteractor = mParticipantsFragmentInteractor;
        mParticipantsFragment = (CreateEventParticipantsFragment) mParticipantsFragmentInteractor;
        mParticipantsFragmentModel = new CreateEventParticipantsFragmentModelImpl(this);
    }

    @Override
    public void loadUsersListFromServer() {
        if (mParticipantsFragmentInteractor != null) {
            mParticipantsFragmentInteractor.showProgressBar();
        }
        mParticipantsFragmentModel.loadUsersListFromServer();
    }

    @Override
    public void onDetach() {
        mParticipantsFragmentInteractor = null;
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
