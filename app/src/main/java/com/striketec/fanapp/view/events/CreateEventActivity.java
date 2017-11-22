package com.striketec.fanapp.view.events;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.striketec.fanapp.R;
import com.striketec.fanapp.presenter.events.CreateEventActivityPresenter;
import com.striketec.fanapp.presenter.events.CreateEventActivityPresenterImpl;
import com.striketec.fanapp.utils.DialogUtils;
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

    private ViewPager mViewPager;
    private Button mCancelButton, mNextButton;

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
        mCancelButton = findViewById(R.id.button_cancel);
        mNextButton = findViewById(R.id.button_next);
        mCancelButton.setOnClickListener(this);
        mNextButton.setOnClickListener(this);

        mCreateEventActivityPresenter.setupViewPagerAdapter(mViewPager);
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
            default:
                break;
        }
    }

    /**
     * Method to handle click event of Next button.
     */
    private void nextButtonClicked() {
        int currentItem = mViewPager.getCurrentItem();

        if (currentItem == STEP_1_EVENT_INFO) {
            // Event Info, validate the event info step 1 page
            /*CreateEventInfoFragment mCreateEventInfoFragment = (CreateEventInfoFragment) mCreateEventActivityPresenter.getCurrentFragment(0);
            mCreateEventInfoFragment.handleOnNextClick();*/
            mCreateEventActivityPresenter.handleNextClick(currentItem);
        } else if (currentItem == STEP_2_SELECT_ACTIVITY) {
            // Event Activities

        } else if (currentItem == STEP_3_ADD_PARTICIPANTS) {
            // Add Participants

        }
        DialogUtils.showToast(this, "current item: " + currentItem);
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

        }
        String cancelButtonText = mCancelButton.getText().toString().trim();
    }

    @Override
    public void navigateToCreateEventStep1() {
        // 0 - Step 1 Create Event General Info
        // 1 - Step 2 Create Event Select Activity
        // 2 - Step 3 Create Event Add Participants
        mViewPager.setCurrentItem(STEP_1_EVENT_INFO);
        mCancelButton.setText(getString(R.string.button_cancel));
    }

    @Override
    public void navigateToCreateEventStep2() {
        // 0 - Step 1 Create Event General Info
        // 1 - Step 2 Create Event Select Activity
        // 2 - Step 3 Create Event Add Participants
        mViewPager.setCurrentItem(STEP_2_SELECT_ACTIVITY);
        mCancelButton.setText(getString(R.string.button_back));
    }

    @Override
    public void navigateToCreateEventStep3() {
        mViewPager.setCurrentItem(STEP_3_ADD_PARTICIPANTS);
        mCancelButton.setText(getString(R.string.button_back));
        mNextButton.setText(getString(R.string.button_done));
    }

}
