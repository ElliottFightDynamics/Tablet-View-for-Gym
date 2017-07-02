package com.efd.striketectablet.activity.training.round;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.efd.striketectablet.DTO.PresetDTO;
import com.efd.striketectablet.R;
import com.efd.striketectablet.activity.MainActivity;
import com.efd.striketectablet.activity.training.quickstart.QuickStartTrainingActivity;
import com.efd.striketectablet.adapter.CustomSpinnerAdapter;
import com.efd.striketectablet.adapter.PresetListAdapter;
import com.efd.striketectablet.customview.CustomButton;
import com.efd.striketectablet.customview.CustomTextView;
import com.efd.striketectablet.util.PresetUtil;
import com.efd.striketectablet.util.StatisticUtil;
import com.efd.striketectablet.utilities.EFDConstants;
import com.efd.striketectablet.utilities.SharedPreferencesUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;

public class RoundTrainingPresetFragment extends Fragment {

    private WheelView roundsPicker, roundPicker, restPicker;
    ArrayWheelAdapter roundsAdapter, roundAdapter, restAdapter;

    Spinner   genderSpinner, gloveTypeSpinner, weightSpinner;
    SpinnerAdapter genderAdapter, gloveAdapter, weightAdpater;

    TextView presetNameView, totalTimeView, prepareTimeView, warningTimeView ;
    RelativeLayout presetParent;
    LinearLayout prepareParent, warningParent;

    CustomButton startTrainingBtn;

    View view;

    String userId;

    PresetDTO defaultPreset;
    ArrayList<PresetDTO> savedPreset;
    int currentPresetPosition = 0;
    int currentPreparePosition = 0;
    int currentWarningPosition = 0;

    private String type = "";

    MainActivity mainActivityInstance;

    private static Context mContext;
    public static RoundTrainingPresetFragment trainingPresetFragment;

    public static Fragment newInstance(Context context, String type) {
        mContext = context;
        trainingPresetFragment = new RoundTrainingPresetFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        trainingPresetFragment.setArguments(bundle);

        return trainingPresetFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mainActivityInstance = (MainActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_training_round_preset, container, false);
        type = getArguments().getString("type");

        initViews();

        return view;
    }

