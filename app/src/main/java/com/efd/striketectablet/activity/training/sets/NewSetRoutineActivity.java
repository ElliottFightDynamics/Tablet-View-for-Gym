package com.efd.striketectablet.activity.training.sets;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.efd.striketectablet.DTO.SetsDTO;
import com.efd.striketectablet.R;
import com.efd.striketectablet.adapter.PopupCombinationListAdapter;
import com.efd.striketectablet.adapter.EditNewSetRoutineListAdapter;
import com.efd.striketectablet.util.ComboSetUtil;
import com.efd.striketectablet.util.StatisticUtil;
import com.efd.striketectablet.utilities.EFDConstants;
import com.efd.striketectablet.utilities.SharedPreferencesUtils;

import java.util.ArrayList;

public class NewSetRoutineActivity extends AppCompatActivity {

    TextView titleView;
    TextView addcomboView;
    ImageView btnBack;
    TextView saveBtn;
    EditText setName;

//    LinearLayout punchkeyParentView;
    ListView combodetailListView;
    EditNewSetRoutineListAdapter detailAdapter;

    ArrayList<Integer> comboIDLists;

    boolean editmode = false;
    int setID = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_set_routine);

        editmode = getIntent().getBooleanExtra(EFDConstants.EDIT_SETS, false);
        if (editmode){
            setID = getIntent().getIntExtra(EFDConstants.EDIT_SETID, -1);
        }

        comboIDLists = new ArrayList<>();

        initViews();
    }

    private void initViews(){
        titleView = (TextView)findViewById(R.id.set_routine_title);
        if (editmode){
            titleView.setText(getResources().getString(R.string.edit_set_title));
        }else {
            titleView.setText(getResources().getString(R.string.new_set_title));
        }

        setName = (EditText)findViewById(R.id.set_name);

        addcomboView = (TextView)findViewById(R.id.add_combo);
        addcomboView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showComboListDialog(comboIDLists.size(), false);
            }
        });

        btnBack = (ImageView)findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        saveBtn = (TextView)findViewById(R.id.savebtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSets();
            }
        });

//        punchkeyParentView = (LinearLayout)findViewById(R.id.punck_key_parent);

        combodetailListView = (ListView) findViewById(R.id.combination_list);

        detailAdapter = new EditNewSetRoutineListAdapter(this, comboIDLists);
        combodetailListView.setAdapter(detailAdapter);

        updateSaveBtn();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (editmode){
            loadSetDetail();
        }
    }

    private void saveSets(){

        if (TextUtils.isEmpty(setName.getText().toString())){
            StatisticUtil.showToastMessage("Combincation name can't be empty");
            return;
        }

        if (comboIDLists.size() == 0){
            StatisticUtil.showToastMessage("please add more than 1 punch");
            return;
        }

        if (editmode){
            SetsDTO setsDTO = new SetsDTO(setName.getText().toString(), comboIDLists, setID);
            ComboSetUtil.updateSetDto(setsDTO);
        }else {
            SetsDTO setsDTO = new SetsDTO(setName.getText().toString(), comboIDLists, SharedPreferencesUtils.increaseSetID(this));
            ComboSetUtil.addSetDto(setsDTO);
        }

        finish();
    }

    private void loadSetDetail(){
        SetsDTO setsDTO = ComboSetUtil.getSetDtowithID(setID);

        if (setsDTO != null){
            setName.setText(setsDTO.getName());

            if (comboIDLists != null && comboIDLists.size() > 0){
                comboIDLists.clear();
            }

            comboIDLists.addAll(setsDTO.getComboIDLists());

            detailAdapter.notifyDataSetChanged();

            updateSaveBtn();
        }
    }

    private void updateSaveBtn(){
        if (comboIDLists.size() > 0){
            saveBtn.setBackgroundResource(R.drawable.orange_btn_bg);
            saveBtn.setTextColor(getResources().getColor(R.color.orange));
        }else {
            saveBtn.setBackgroundResource(R.drawable.trainingstats_btn_fill);
            saveBtn.setTextColor(getResources().getColor(R.color.black));
        }
    }

    public void showSettings(final int position){
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.BOTTOM;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_editcomboset);

        final TextView replaceView, insertaboveView, insertbelowView, deleteView;
        replaceView = (TextView)dialog.findViewById(R.id.replace);
        replaceView.setText(getResources().getString(R.string.replace_combo));
        insertaboveView = (TextView)dialog.findViewById(R.id.insert_above);
        insertbelowView = (TextView)dialog.findViewById(R.id.insert_below);
        deleteView = (TextView)dialog.findViewById(R.id.delete);

        replaceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showComboListDialog(position, true);
                dialog.dismiss();
            }
        });

        insertaboveView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showComboListDialog(position, false);
                dialog.dismiss();
            }
        });

        insertbelowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showComboListDialog(position + 1, false);
                dialog.dismiss();
            }
        });


        deleteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comboIDLists.remove(position);
                detailAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void showComboListDialog(final int comboposition, final boolean replace){
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_addset);

        TextView titleView = (TextView)dialog.findViewById(R.id.titleview);
        titleView.setText(getResources().getString(R.string.dialog_settitle));

        TextView cancelView = (TextView)dialog.findViewById(R.id.cancel_btn);
        final ListView comboListsView = (ListView)dialog.findViewById(R.id.comboset_listview);

        final PopupCombinationListAdapter adapter = new PopupCombinationListAdapter(this, SharedPreferencesUtils.getSavedCombinationList(this));
        comboListsView.setAdapter(adapter);

        comboListsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialog.dismiss();

                if (replace){
                    comboIDLists.set(comboposition, SharedPreferencesUtils.getSavedCombinationList(NewSetRoutineActivity.this).get(position).getId());
                }else {
                    comboIDLists.add(comboposition, SharedPreferencesUtils.getSavedCombinationList(NewSetRoutineActivity.this).get(position).getId());
                }

                detailAdapter.notifyDataSetChanged();

                updateSaveBtn();
            }
        });


        cancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
