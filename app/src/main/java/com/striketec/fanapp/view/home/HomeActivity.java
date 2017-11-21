package com.striketec.fanapp.view.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.striketec.fanapp.R;
import com.striketec.fanapp.presenter.home.HomePresenter;
import com.striketec.fanapp.presenter.home.HomePresenterImpl;
import com.striketec.fanapp.utils.DialogUtils;
import com.striketec.fanapp.utils.constants.Constants;
import com.striketec.fanapp.view.events.fragment.EventsFragment;
import com.striketec.fanapp.view.events.fragment.EventsHolderFragment;
import com.striketec.fanapp.view.users.fragment.UserDatabaseFragment;

/**
 * This is Home Screen activity class that contains the menu navigation drawer.
 */
public class HomeActivity extends AppCompatActivity
        implements HomeActivityInteractor, NavigationView.OnNavigationItemSelectedListener,
        EventsHolderFragment.OnFragmentInteractionListener,
        EventsFragment.OnFragmentInteractionListener,
        UserDatabaseFragment.OnFragmentInteractionListener {

    private HomePresenter mHomePresenter;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        findViewByIds();
        mHomePresenter = new HomePresenterImpl(this);

        mHomePresenter.replaceFragment(Constants.FRAGMENT_TAG_EVENTS_HOLDER);
    }

    /**
     * Method to set the layout references.
     */
    private void findViewByIds() {
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        FloatingActionButton mFab = findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabButtonClicked();
            }
        });

        DrawerLayout mDrawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle mActionBarDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.syncState();

        NavigationView mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        mTabLayout = findViewById(R.id.tabs);
    }

    /**
     * FloatingActionButton clicked.
     */
    private void fabButtonClicked() {
        FragmentManager mFragmentManager = getSupportFragmentManager();
        Fragment mFragment = mFragmentManager.findFragmentByTag(Constants.FRAGMENT_TAG_USER_DATABASE);
        if (mFragment != null) {
//            ((UserDatabaseFragment)mFragment).showAddUserDialog();
            ((UserDatabaseFragment) mFragment).showAddUserDialogFragment();
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_timer) {

        } else if (id == R.id.nav_events) {
            mHomePresenter.replaceFragment(Constants.FRAGMENT_TAG_EVENTS_HOLDER);
        } else if (id == R.id.nav_user_database) {
            mHomePresenter.replaceFragment(Constants.FRAGMENT_TAG_USER_DATABASE);
        } else if (id == R.id.nav_settings) {

        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK) {
                FragmentManager mFragmentManager = getSupportFragmentManager();
                Fragment mFragment = mFragmentManager.findFragmentByTag(Constants.FRAGMENT_TAG_USER_DATABASE);
                if (mFragment != null) {
                    ((UserDatabaseFragment) mFragment).previewCapturedImage();
                }
            } else if (resultCode == RESULT_CANCELED) {
                DialogUtils.showToast(this, getString(R.string.error_user_cancelled_image_capture));
            } else {
                DialogUtils.showToast(this, getString(R.string.error_failed_to_capture_image));
            }
        }
    }

    @Override
    public void setTabLayoutWithViewPager(ViewPager mViewPager) {
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void setTabLayoutVisibility(int visibility) {
        mTabLayout.setVisibility(visibility);
    }

    @Override
    protected void onDestroy() {
        mHomePresenter.onDestroy();
        super.onDestroy();
    }
}
