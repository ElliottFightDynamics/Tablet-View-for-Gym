package com.efd.striketectablet.activity.training.combination;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.efd.striketectablet.DTO.ComboDTO;
import com.efd.striketectablet.R;
import com.efd.striketectablet.adapter.EditNewCombinationListAdapter;
import com.efd.striketectablet.adapter.PopupPunchListAdapter;
import com.efd.striketectablet.util.ComboSetUtil;
import com.efd.striketectablet.util.StatisticUtil;
import com.efd.striketectablet.utilities.EFDConstants;
import com.efd.striketectablet.utilities.SharedPreferencesUtils;

import java.util.ArrayList;

public class NewCombinationActivity extends AppCompatActivity {

    TextView titleView;
    TextView addpunchView;
    ImageView btnBack;
    TextView saveBtn;
    EditText comboName;

    LinearLayout punchkeyParentView;
    ListView punchdetailListView;
    EditNewCombinationListAdapter detailAdapter;

    ArrayList<String> punchKeyLists;

    boolean editmode = false;
    int comboID = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_new_combination);

        editmode = getIntent().getBooleanExtra(EFDConstants.EDIT_COMBINATION, false);
        if (editmode){
            comboID = getIntent().getIntExtra(EFDConstants.EDIT_COMBOID, -1);
        }

        punchKeyLists = new ArrayList<>();

        initViews();
    }

    private void initViews(){
        titleView = (TextView)findViewById(R.id.combination_title);
        if (editmode){
            titleView.setText(getResources().getString(R.string.edit_combination_title));
        }else {
            titleView.setText(getResources().getString(R.string.new_combination_title));
        }

        comboName = (EditText)findViewById(R.id.combo_name);

        addpunchView = (TextView)findViewById(R.id.add_punch);
        addpunchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPunchListDialog(punchKeyLists.size(), false);
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
                saveCombination();
            }
        });
        punchkeyParentView = (LinearLayout)findViewById(R.id.punck_key_parent);
        punchdetailListView = (ListView) findViewById(R.id.punch_detail);

        detailAdapter = new EditNewCombinationListAdapter(this, punchKeyLists);
        punchdetailListView.setAdapter(detailAdapter);

        updateSaveBtn();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (editmode){
            loadComboDetail();
        }
    }

    private void saveCombination(){
        if (TextUtils.isEmpty(comboName.getText().toString())){
            StatisticUtil.showToastMessage("Combincation name can't be empty");
            return;
        }

        if (punchKeyLists.size() == 0){
            StatisticUtil.showToastMessage("please add more than 1 punch");
            return;
        }

        if (editmode){
            ComboDTO comboDTO = new ComboDTO(comboName.getText().toString(), punchKeyLists, comboID);
            ComboSetUtil.updateComboDto(comboDTO);
        }else {
            ComboDTO comboDTO = new ComboDTO(comboName.getText().toString(), punchKeyLists, SharedPreferencesUtils.increaseComboID(this));
            ComboSetUtil.addComboDto(comboDTO);
        }

        finish();
    }

    private void loadComboDetail(){
        ComboDTO comboDTO = ComboSetUtil.getComboDtowithID(comboID);

        if (comboDTO != null){
            comboName.setText(comboDTO.getName());

            if (punchKeyLists != null && punchKeyLists.size() > 0){
                punchKeyLists.clear();
            }

//            punchKeyLists.addAll(comboDTO.getComboTypes());

            for (int i = 0; i <comboDTO.getComboTypes().size(); i++){
                punchKeyLists.add(comboDTO.getComboTypes().get(i));
                addPunckKeyChildView(i, comboDTO.getComboTypes().get(i));
            }

            detailAdapter.notifyDataSetChanged();

            updateSaveBtn();
        }
    }

    private void updateSaveBtn(){
        if (punchKeyLists.size() > 0){
            saveBtn.setBackgroundResource(R.drawable.orange_btn_bg);
            saveBtn.setTextColor(getResources().getColor(R.color.orange));
        }else {
            saveBtn.setBackgroundResource(R.drawable.trainingstats_btn_fill);
            saveBtn.setTextColor(getResources().getColor(R.color.black));
        }
    }

    private void addPunckKeyChildView(int position, String key){
        final LinearLayout newLayout = (LinearLayout)getLayoutInflater().inflate(R.layout.item_punchkey, null);
        TextView keyView = (TextView)newLayout.findViewById(R.id.key);

        if (position == 0){
            keyView.setBackgroundResource(R.drawable.punchkey_bg_first);

            if (punchKeyLists.size() > 1){
                LinearLayout nextChild = (LinearLayout)punchkeyParentView.getChildAt(position);
                TextView nextkeyView = (TextView)nextChild.findViewById(R.id.key);
                nextkeyView.setBackgroundResource(R.drawable.punchkey_bg_later);
            }

        }else
            keyView.setBackgroundResource(R.drawable.punchkey_bg_later);


        keyView.setText(key);

        punchkeyParentView.addView(newLayout, position);
//        punchkeyParentView.addView(newLayout);
    }

    private void removePunchKeyChildView(int position){
        LinearLayout childLayout = (LinearLayout)punchkeyParentView.getChildAt(position);

        if (position == 0){
            if (punchKeyLists.size() > 1){
                LinearLayout nextChild = (LinearLayout)punchkeyParentView.getChildAt(1);
                TextView keyView = (TextView)nextChild.findViewById(R.id.key);
                keyView.setBackgroundResource(R.drawable.punchkey_bg_first);
            }
        }

        punchkeyParentView.removeView(childLayout);
    }

    private void updatePunchKeyChildView(int position, String key){
        LinearLayout childLayout = (LinearLayout)punchkeyParentView.getChildAt(position);
        TextView keyView = (TextView)childLayout.findViewById(R.id.key);
        keyView.setText(key);

//        final LinearLayout newLayout = (LinearLayout)getLayoutInflater().inflate(R.layout.item_punchkey, null);
//        TextView keyView = (TextView)newLayout.findViewById(R.id.key);
//        if (punchKeyLists.size() > 1){
//            keyView.setBackgroundResource(R.drawable.punchkey_bg_later);
//        }else
//            keyView.setBackgroundResource(R.drawable.punchkey_bg_first);
//
//        keyView.setText(key);
//
//        punchkeyParentView.addView(newLayout, position);
//        punchkeyParentView.addView(newLayout);
    }

