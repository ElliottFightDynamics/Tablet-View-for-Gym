package com.striketec.fanapp.view.home;

/**
 * Created by Sukhbirs on 17-11-2017.
 */

public interface HomeActivityInteractor {
    /**
     * Method to set the visibility of TabLayout as it is not required on all pages.
     *
     * @param visibility
     */
    void setTabLayoutVisibility(int visibility);
}
