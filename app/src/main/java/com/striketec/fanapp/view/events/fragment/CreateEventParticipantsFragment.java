package com.striketec.fanapp.view.events.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.striketec.fanapp.R;

/**
 * This fragment is used to select the participants to the event or add user.
 */
public class CreateEventParticipantsFragment extends Fragment implements CreateEventParticipantsFragmentInteractor {

    private OnFragmentInteractionListener mListener;

    public CreateEventParticipantsFragment() {
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
        View view = inflater.inflate(R.layout.fragment_create_event_step_3, container, false);
        findViewByIds(view);
        return view;
    }

    /**
     * Method to set the layout references.
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

    public interface OnFragmentInteractionListener {
    }
}
