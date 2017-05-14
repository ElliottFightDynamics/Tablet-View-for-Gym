package com.efd.striketectablet.activity.training;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.efd.striketectablet.R;

public class TrainingFragment extends Fragment implements View.OnClickListener {

//    private CustomTextView boxerNameView;
//    private CustomTextView boxerMailView;
//    private CustomTextView boxerReachView;
//    private CustomTextView boxerGlovesView;
//    private CustomTextView boxerHeightView;
//    private CustomTextView boxerWeightView;
//    private CustomTextView boxerDetailView;
//    private HexagonImageView mUserPhotoView;
//
//    private MainActivity mainActivityInstance;
//
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        this.mainActivityInstance = (MainActivity) activity;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.training_fragment, container, false);
//        boxerNameView = (CustomTextView)view.findViewById(R.id.profile_name_view);
//        boxerMailView = (CustomTextView)view.findViewById(R.id.mail_view);
//        boxerReachView = (CustomTextView)view.findViewById(R.id.reach_view);
//        boxerGlovesView = (CustomTextView)view.findViewById(R.id.gloves_view);
//        boxerHeightView = (CustomTextView)view.findViewById(R.id.height_view);
//        boxerWeightView = (CustomTextView)view.findViewById(R.id.weight_view);
//        boxerDetailView = (CustomTextView)view.findViewById(R.id.details_text);
//        mUserPhotoView = (HexagonImageView) view.findViewById(R.id.user_photo);
//        mUserPhotoView.setImageResource(R.drawable.train1);
//        view.findViewById(R.id.edit_button).setOnClickListener(this);
//        init();
//        initConnectSensorButton(view);
        return view;
    }

    private void initConnectSensorButton(View view) {
//        view.findViewById(R.id.connect_button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openTestProfileFragment();
//            }
//
//        });
    }

    private void openTestProfileFragment() {
//        Intent searchSensorIntent = new Intent(getActivity(), SearchSensorActivity.class);
//        startActivity(searchSensorIntent);
//        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void init(){
//        JSONObject user_info_jsonObj = MainActivity.db.trainingUserInfo(mainActivityInstance.userId);
//
//        try {
//            if (user_info_jsonObj != null && user_info_jsonObj.getString("success").equals("true")) {
//                JSONObject user_info_json = user_info_jsonObj.getJSONObject("userInfo");
//                boxerNameView.setText(user_info_json.getString("first_name") + " " + user_info_json.getString("last_name"));
//                boxerMailView.setText(user_info_json.getString("user_email"));
//                boxerHeightView.setText(user_info_json.getString("user_height"));
//                boxerWeightView.setText(user_info_json.getString("user_weight"));
//                boxerReachView.setText(user_info_json.getString("user_reach"));
//                boxerGlovesView.setText(user_info_json.getString("user_glove_type"));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.edit_button:
//                editProfile();
//                break;
        }
    }

    private void editProfile() {
//        startActivity(new Intent(getActivity(), EditProfileActivity.class));
//        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
