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

/**
 * This activity is used to create an event that contains ViewPager to display the steps Event Info, Activities and Participants to create event.
 */
public class CreateEventActivity extends AppCompatActivity
        implements CreateEventActivityInteractor, View.OnClickListener,
        CreateEventInfoFragment.OnFragmentInteractionListener,
        CreateEventActivitiesFragment.OnFragmentInteractionListener,
        CreateEventParticipantsFragment.OnFragmentInteractionListener{

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
        actionBar.setTitle(getString(R.string.title_create_event_screen));
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

    private void nextButtonClicked() {
        int currentItem = mViewPager.getCurrentItem();

        if (currentItem == 0){
            // Event Info, validate the event info step 1 page

        } else if (currentItem == 1){
            // Event Activities

        } else if (currentItem == 2){
            // Add Participants

        }
        DialogUtils.showToast(this, "current item: " + currentItem);
    }

    /**
     * Method to handle click event of Cancel button to go back to previous page or previous step.
     */
    private void cancelButtonClicked() {
        finish();
    }
}
