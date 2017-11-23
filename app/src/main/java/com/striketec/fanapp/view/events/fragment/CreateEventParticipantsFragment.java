package com.striketec.fanapp.view.events.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.striketec.fanapp.R;
import com.striketec.fanapp.model.users.dto.UserInfo;
import com.striketec.fanapp.presenter.events.fragment.CreateEventParticipantsFragmentPresenter;
import com.striketec.fanapp.presenter.events.fragment.CreateEventParticipantsFragmentPresenterImpl;
import com.striketec.fanapp.utils.DialogUtils;
import com.striketec.fanapp.view.users.adapter.UsersAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * This fragment is used to select the participants to the event or add user.
 */
public class CreateEventParticipantsFragment extends Fragment implements CreateEventParticipantsFragmentInteractor {

    private OnFragmentInteractionListener mListener;
    private CreateEventParticipantsFragmentPresenter mParticipantsFragmentPresenter;

    private Button mAddUsersToDb, mSearchButton;
    private RecyclerView mParticipantsRecyclerView;
    private ProgressBar mProgressBar;

    private List<UserInfo> mUserInfoList;

    public CreateEventParticipantsFragment() {
        // Required empty public constructor
        mParticipantsFragmentPresenter = new CreateEventParticipantsFragmentPresenterImpl(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_event_step_3, container, false);
        findViewByIds(view);
//        loadUsersListFromServer();
        showUsersList();
        return view;
    }

    /**
     * Method to fetch the users list from server.
     */
    private void loadUsersListFromServer() {
        mParticipantsFragmentPresenter.loadUsersListFromServer();
    }

    /**
     * Method to set the layout references.
     *
     * @param view
     */
    private void findViewByIds(View view) {
        // Step 1
        View mStep1SecondLineView = view.findViewById(R.id.view_step_1_second_line);
        TextView mStep1NumberTV = view.findViewById(R.id.tv_step_1_number);
        TextView mStep1EventInfoTV = view.findViewById(R.id.tv_step_1_event_info);
        mStep1SecondLineView.setBackgroundColor(getResources().getColor(R.color.color_1));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mStep1NumberTV.setBackground(getResources().getDrawable(R.drawable.steps_circle_shape_sold_bg));
        } else {
            mStep1NumberTV.setBackgroundDrawable(getResources().getDrawable(R.drawable.steps_circle_shape_sold_bg));
        }
        mStep1EventInfoTV.setTextColor(getResources().getColor(R.color.color_1));

        // Step 2
        View mStep2FirstLineView = view.findViewById(R.id.view_step_2_first_line);
        View mStep2SecondLineView = view.findViewById(R.id.view_step_2_second_line);
        TextView mStep2NumberTV = view.findViewById(R.id.tv_step_2_number);
        TextView mStep2ActivitiesTV = view.findViewById(R.id.tv_step_2_activities);
        mStep2FirstLineView.setBackgroundColor(getResources().getColor(R.color.color_1));
        mStep2SecondLineView.setBackgroundColor(getResources().getColor(R.color.color_1));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mStep2NumberTV.setBackground(getResources().getDrawable(R.drawable.steps_circle_shape_sold_bg));
        } else {
            mStep2NumberTV.setBackgroundDrawable(getResources().getDrawable(R.drawable.steps_circle_shape_sold_bg));
        }
        mStep2ActivitiesTV.setTextColor(getResources().getColor(R.color.color_1));

        // Step 3
        View mStep3FirstLineView = view.findViewById(R.id.view_step_3_first_line);
        View mStep3SecondLineView = view.findViewById(R.id.view_step_3_second_line);
        TextView mStep3NumberTV = view.findViewById(R.id.tv_step_3_number);
        TextView mStep3ParticipantsTV = view.findViewById(R.id.tv_step_3_add_participants);
        mStep3FirstLineView.setBackgroundColor(getResources().getColor(R.color.color_1));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mStep3NumberTV.setBackground(getResources().getDrawable(R.drawable.steps_circle_shape_sold_bg));
        } else {
            mStep3NumberTV.setBackgroundDrawable(getResources().getDrawable(R.drawable.steps_circle_shape_sold_bg));
        }
        mStep3ParticipantsTV.setTextColor(getResources().getColor(R.color.color_1));

        // Add Users To DB
        mAddUsersToDb = view.findViewById(R.id.button_add_users_to_db);
        // Search Button
        mSearchButton = view.findViewById(R.id.button_search);

        // Participants RecyclerView
        mParticipantsRecyclerView = view.findViewById(R.id.recycler_view_create_event_participants);

        // ProgressBar
        mProgressBar = view.findViewById(R.id.progress_bar);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
        mParticipantsRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
        mParticipantsRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setUsersList(List<UserInfo> userInfoList) {
        this.mUserInfoList = userInfoList;
    }

    @Override
    public void setWebApiError(String errorMessage) {
        DialogUtils.showToast(getActivity(), errorMessage);
    }

    private void showUsersList() {
        List<UserInfo> mUserInfoList = getUserInfoList();

        UsersAdapter mUsersAdapter = new UsersAdapter(getActivity(), mUserInfoList);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getActivity(), 3);
        mParticipantsRecyclerView.setLayoutManager(mGridLayoutManager);
        mParticipantsRecyclerView.setAdapter(mUsersAdapter);
    }

    private List<UserInfo> getUserInfoList() {
        List<UserInfo> mUserInfoList = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            UserInfo mUserInfo = new UserInfo();
            mUserInfo.setName("Full Name - " + i);
            mUserInfo.setEmail("abc@xyz.com - " + i);
            mUserInfoList.add(mUserInfo);
        }

        return mUserInfoList;
    }

    /**
     * Method to handle Next/Done button click event if it is on Create Event Step 3 Select Activity screen.
     */
    public void handleOnNextClick() {

    }

    public interface OnFragmentInteractionListener {
        void navigateToCreateEventStep3();
    }
}
