package com.efd.striketectablet.activity.training;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.efd.striketectablet.DTO.PresetDTO;
import com.efd.striketectablet.R;
import com.efd.striketectablet.adapter.CustomSpinnerAdapter;
import com.efd.striketectablet.adapter.PresetListAdapter;
import com.efd.striketectablet.customview.CustomButton;
import com.efd.striketectablet.customview.CustomTextView;
import com.efd.striketectablet.util.PresetUtil;
import com.efd.striketectablet.utilities.EFDConstants;
import com.efd.striketectablet.utilities.SharedPreferencesUtils;

import java.util.ArrayList;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;

public class TrainingPresetActivity extends FragmentActivity {

    private WheelView roundsPicker, roundPicker, restPicker;//, preparePicker, warningPicker;
    ArrayWheelAdapter roundsAdapter, roundAdapter, restAdapter;//, prepareAdapter, warningAdapter;
//    Spinner prepareSpinner, warningSpinner, genderSpinner, gloveTypeSpinner, weightSpinner;
//    SpinnerAdapter prepareAdapter, warningAdapter, genderAdapter, gloveAdapter, weightAdpater;

    Spinner   genderSpinner, gloveTypeSpinner, weightSpinner, heightSpinner;
    SpinnerAdapter genderAdapter, gloveAdapter, weightAdpater, heightAdapter;

    TextView presetNameView, totalTimeView, prepareTimeView, warningTimeView, titleView;
    RelativeLayout presetParent;
    LinearLayout prepareParent, warningParent;

    CustomButton startTrainingBtn;

    ImageView backBtn;

    PresetDTO defaultPreset;
    ArrayList<PresetDTO> savedPreset;
    int currentPresetPosition = 0;
    int currentPreparePosition = 0;
    int currentWarningPosition = 0;

    private String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_training_preset);

        type = getIntent().getStringExtra("type");
        initViews();
    }

    private void initViews(){

        titleView = (TextView)findViewById(R.id.preset_title);

        if (type.equalsIgnoreCase("round")){
            titleView.setText(getResources().getString(R.string.title_activity_round_training_set));
        }else {
            titleView.setText(getResources().getString(R.string.title_activity_quick_start_set));
        }

        presetNameView = (CustomTextView)findViewById(R.id.preset_name);
        presetParent = (RelativeLayout)findViewById(R.id.preset_parent);
        presetParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSavedPresetDialog();
            }
        });

        backBtn = (ImageView)findViewById(R.id.btn_back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //set rounds picker
        roundsPicker = (WheelView)findViewById(R.id.rounds_picker);
        roundsAdapter = new ArrayWheelAdapter(this, PresetUtil.roundsList.toArray());
        roundsAdapter.setItemResource(R.layout.item_wheel_rounds_text);
        roundsAdapter.setItemTextResource(R.id.text);
        roundsAdapter.setActiveTextColor(getResources().getColor(R.color.rounds_select));
        roundsAdapter.setDeactiveTextColor(getResources().getColor(R.color.rounds_unselect));
        roundsAdapter.setActiveTextSzie(120);
        roundsAdapter.setDeactiveTextSize(80);
        roundsPicker.setViewAdapter(roundsAdapter);
        roundsPicker.setVisibleItems(3);

        //set round time picker
        roundPicker = (WheelView)findViewById(R.id.round_picker);
        roundAdapter = new ArrayWheelAdapter(this, PresetUtil.timeList.toArray());
        roundAdapter.setItemResource(R.layout.item_wheel_rounds_text);
        roundAdapter.setItemTextResource(R.id.text);
        roundAdapter.setActiveTextColor(getResources().getColor(R.color.round_select));
        roundAdapter.setDeactiveTextColor(getResources().getColor(R.color.round_unselect));
        roundAdapter.setActiveTextSzie(55);
        roundAdapter.setDeactiveTextSize(50);
        roundPicker.setViewAdapter(roundAdapter);
        roundPicker.setVisibleItems(3);

        //set rest picker
        restPicker = (WheelView)findViewById(R.id.rest_picker);
        restAdapter = new ArrayWheelAdapter(this, PresetUtil.timeList.toArray());
        restAdapter.setItemResource(R.layout.item_wheel_rounds_text);
        restAdapter.setItemTextResource(R.id.text);
        restAdapter.setActiveTextColor(getResources().getColor(R.color.speed_text_color));
        restAdapter.setDeactiveTextColor(getResources().getColor(R.color.rest_unselect));
        restAdapter.setActiveTextSzie(55);
        restAdapter.setDeactiveTextSize(50);
        restPicker.setViewAdapter(restAdapter);
        restPicker.setVisibleItems(3);

        prepareTimeView = (TextView)findViewById(R.id.prepare_time);
        prepareParent = (LinearLayout)findViewById(R.id.prepare_parent);
        prepareParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWheelPicker(true);
            }
        });

        warningParent = (LinearLayout)findViewById(R.id.warning_parent);
        warningTimeView = (TextView)findViewById(R.id.warning_time);
        warningParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWheelPicker (false);
            }
        });


