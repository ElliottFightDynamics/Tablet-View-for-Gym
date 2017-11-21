package com.striketec.fanapp.presenter.home;

/**
 * Created by Sukhbirs on 17-11-2017.
 */

public interface HomePresenter {
    /**
     * Method to replace the fragment on basis of given fragmentTag.
     *
     * @param fragmentTag
     */
    void replaceFragment(String fragmentTag);

    void onDestroy();
}
