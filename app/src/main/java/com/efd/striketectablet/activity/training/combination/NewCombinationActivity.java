package com.efd.striketectablet.activity.training.combination;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.efd.striketectablet.R;
import com.efd.striketectablet.adapter.PunchListAdapter;
import com.efd.striketectablet.util.ComboSetUtil;
import com.efd.striketectablet.util.StatisticUtil;

import java.util.ArrayList;

import static android.support.v7.appcompat.R.layout.support_simple_spinner_dropdown_item;

public class NewCombinationActivity extends AppCompatActivity {

    TextView addpunchView;
    ImageView btnBack;
    TextView saveBtn;

    LinearLayout punchkeyParentView;
    LinearLayout punchdetailParentView;

    ArrayList<String> punchKeyLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_new_combination);

        punchKeyLists = new ArrayList<>();

        initViews();
    }

    private void initViews(){
        addpunchView = (TextView)findViewById(R.id.add_punch);
        addpunchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPunchListDialog();
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
        punchkeyParentView = (LinearLayout)findViewById(R.id.punck_key_parent);
        punchdetailParentView = (LinearLayout)findViewById(R.id.punch_detail);

        updateSaveBtn();
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

    private void addPunckKeyChildView(String key){
        final LinearLayout newLayout = (LinearLayout)getLayoutInflater().inflate(R.layout.item_punchkey, null);
        TextView keyView = (TextView)newLayout.findViewById(R.id.key);
        if (punchKeyLists.size() > 1){
            keyView.setBackgroundResource(R.drawable.punchkey_bg_later);
        }else
            keyView.setBackgroundResource(R.drawable.punchkey_bg_first);

        keyView.setText(key);
        punchkeyParentView.addView(newLayout);
    }

    private void addPunchDetailChildView(String key){
        final LinearLayout newLayout = (LinearLayout)getLayoutInflater().inflate(R.layout.item_punchdetail, null);
        TextView keyView = (TextView)newLayout.findViewById(R.id.key);
        TextView nameView = (TextView)newLayout.findViewById(R.id.name);

        keyView.setText(key);
        nameView.setText(ComboSetUtil.punchTypeMap.get(key));

        punchdetailParentView.addView(newLayout);
    }

    private void showPunchListDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_addcomboset);

        TextView cancelView = (TextView)dialog.findViewById(R.id.cancel_btn);
        final ListView punchListsView = (ListView)dialog.findViewById(R.id.comboset_listview);
        final PunchListAdapter adapter = new PunchListAdapter(this, ComboSetUtil.keyLists);
        punchListsView.setAdapter(adapter);

        punchListsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialog.dismiss();
                StatisticUtil.showToastMessage(ComboSetUtil.keyLists.get(position) + " is added");

                String key = ComboSetUtil.keyLists.get(position);

                punchKeyLists.add(key);
                addPunckKeyChildView(key);
                addPunchDetailChildView(key);

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
