package com.striketec.fanapp.view.events.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.striketec.fanapp.R;
import com.striketec.fanapp.view.events.CreateEventActivity;

/**
 * This EventsFragment is used to display the list of events.
 */
public class EventsFragment extends Fragment implements EventsFragmentInteractor, View.OnClickListener {

    private OnFragmentInteractionListener mListener;
    private Button mCreateEventButton;

    public EventsFragment() {
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
        View view = inflater.inflate(R.layout.fragment_events, container, false);
        findViewByIds(view);
        return view;
    }

    private void findViewByIds(View view) {
        mCreateEventButton = view.findViewById(R.id.button_create_event);
        mCreateEventButton.setOnClickListener(this);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_create_event:
                navigateToCreateEventScreen();
                break;
            default:
                break;
        }
    }

    @Override
    public void navigateToCreateEventScreen() {
        Intent intent = new Intent(getActivity(), CreateEventActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
    }
}