//    private void addPunchDetailChildView(String key){
//        final LinearLayout newLayout = (LinearLayout)getLayoutInflater().inflate(R.layout.item_addpunch_list, null);
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
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.BOTTOM;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_editcomboset);

        final TextView replaceView, insertaboveView, insertbelowView, deleteView;
        replaceView = (TextView)dialog.findViewById(R.id.replace);
        insertaboveView = (TextView)dialog.findViewById(R.id.insert_above);
        insertbelowView = (TextView)dialog.findViewById(R.id.insert_below);
        deleteView = (TextView)dialog.findViewById(R.id.delete);

        replaceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPunchListDialog(position, true);
                dialog.dismiss();
            }
        });

        insertaboveView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPunchListDialog(position, false);
                dialog.dismiss();
            }
        });

        insertbelowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPunchListDialog(position + 1, false);
                dialog.dismiss();
            }
        });


        deleteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                punchKeyLists.remove(position);
                detailAdapter.notifyDataSetChanged();
                removePunchKeyChildView(position);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void showPunchListDialog(final int comboposition, final boolean replace){
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_addcombo);

        TextView cancelView = (TextView)dialog.findViewById(R.id.cancel_btn);
        final ListView punchListsView = (ListView)dialog.findViewById(R.id.comboset_listview);
        final PopupPunchListAdapter adapter = new PopupPunchListAdapter(this, ComboSetUtil.keyLists);
        punchListsView.setAdapter(adapter);

        punchListsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialog.dismiss();

                String key = ComboSetUtil.keyLists.get(position);

                if (replace){
                    punchKeyLists.set(comboposition, key);
                    updatePunchKeyChildView(comboposition, key);
                }else {
                    punchKeyLists.add(comboposition, key);
                    addPunckKeyChildView(comboposition, key);
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
