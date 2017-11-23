package com.striketec.fanapp.utils.constants;

/**
 * Created by Sukhbirs on 14-11-2017.
 */

public interface Constants {
    // Tag name for fragment to recognize from fragment manager
    String FRAGMENT_TAG_USER_DATABASE = "UserDatabaseFragmentTag";
    String FRAGMENT_TAG_EVENTS_HOLDER = "EventsHolderFragmentTag";
    String FRAGMENT_TAG_EVENTS = "EventsFragmentTag";
    String DIALOG_FRAGMENT_TAG_ADD_USER = "AddUserDialogFragment";

    // to capture image using camera
    int REQUEST_IMAGE_CAPTURE = 1;
    int MEDIA_TYPE_IMAGE = 2;
    String IMAGE_DIRECTORY_NAME = "StrikeTecFanApp";

    String TRUE = "true";
    String FALSE = "false";

    // Create Event Steps constant
    int STEP_1_EVENT_INFO = 0;
    int STEP_2_SELECT_ACTIVITY = 1;
    int STEP_3_ADD_PARTICIPANTS = 2;

    String DATE_FORMAT_MM_DD_YYYY = "MM-dd-yyyy";
    String TIME_FORMAT_HH_MM = "HH:mm";
}