    private void initViews(){
        presetNameView = (CustomTextView)view.findViewById(R.id.preset_name);
        presetParent = (RelativeLayout)view.findViewById(R.id.preset_parent);
        presetParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSavedPresetDialog();
            }
        });

        //set rounds picker
        roundsPicker = (WheelView)view.findViewById(R.id.rounds_picker);
        roundsAdapter = new ArrayWheelAdapter(mainActivityInstance, PresetUtil.roundsList.toArray());
        roundsAdapter.setItemResource(R.layout.item_wheel_rounds_text);
        roundsAdapter.setItemTextResource(R.id.text);
        roundsAdapter.setActiveTextColor(getResources().getColor(R.color.rounds_select));
        roundsAdapter.setDeactiveTextColor(getResources().getColor(R.color.rounds_unselect));
        if (StatisticUtil.is600Dp()) {
            roundsAdapter.setActiveTextSzie(80);
            roundsAdapter.setDeactiveTextSize(50);
        }else {
            roundsAdapter.setActiveTextSzie(120);
            roundsAdapter.setDeactiveTextSize(80);
        }
        roundsPicker.setViewAdapter(roundsAdapter);
        roundsPicker.setVisibleItems(3);

        //set round time picker
        roundPicker = (WheelView)view.findViewById(R.id.round_picker);
        roundAdapter = new ArrayWheelAdapter(mainActivityInstance, PresetUtil.timeList.toArray());
        roundAdapter.setItemResource(R.layout.item_wheel_rounds_text);
        roundAdapter.setItemTextResource(R.id.text);
        roundAdapter.setActiveTextColor(getResources().getColor(R.color.round_select));
        roundAdapter.setDeactiveTextColor(getResources().getColor(R.color.round_unselect));
        if (!StatisticUtil.is600Dp()) {
            roundAdapter.setActiveTextSzie(55);
            roundAdapter.setDeactiveTextSize(50);
        }else {
            roundAdapter.setActiveTextSzie(40);
            roundAdapter.setDeactiveTextSize(35);
        }
        roundPicker.setViewAdapter(roundAdapter);
        roundPicker.setVisibleItems(3);

        //set rest picker
        restPicker = (WheelView)view.findViewById(R.id.rest_picker);
        restAdapter = new ArrayWheelAdapter(mainActivityInstance, PresetUtil.timeList.toArray());
        restAdapter.setItemResource(R.layout.item_wheel_rounds_text);
        restAdapter.setItemTextResource(R.id.text);
        restAdapter.setActiveTextColor(getResources().getColor(R.color.speed_text_color));
        restAdapter.setDeactiveTextColor(getResources().getColor(R.color.rest_unselect));
        if (!StatisticUtil.is600Dp()) {
            restAdapter.setActiveTextSzie(55);
            restAdapter.setDeactiveTextSize(50);
        }else {
            restAdapter.setActiveTextSzie(40);
            restAdapter.setDeactiveTextSize(35);
        }
        restPicker.setViewAdapter(restAdapter);
        restPicker.setVisibleItems(3);

        prepareTimeView = (TextView)view.findViewById(R.id.prepare_time);
        prepareParent = (LinearLayout)view.findViewById(R.id.prepare_parent);
        prepareParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWheelPicker(true);
            }
        });

        warningParent = (LinearLayout)view.findViewById(R.id.warning_parent);
        warningTimeView = (TextView)view.findViewById(R.id.warning_time);
        warningParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWheelPicker (false);
            }
        });

        //set weight spinner
        weightSpinner = (Spinner)view.findViewById(R.id.weight_spinner);
        weightAdpater = new CustomSpinnerAdapter(mainActivityInstance, R.layout.custom_spinner_digit_with_img, PresetUtil.weightList, EFDConstants.SPINNER_WHITE);
        weightSpinner.setAdapter(weightAdpater);

        //set glove spinner
        gloveTypeSpinner = (Spinner)view.findViewById(R.id.glove_spinner);
        gloveAdapter = new CustomSpinnerAdapter(mainActivityInstance, R.layout.custom_spinner_digit_with_img, PresetUtil.gloveList, EFDConstants.SPINNER_WHITE);
        gloveTypeSpinner.setAdapter(gloveAdapter);

        //set gender spinner
        genderSpinner = (Spinner)view.findViewById(R.id.gender_spinner);
        genderAdapter = new CustomSpinnerAdapter(mainActivityInstance, R.layout.custom_spinner_digit_with_img, PresetUtil.sexList, EFDConstants.SPINNER_WHITE);
        genderSpinner.setAdapter(genderAdapter);

        totalTimeView = (CustomTextView)view.findViewById(R.id.total_time);

        //set callback functions
        roundsPicker.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                setTotalTime();
            }
        });

        roundPicker.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                setTotalTime();
            }
        });

        restPicker.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                setTotalTime();
            }
        });

        startTrainingBtn = (CustomButton)view.findViewById(R.id.training_start_button);
        startTrainingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTraining();
            }
        });

        defaultPreset = new PresetDTO("Preset 1", PresetUtil.roundsList.size() / 2, PresetUtil.timeList.size() / 2, PresetUtil.timeList.size() / 2, PresetUtil.timeList.size() / 2,
                PresetUtil.warningTimewithSecList.size() / 2);

        savedPreset = SharedPreferencesUtils.getPresetList(mainActivityInstance);

        if (savedPreset == null )
            savedPreset = new ArrayList<>();

        if (savedPreset.size() > 0){
            showPresetInfo(savedPreset.get(0));
        }else {
            showPresetInfo(defaultPreset);
        }

