package com.striketec.fanapp.view.events.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.striketec.fanapp.R;
import com.striketec.fanapp.model.events.EventGeneralInfo;
import com.striketec.fanapp.presenter.events.fragment.CreateEventInfoFragmentPresenterImpl;
import com.striketec.fanapp.utils.DialogUtils;

/**
 * This fragment is used to select the activity for the event to be created.
 */
public class CreateEventInfoFragment extends Fragment implements CreateEventInfoFragmentInteractor, View.OnClickListener {

    private OnFragmentInteractionListener mListener;
    private CreateEventInfoFragmentPresenterImpl mCreateEventInfoFragmentPresenter;
    private EditText mEventTitleEdit, mLocationSpinnerEdit, mEventDescriptionEdit, mEventStartDateEdit, mEventStartTimeEdit, mEventEndDate, mEventEndTime;

    public CreateEventInfoFragment() {
        // Required empty public constructor
        mCreateEventInfoFragmentPresenter = new CreateEventInfoFragmentPresenterImpl(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_event_step_1, container, false);
        findViewByIds(view);
        return view;
    }

    /**
     * Method to set the layout references.
     *
     * @param view
     */
    private void findViewByIds(View view) {
        // Step 1, header layout
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

        // Event Title EditText
        mEventTitleEdit = view.findViewById(R.id.edit_event_title);
        // Location
        mLocationSpinnerEdit = view.findViewById(R.id.edit_spinner_location);
        mLocationSpinnerEdit.setOnClickListener(this);
        // Event Description
        mEventDescriptionEdit = view.findViewById(R.id.edit_description);
        // Event Start Date
        mEventStartDateEdit = view.findViewById(R.id.edit_spinner_from_date);
        mEventStartDateEdit.setOnClickListener(this);
        // Event Start Time
        mEventStartTimeEdit = view.findViewById(R.id.edit_spinner_from_time);
        mEventStartTimeEdit.setOnClickListener(this);
        // Event End Date
        mEventEndDate = view.findViewById(R.id.edit_spinner_to_date);
        mEventEndDate.setOnClickListener(this);
        // Event End Time
        mEventEndTime = view.findViewById(R.id.edit_spinner_to_time);
        mEventEndTime.setOnClickListener(this);
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
    public void setEventTitleError() {
        DialogUtils.showToast(getActivity(), getString(R.string.error_event_title_is_required));
    }

    @Override
    public void setEventLocationError() {
        DialogUtils.showToast(getActivity(), getString(R.string.error_event_location_is_required));
    }

    @Override
    public void setEventDescriptionError() {
        DialogUtils.showToast(getActivity(), getString(R.string.error_event_description_is_required));
    }

    @Override
    public void setEventStartDateError() {
        DialogUtils.showToast(getActivity(), getString(R.string.error_event_start_date_is_required));
    }

    @Override
    public void setEventStartTimeError() {
        DialogUtils.showToast(getActivity(), getString(R.string.error_event_start_time_is_required));
    }

    @Override
    public void setEventEndDateError() {
        DialogUtils.showToast(getActivity(), getString(R.string.error_event_end_date_is_required));
    }

    @Override
    public void setEventEndTimeError() {
        DialogUtils.showToast(getActivity(), getString(R.string.error_event_end_time_is_required));
    }

    @Override
    public void setEventStartDateAfterEndDateError() {
        DialogUtils.showToast(getActivity(), getString(R.string.error_event_start_date_after_end_date));
    }

    @Override
    public void navigateToCreateEventStep2() {
        mListener.navigateToCreateEventStep2();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_spinner_location:
                break;
            case R.id.edit_spinner_from_date:
                mCreateEventInfoFragmentPresenter.showDatePicker(mEventStartDateEdit);
                break;
            case R.id.edit_spinner_from_time:
                mCreateEventInfoFragmentPresenter.showTimePicker(mEventStartTimeEdit);
                break;
            case R.id.edit_spinner_to_date:
                mCreateEventInfoFragmentPresenter.showDatePicker(mEventEndDate);
                break;
            case R.id.edit_spinner_to_time:
                mCreateEventInfoFragmentPresenter.showTimePicker(mEventEndTime);
                break;
            default:
                break;
        }
    }

    public interface OnFragmentInteractionListener {
        void navigateToCreateEventStep2();
    }

    /**
     * Method to handle click event of Next button if it is on Step 1 of create event page.
     */
    public void handleOnNextClick() {
        String eventTitle = mEventTitleEdit.getText().toString().trim();
        String eventLocation = mLocationSpinnerEdit.getText().toString().trim();
        String eventDescription = mEventDescriptionEdit.getText().toString().trim();
        String eventStartDate = mEventStartDateEdit.getText().toString().trim();
        String eventStartTime = mEventStartTimeEdit.getText().toString().trim();
        String eventEndDate = mEventEndDate.getText().toString().trim();
        String eventEndTime = mEventEndTime.getText().toString().trim();
        EventGeneralInfo eventGeneralInfo = new EventGeneralInfo();
        eventGeneralInfo.setEventTitle(eventTitle);
        eventGeneralInfo.setEventLocation(eventLocation);
        eventGeneralInfo.setEventDescription(eventDescription);
        eventGeneralInfo.setEventStartDate(eventStartDate);
        eventGeneralInfo.setEventStartTime(eventStartTime);
        eventGeneralInfo.setEventEndDate(eventEndDate);
        eventGeneralInfo.setEventEndTime(eventEndTime);
        mCreateEventInfoFragmentPresenter.validateEventGeneralInfoOnNext(eventGeneralInfo);
    }
}
