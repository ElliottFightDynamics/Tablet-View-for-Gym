package com.striketec.fanapp.view.users.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.striketec.fanapp.R;
import com.striketec.fanapp.model.users.dto.UserInfo;
import com.striketec.fanapp.utils.constants.Constants;
import com.striketec.fanapp.view.users.adapter.UsersAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * This fragment is used to display the list of users.
 */
public class UserDatabaseFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private RecyclerView mUserRecyclerView;
    private AddUserDialogFragment mAddUserDialogFragment;

    public UserDatabaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_user_database, container, false);
        findViewByIds(mView);
        return mView;
    }

    private void findViewByIds(View view) {
        mUserRecyclerView = view.findViewById(R.id.users_recycler_view);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        showUsersList();
    }

    private void showUsersList() {
        List<UserInfo> mUserInfoList = getUserInfoList();

        UsersAdapter mUsersAdapter = new UsersAdapter(getActivity(), mUserInfoList);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getActivity(), 3);
        mUserRecyclerView.setLayoutManager(mGridLayoutManager);
        mUserRecyclerView.setAdapter(mUsersAdapter);
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
     * Method to show Add User DialogFragment.
     */
    public void showAddUserDialogFragment() {
        mAddUserDialogFragment = new AddUserDialogFragment();
        mAddUserDialogFragment.show(getActivity().getFragmentManager(), Constants.DIALOG_FRAGMENT_TAG_ADD_USER);
    }

    /**
     * Method to set the captured image.
     */
    public void previewCapturedImage() {
        if (mAddUserDialogFragment == null) {
            return;
        }
        mAddUserDialogFragment.previewCapturedImage();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

}
