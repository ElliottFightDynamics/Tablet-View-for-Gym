package com.striketec.fanapp.presenter.home;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.striketec.fanapp.R;
import com.striketec.fanapp.utils.constants.Constants;
import com.striketec.fanapp.view.events.fragment.EventsHolderFragment;
import com.striketec.fanapp.view.home.HomeActivity;
import com.striketec.fanapp.view.home.HomeActivityInteractor;
import com.striketec.fanapp.view.users.fragment.UserDatabaseFragment;

/**
 * Created by Sukhbirs on 17-11-2017.
 * This is presenter for Home section/page.
 */

public class HomePresenterImpl implements HomePresenter {

    private HomeActivityInteractor mHomeActivityInteractor;
    private HomeActivity mHomeActivity;

    public HomePresenterImpl(HomeActivityInteractor mHomeActivityInteractor) {
        this.mHomeActivityInteractor = mHomeActivityInteractor;
        mHomeActivity = (HomeActivity) mHomeActivityInteractor;
    }

    @Override
    public void replaceFragment(String fragmentTag) {
        mHomeActivityInteractor.setTabLayoutVisibility(View.GONE);
        switch (fragmentTag) {
            // EventsHolderFragment
            case Constants.FRAGMENT_TAG_EVENTS_HOLDER:
                showEventsHolderFragment();
                break;

            // UserDatabaseFragment
            case Constants.FRAGMENT_TAG_USER_DATABASE:
                showUserDatabaseFragment();
                break;

            default:
                break;
        }
    }

    /**
     * Method to show Events Holder Fragment.
     */
    private void showEventsHolderFragment() {
        mHomeActivityInteractor.setTabLayoutVisibility(View.VISIBLE);
        FragmentManager mFragmentManager = mHomeActivity.getSupportFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        EventsHolderFragment mEventsHolderFragment = new EventsHolderFragment();
        mFragmentTransaction.replace(R.id.fragment_container, mEventsHolderFragment, Constants.FRAGMENT_TAG_EVENTS_HOLDER);
        mHomeActivity.setTitle(mHomeActivity.getString(R.string.title_events_screen));
        mFragmentTransaction.commit();
    }

    /**
     * Method to show user database screen/fragment.
     */
    private void showUserDatabaseFragment() {
        FragmentManager fragmentManager = mHomeActivity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        UserDatabaseFragment userDatabaseFragment = new UserDatabaseFragment();
        fragmentTransaction.replace(R.id.fragment_container, userDatabaseFragment, Constants.FRAGMENT_TAG_USER_DATABASE);
        mHomeActivity.setTitle(mHomeActivity.getString(R.string.title_users_database_screen));
        fragmentTransaction.commit();
    }

    @Override
    public void onDestroy() {
        mHomeActivityInteractor = null;
    }
}