//        userId = mainActivityInstance.userId;
//
//        JSONObject result_Training_UserInfo_display = MainActivity.db.trainingUserInfo(userId);
//        showUserDetails(result_Training_UserInfo_display.toString());
    }



    @Override
    public void onResume() {
        super.onResume();

        Log.e("Super", "on resume = " + type);
        savedPreset = SharedPreferencesUtils.getPresetList(mainActivityInstance);

        if (savedPreset == null )
            savedPreset = new ArrayList<>();

        userId = mainActivityInstance.userId;

        JSONObject result_Training_UserInfo_display = MainActivity.db.trainingUserInfo(userId);
        showUserDetails(result_Training_UserInfo_display.toString());
    }

    private void showUserDetails(String result) {
        JSONObject json;
        try {
            json = new JSONObject(result);
            if (json.getString("success").equals("true")) {

                JSONObject userInfoJsonObject = json.getJSONObject("userInfo");
                String  userWeight, userGloveType;

                userWeight = (userInfoJsonObject.get("user_weight").equals("0")) ? "" : userInfoJsonObject.getString("user_weight");
                userGloveType = (userInfoJsonObject.get("user_glove_type").equals("null")) ? "" : userInfoJsonObject.getString("user_glove_type");

                weightSpinner.setSelection(PresetUtil.getWeightPosition(userWeight));
                gloveTypeSpinner.setSelection(PresetUtil.getGlovePosition(userGloveType));

            } else {

            }

        } catch (JSONException e1) {
            e1.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void startTraining(){
        PresetDTO presetDTO = new PresetDTO();
        presetDTO.setRounds(roundsPicker.getCurrentItem());
        presetDTO.setRound_time(roundPicker.getCurrentItem());
        presetDTO.setRest(restPicker.getCurrentItem());
        presetDTO.setPrepare(currentPreparePosition);
        presetDTO.setWarning(currentWarningPosition);
        presetDTO.setName("preset");

        if (type.equalsIgnoreCase("round")){
            Intent trainingIntent = new Intent(mainActivityInstance, RoundTrainingActivity.class);
            trainingIntent.putExtra("preset", presetDTO);
            startActivity(trainingIntent);
            mainActivityInstance.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }else {
            Intent trainingIntent = new Intent(mainActivityInstance, QuickStartTrainingActivity.class);
            trainingIntent.putExtra("preset", presetDTO);
            startActivity(trainingIntent);
            mainActivityInstance.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
    }

    private void showPresetInfo (PresetDTO presetDTO){
        roundsPicker.setCurrentItem(presetDTO.getRounds());
        roundPicker.setCurrentItem(presetDTO.getRound_time());
        restPicker.setCurrentItem(presetDTO.getRest());
        currentPreparePosition = presetDTO.getPrepare();
        currentWarningPosition = presetDTO.getWarning();
        prepareTimeView.setText(PresetUtil.timeList.get(currentPreparePosition));
        warningTimeView.setText(PresetUtil.warningTimewithSecList.get(currentWarningPosition));

        presetNameView.setText(presetDTO.getName());

        setTotalTime();
    }

    private void showWheelPicker(final boolean isprepare){
        final Dialog dialog = new Dialog(mainActivityInstance);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_prepare_warning_picker);
        dialog.setCancelable(false);

        //set prepare picker
        WheelView preparewarningPicker = (WheelView)dialog.findViewById(R.id.preparewarning_picker);
        ArrayList dataList;
        if (isprepare)
            dataList = PresetUtil.timeList;
        else
            dataList = PresetUtil.warningTimewithSecList;

        ArrayWheelAdapter preparewarningAdapter = new ArrayWheelAdapter(mainActivityInstance, dataList.toArray());

        preparewarningAdapter.setItemResource(R.layout.item_wheel_rounds_text);
        preparewarningAdapter.setItemTextResource(R.id.text);
        preparewarningAdapter.setActiveTextColor(getResources().getColor(R.color.prepare_select));
        preparewarningAdapter.setDeactiveTextColor(getResources().getColor(R.color.prepare_unselect));
        preparewarningAdapter.setActiveTextSzie(45);
        preparewarningAdapter.setDeactiveTextSize(40);
        preparewarningPicker.setViewAdapter(preparewarningAdapter);
        preparewarningPicker.setVisibleItems(3);

        if (isprepare)
            preparewarningPicker.setCurrentItem(currentPreparePosition);
        else
            preparewarningPicker.setCurrentItem(currentWarningPosition);

        preparewarningPicker.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                if (isprepare){
                    currentPreparePosition = newValue;
                    prepareTimeView.setText(PresetUtil.timeList.get(newValue));
                    setTotalTime();
                }else {
                    currentWarningPosition = newValue;
                    warningTimeView.setText(PresetUtil.warningTimewithSecList.get(newValue));
                }
            }
        });

        CustomTextView setBtn = (CustomTextView)dialog.findViewById(R.id.okbtn);
        setBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }

    private void setTotalTime(){
        int round = Integer.parseInt(PresetUtil.roundsList.get(roundsPicker.getCurrentItem()));
        int roundtime = Integer.parseInt(PresetUtil.timerwitSecsList.get(roundPicker.getCurrentItem()));
        int resttime = Integer.parseInt(PresetUtil.timerwitSecsList.get(restPicker.getCurrentItem()));
        int preparetime = Integer.parseInt(PresetUtil.timerwitSecsList.get(currentPreparePosition));

        String totalTime = PresetUtil.changeSecondsToHours(round * (roundtime + resttime) + preparetime);
        totalTimeView.setText(totalTime);
    }

    private void showSavedPresetDialog(){
        final Dialog dialog = new Dialog(mainActivityInstance);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.BOTTOM;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_preset_popup);

        ListView presetListView = (ListView)dialog.findViewById(R.id.preset_listview);
        final PresetListAdapter adapter = new PresetListAdapter(mainActivityInstance, savedPreset, currentPresetPosition);
        presetListView.setAdapter(adapter);

        presetListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    saveNewPreset();
                    dialog.dismiss();
                }else {
                    PresetDTO presetDTO = adapter.getItem(position - 1);
                    currentPresetPosition = position - 1;
                    showPresetInfo(presetDTO);
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }

    private void saveNewPreset(){
        PresetDTO presetDTO = new PresetDTO();
        presetDTO.setRounds(roundsPicker.getCurrentItem());
        presetDTO.setRound_time(roundPicker.getCurrentItem());
        presetDTO.setRest(restPicker.getCurrentItem());
        presetDTO.setPrepare(currentPreparePosition);
        presetDTO.setWarning(currentWarningPosition);

        presetDTO.setName("Preset " + String.valueOf(savedPreset.size() + 1));

        savedPreset.add(presetDTO);
        SharedPreferencesUtils.savePresetLists(mainActivityInstance, savedPreset);
        presetNameView.setText(presetDTO.getName());
    }

    @Override
    public void onDetach() {
        super.onDetach();

        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