//
//        //set prepare picker
//        preparePicker = (WheelView)findViewById(R.id.prepare_picker);
//        prepareAdapter = new ArrayWheelAdapter(this, PresetUtil.timeList.toArray());
//        prepareAdapter.setItemResource(R.layout.wheel_rest_text_item);
//        prepareAdapter.setItemTextResource(R.id.text);
//        prepareAdapter.setActiveTextColor(Color.parseColor(getResources().getString(R.string.prepare_select)));
//        prepareAdapter.setDeactiveTextColor(Color.parseColor(getResources().getString(R.string.prepare_unselect)));
//        prepareAdapter.setActiveTextSzie(45);
//        prepareAdapter.setDeactiveTextSize(40);
//        preparePicker.setViewAdapter(prepareAdapter);
//        preparePicker.setVisibleItems(3);
//
//        //set warning picker
//        warningPicker = (WheelView)findViewById(R.id.warning_picker);
//        warningAdapter = new ArrayWheelAdapter(this, PresetUtil.timeList.toArray());
//        warningAdapter.setItemResource(R.layout.wheel_rest_text_item);
//        warningAdapter.setItemTextResource(R.id.text);
//        warningAdapter.setActiveTextColor(Color.parseColor(getResources().getString(R.string.prepare_select)));
//        warningAdapter.setDeactiveTextColor(Color.parseColor(getResources().getString(R.string.prepare_unselect)));
//        warningAdapter.setActiveTextSzie(45);
//        warningAdapter.setDeactiveTextSize(40);
//        warningPicker.setViewAdapter(warningAdapter);
//        warningPicker.setVisibleItems(3);

        //set weight spinner
        weightSpinner = (Spinner)findViewById(R.id.weight_spinner);
        weightAdpater = new CustomSpinnerAdapter(this, R.layout.custom_spinner_with_img, PresetUtil.weightList, EFDConstants.SPINNER_WHITE);
        weightSpinner.setAdapter(weightAdpater);

        //set glove spinner
        gloveTypeSpinner = (Spinner)findViewById(R.id.glove_spinner);
        gloveAdapter = new CustomSpinnerAdapter(this, R.layout.custom_spinner_with_img, PresetUtil.gloveList, EFDConstants.SPINNER_WHITE);
        gloveTypeSpinner.setAdapter(gloveAdapter);

        //set gender spinner
        genderSpinner = (Spinner)findViewById(R.id.gender_spinner);
        genderAdapter = new CustomSpinnerAdapter(this, R.layout.custom_spinner_with_img, PresetUtil.sexList, EFDConstants.SPINNER_WHITE);
        genderSpinner.setAdapter(genderAdapter);

        totalTimeView = (CustomTextView)findViewById(R.id.total_time);

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

//        preparePicker.addChangingListener(new OnWheelChangedListener() {
//            @Override
//            public void onChanged(WheelView wheel, int oldValue, int newValue) {
//                setTotalTime();
//            }
//        });

        startTrainingBtn = (CustomButton)findViewById(R.id.training_start_button);
        startTrainingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTraining();
            }
        });

        savedPreset = SharedPreferencesUtils.getPresetList(this);
        defaultPreset = new PresetDTO("Preset 1", PresetUtil.roundsList.size() / 2, PresetUtil.timeList.size() / 2, PresetUtil.timeList.size() / 2, PresetUtil.timeList.size() / 2,
                PresetUtil.warningTimewithSecList.size() / 2, PresetUtil.weightList.size() / 2, 0, 0);

        if (savedPreset == null )
            savedPreset = new ArrayList<>();

        if (savedPreset.size() > 0){
            showPresetInfo(savedPreset.get(0));
        }else {
            showPresetInfo(defaultPreset);
        }
    }

    private void startTraining(){
        PresetDTO presetDTO = new PresetDTO();
        presetDTO.setRounds(roundsPicker.getCurrentItem());
        presetDTO.setRound_time(roundPicker.getCurrentItem());
        presetDTO.setRest(restPicker.getCurrentItem());
        presetDTO.setPrepare(currentPreparePosition);
        presetDTO.setWarning(currentWarningPosition);
        presetDTO.setWeight(weightSpinner.getSelectedItemPosition());
        presetDTO.setGlove(gloveTypeSpinner.getSelectedItemPosition());
        presetDTO.setGender(genderSpinner.getSelectedItemPosition());
        presetDTO.setName("preset");

        if (type.equalsIgnoreCase("round")){
            Intent trainingIntent = new Intent(this, RoundTrainingActivity.class);
            trainingIntent.putExtra("preset", presetDTO);
            startActivity(trainingIntent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }else {
            Intent trainingIntent = new Intent(this, RoundTrainingActivity.class);
            trainingIntent.putExtra("preset", presetDTO);
            startActivity(trainingIntent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
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
        weightSpinner.setSelection(presetDTO.getWeight());
        gloveTypeSpinner.setSelection(presetDTO.getGlove());
        genderSpinner.setSelection(presetDTO.getGender());

        presetNameView.setText(presetDTO.getName());

        setTotalTime();
    }

    private void showWheelPicker(final boolean isprepare){
        final Dialog dialog = new Dialog(this);
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

        ArrayWheelAdapter preparewarningAdapter = new ArrayWheelAdapter(this, dataList.toArray());

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

        String totalTime = PresetUtil.changeSecondsToHours(round * (roundtime + resttime + preparetime));
        totalTimeView.setText(totalTime);
    }

    private void showSavedPresetDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.BOTTOM;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_preset_popup);

        ListView presetListView = (ListView)dialog.findViewById(R.id.preset_listview);
        final PresetListAdapter adapter = new PresetListAdapter(this, savedPreset, currentPresetPosition);
        presetListView.setAdapter(adapter);

        presetListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("Super", "item selected  position = " + position);
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
        presetDTO.setWeight(weightSpinner.getSelectedItemPosition());
        presetDTO.setGlove(gloveTypeSpinner.getSelectedItemPosition());
        presetDTO.setGender(genderSpinner.getSelectedItemPosition());
        presetDTO.setName("Preset " + String.valueOf(savedPreset.size() + 1));

        savedPreset.add(presetDTO);
        SharedPreferencesUtils.savePresetLists(this, savedPreset);
        presetNameView.setText(presetDTO.getName());
    }

    @Override
    public void finish() {
        super.finish();
//        overridePendingTransition(R.anim.left_center, R.anim.center_right);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
