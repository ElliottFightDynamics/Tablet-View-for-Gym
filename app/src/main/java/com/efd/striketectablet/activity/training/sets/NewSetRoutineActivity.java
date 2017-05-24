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
import com.efd.striketectablet.adapter.AddComboListAdapter;
import com.efd.striketectablet.adapter.EditComboListAdapter;
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
    EditComboListAdapter detailAdapter;

    ArrayList<Integer> comboPositionLists;

    boolean editmode = false;
    int setDtoIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_set_routine);

        editmode = getIntent().getBooleanExtra(EFDConstants.EDIT_SETS, false);
        if (editmode){
            setDtoIndex = getIntent().getIntExtra(EFDConstants.EDIT_SETPOSITION, -1);
        }

        comboPositionLists = new ArrayList<>();

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
                showComboListDialog(comboPositionLists.size(), false);
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

        detailAdapter = new EditComboListAdapter(this, comboPositionLists);
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
        ArrayList<SetsDTO> setsDTOs = SharedPreferencesUtils.getSavedSetList(this);

        if (TextUtils.isEmpty(setName.getText().toString())){
            StatisticUtil.showToastMessage("Combincation name can't be empty");
            return;
        }

        if (comboPositionLists.size() == 0){
            StatisticUtil.showToastMessage("please add more than 1 punch");
            return;
        }

        SetsDTO setsDTO = new SetsDTO(setName.getText().toString(), comboPositionLists);

        if (editmode){
            setsDTOs.set(setDtoIndex, setsDTO);
            SharedPreferencesUtils.saveSetList(this, setsDTOs);
        }else {
            setsDTOs.add(setsDTO);
            SharedPreferencesUtils.saveSetList(this, setsDTOs);
        }

        finish();
    }

    private void loadSetDetail(){
        ArrayList<SetsDTO> setsDTOs = SharedPreferencesUtils.getSavedSetList(this);
        SetsDTO setsDTO = setsDTOs.get(setDtoIndex);

        if (setsDTO != null){
            setName.setText(setsDTO.getName());

            if (comboPositionLists != null && comboPositionLists.size() > 0){
                comboPositionLists.clear();
            }

            comboPositionLists.addAll(setsDTO.getComboPositionlists());

//            for (int i = 0; i <setsDTO.getComboPositionlists().size(); i++){
//                comboPositionLists.add(setsDTO.getComboPositionlists().get(i));
////                addPunckKeyChildView(i, setsDTO.getComboPositionlists().get(i));
//            }

            detailAdapter.notifyDataSetChanged();

            updateSaveBtn();
        }
    }

    private void updateSaveBtn(){
        if (comboPositionLists.size() > 0){
            saveBtn.setBackgroundResource(R.drawable.orange_btn_bg);
            saveBtn.setTextColor(getResources().getColor(R.color.orange));
        }else {
            saveBtn.setBackgroundResource(R.drawable.trainingstats_btn_fill);
            saveBtn.setTextColor(getResources().getColor(R.color.black));
        }
    }

//    private void addPunckKeyChildView(int position, String key){
//        final LinearLayout newLayout = (LinearLayout)getLayoutInflater().inflate(R.layout.item_punchkey, null);
//        TextView keyView = (TextView)newLayout.findViewById(R.id.key);
//
//        if (position == 0){
//            keyView.setBackgroundResource(R.drawable.punchkey_bg_first);
//
//            if (punchKeyLists.size() > 1){
//                LinearLayout nextChild = (LinearLayout)punchkeyParentView.getChildAt(position);
//                TextView nextkeyView = (TextView)nextChild.findViewById(R.id.key);
//                nextkeyView.setBackgroundResource(R.drawable.punchkey_bg_later);
//            }
//
//        }else
//            keyView.setBackgroundResource(R.drawable.punchkey_bg_later);
//
//
//        keyView.setText(key);
//
//        punchkeyParentView.addView(newLayout, position);
////        punchkeyParentView.addView(newLayout);
//    }

//    private void removePunchKeyChildView(int position){
//        LinearLayout childLayout = (LinearLayout)punchkeyParentView.getChildAt(position);
//
//        if (position == 0){
//            if (punchKeyLists.size() > 1){
//                LinearLayout nextChild = (LinearLayout)punchkeyParentView.getChildAt(1);
//                TextView keyView = (TextView)nextChild.findViewById(R.id.key);
//                keyView.setBackgroundResource(R.drawable.punchkey_bg_first);
//            }
//        }
//
//        punchkeyParentView.removeView(childLayout);
//    }

//    private void updatePunchKeyChildView(int position, String key){
//        LinearLayout childLayout = (LinearLayout)punchkeyParentView.getChildAt(position);
//        TextView keyView = (TextView)childLayout.findViewById(R.id.key);
//        keyView.setText(key);
//
////        final LinearLayout newLayout = (LinearLayout)getLayoutInflater().inflate(R.layout.item_punchkey, null);
////        TextView keyView = (TextView)newLayout.findViewById(R.id.key);
////        if (punchKeyLists.size() > 1){
////            keyView.setBackgroundResource(R.drawable.punchkey_bg_later);
////        }else
////            keyView.setBackgroundResource(R.drawable.punchkey_bg_first);
////
////        keyView.setText(key);
////
////        punchkeyParentView.addView(newLayout, position);
////        punchkeyParentView.addView(newLayout);
//    }

//    private void addPunchDetailChildView(String key){
//        final LinearLayout newLayout = (LinearLayout)getLayoutInflater().inflate(R.layout.item_punchdetail, null);
//        TextView keyView = (TextView)newLayout.findViewById(R.id.key);
//        TextView nameView = (TextView)newLayout.findViewById(R.id.name);
//
//        keyView.setText(key);
//        nameView.setText(ComboSetUtil.punchTypeMap.get(key));
//
//        punchdetailParentView.addView(newLayout);
//    }

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
                comboPositionLists.remove(position);
                detailAdapter.notifyDataSetChanged();
//                removePunchKeyChildView(position);
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

        final AddComboListAdapter adapter = new AddComboListAdapter(this, SharedPreferencesUtils.getSavedCombinationList(this));
        comboListsView.setAdapter(adapter);

        comboListsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialog.dismiss();

                if (replace){
                    comboPositionLists.set(comboposition, position);
//                    updatePunchKeyChildView(comboposition, key);
                }else {
                    comboPositionLists.add(comboposition, position);
//                    addPunckKeyChildView(comboposition, key);
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
