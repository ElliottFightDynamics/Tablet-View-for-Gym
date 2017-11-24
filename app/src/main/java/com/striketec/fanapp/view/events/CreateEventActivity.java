package com.striketec.fanapp.view.events;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.striketec.fanapp.R;
import com.striketec.fanapp.model.events.dto.CreateEventInfo;
import com.striketec.fanapp.model.events.dto.EventGeneralInfo;
import com.striketec.fanapp.presenter.events.CreateEventActivityPresenter;
import com.striketec.fanapp.presenter.events.CreateEventActivityPresenterImpl;
import com.striketec.fanapp.utils.DialogUtils;
import com.striketec.fanapp.view.events.customview.CustomViewPager;
import com.striketec.fanapp.view.events.fragment.CreateEventActivitiesFragment;
import com.striketec.fanapp.view.events.fragment.CreateEventInfoFragment;
import com.striketec.fanapp.view.events.fragment.CreateEventParticipantsFragment;

import static com.striketec.fanapp.utils.constants.Constants.STEP_1_EVENT_INFO;
import static com.striketec.fanapp.utils.constants.Constants.STEP_2_SELECT_ACTIVITY;
import static com.striketec.fanapp.utils.constants.Constants.STEP_3_ADD_PARTICIPANTS;

/**
 * This activity is used to create an event that contains ViewPager to display the steps Event Info, Activities and Participants to create event.
 */
public class CreateEventActivity extends AppCompatActivity
        implements CreateEventActivityInteractor, View.OnClickListener,
        CreateEventInfoFragment.OnFragmentInteractionListener,
        CreateEventActivitiesFragment.OnFragmentInteractionListener,
        CreateEventParticipantsFragment.OnFragmentInteractionListener {

    private CreateEventActivityPresenter mCreateEventActivityPresenter;

    private CustomViewPager mViewPager;
    private Button mCancelButton, mNextButton;
    private TextView mIWillDoThisLaterTV;
    private EventGeneralInfo mEventGeneralInfo;
    private String mSelectedEventActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        mCreateEventActivityPresenter = new CreateEventActivityPresenterImpl(this);
        findViewByIds();
    }

    /**
     * Method to set the layout references.
     */
    private void findViewByIds() {
        setToolbar();

        mViewPager = findViewById(R.id.view_pager);
        mViewPager.setPagingEnabled(false);

        mCancelButton = findViewById(R.id.button_cancel);
        mNextButton = findViewById(R.id.button_next);
        mIWillDoThisLaterTV = findViewById(R.id.tv_i_will_do_this_later);

        mCancelButton.setOnClickListener(this);
        mNextButton.setOnClickListener(this);
        mIWillDoThisLaterTV.setOnClickListener(this);

        mCreateEventActivityPresenter.setupViewPagerAdapter(mViewPager);
        mViewPager.setCurrentItem(0);
    }

    /**
     * Method to set the ActionBar and its title.
     */
    private void setToolbar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getString(R.string.title_new_event_screen));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_cancel:
                cancelButtonClicked();
                break;
            case R.id.button_next:
                nextButtonClicked();
                break;
            case R.id.tv_i_will_do_this_later:
                iWillDoThisLaterClicked();
                break;
            default:
                break;
        }
    }

    /**
     * Method to handle click event of "I will do this later" button text.
     */
    private void iWillDoThisLaterClicked() {
        DialogUtils.showToast(this, "I will do this later - temp test message.");
    }

    /**
     * Method to handle click event of Next button.
     */
    private void nextButtonClicked() {
        int currentItem = mViewPager.getCurrentItem();

        mCreateEventActivityPresenter.handleNextClick(currentItem);
//        DialogUtils.showToast(this, "current item: " + currentItem);
    }

    /**
     * Method to handle click event of Cancel button to go back to previous page or previous step.
     */
    private void cancelButtonClicked() {
        int currentItem = mViewPager.getCurrentItem();
        if (currentItem == STEP_1_EVENT_INFO) {
            finish();
        } else if (currentItem == STEP_2_SELECT_ACTIVITY) {
            mCreateEventActivityPresenter.handleCancelClick(currentItem);
        } else if (currentItem == STEP_3_ADD_PARTICIPANTS) {
            mCreateEventActivityPresenter.handleCancelClick(currentItem);
        }
    }

    @Override
    public void navigateToCreateEventStep1() {
        // 0 - Step 1 Create Event General Info
        // 1 - Step 2 Create Event Select Activity
        // 2 - Step 3 Create Event Add Participants
        mViewPager.setCurrentItem(STEP_1_EVENT_INFO);
        mCancelButton.setText(getString(R.string.button_cancel));
        mIWillDoThisLaterTV.setVisibility(View.GONE);
    }

    @Override
    public void navigateToCreateEventStep2() {
        // 0 - Step 1 Create Event General Info
        // 1 - Step 2 Create Event Select Activity
        // 2 - Step 3 Create Event Add Participants
        mViewPager.setCurrentItem(STEP_2_SELECT_ACTIVITY);
        mCancelButton.setText(getString(R.string.button_back));
        mNextButton.setText(getString(R.string.button_next));
        mIWillDoThisLaterTV.setVisibility(View.GONE);
    }

    @Override
    public void setEventGeneralInfo(EventGeneralInfo eventGeneralInfo) {
        this.mEventGeneralInfo = eventGeneralInfo;
    }

    @Override
    public void navigateToCreateEventStep3() {
        mViewPager.setCurrentItem(STEP_3_ADD_PARTICIPANTS);
        mCancelButton.setText(getString(R.string.button_back));
        mNextButton.setText(getString(R.string.button_done));
        mIWillDoThisLaterTV.setVisibility(View.VISIBLE);
    }

    @Override
    public void setSelectedEventActivity(String selectedEventActivity) {
        this.mSelectedEventActivity = selectedEventActivity;
    }

    @Override
    public void getCompleteEventDetails(CreateEventInfo createEventInfo) {
        if (createEventInfo != null) {
            // Step 1 Details
            if (mEventGeneralInfo != null) {
                createEventInfo.setEventTitle(mEventGeneralInfo.getEventTitle());
                createEventInfo.setLocationId(mEventGeneralInfo.getEventLocationInfo().getId());
                createEventInfo.setEventDescription(mEventGeneralInfo.getEventDescription());
                createEventInfo.setFromDate(mEventGeneralInfo.getEventStartDate());
                createEventInfo.setFromTime(mEventGeneralInfo.getEventStartTime());
                createEventInfo.setToDate(mEventGeneralInfo.getEventEndDate());
                createEventInfo.setToTime(mEventGeneralInfo.getEventEndTime());
                createEventInfo.setAllDay(mEventGeneralInfo.isAllDay());
            }
            // Step 2 Details
            createEventInfo.setEventActivityType(mSelectedEventActivity);
        }
    }
}
