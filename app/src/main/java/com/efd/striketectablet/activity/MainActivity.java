package com.efd.striketectablet.activity;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.efd.striketectablet.DTO.PunchDataDTO;
import com.efd.striketectablet.R;
import com.efd.striketectablet.activity.profile.ProfileFragment;
import com.efd.striketectablet.activity.training.TrainingFragment;
import com.efd.striketectablet.activity.trainingstats.TrainingStatsFragment;
import com.efd.striketectablet.bluetooth.readerBean.PunchDetectionConfig;
import com.efd.striketectablet.database.DBAdapter;
import com.efd.striketectablet.mmaGlove.EffectivePunchMassCalculator;
import com.efd.striketectablet.utilities.EFDConstants;
import com.efd.striketectablet.utilities.TrainingManager;
import com.gigamole.navigationtabbar.ntb.NavigationTabBar;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    public static final int REQUEST_ENABLE_BT = 1;
    public static final int MESSAGE_TOAST = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int rightDeviceRequestCode = 4;
    public static final int leftDeviceRequestCode = 5;
    public static final int guestRightDeviceRequestCode = 6;
    public static final int guestLeftDeviceRequestCode = 7;

    public static final String TOAST = "TOAST";

    public static double boxerPunchMassEffect;

    public boolean flagForDevice;
    String currentRightDevice = null, currentLeftDevice = null;

    private boolean isGuestBoxerActive;

    public String userId = "1";
    public static DBAdapter db;

    public PunchDataDTO punchDataDTO = new PunchDataDTO();

    public static Context context;
    private CustomViewPagerAdapter mAdapter;
    private ViewPager viewPager;

    public TrainingManager trainingManager = new TrainingManager();

    public MainActivity() {
        flagForDevice = false;
        setGuestBoxerActive(false);
        Log.d(TAG, "flagForDevice :" + flagForDevice);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        db = DBAdapter.getInstance(MainActivity.this);

        int theUserId = Integer.valueOf(userId);
        HashMap<String, String> usersBoxerDetails = MainActivity.db.getUsersBoxerDetails(theUserId);

        double punchMassEffect = calculateBoxerPunchMassEffect(usersBoxerDetails);
        PunchDetectionConfig.getInstance().setPunchMassEff(punchMassEffect);


        initUI();

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, MainActivity.REQUEST_ENABLE_BT);
        }

    }

    public boolean isGuestBoxerActive() {
        return isGuestBoxerActive;
    }

    public void setGuestBoxerActive(boolean isGuestBoxerActive) {
        this.isGuestBoxerActive = isGuestBoxerActive;
    }

    public double calculateBoxerPunchMassEffect(HashMap<String, String> boxerDetails) {

        double effectivePunchMass = EFDConstants.GUEST_TRAINING_EFFECTIVE_PUNCH_MASS;

        try {

            JSONObject user_info_jsonObj = MainActivity.db.trainingUserInfo(userId).getJSONObject("userInfo");

            String gender = user_info_jsonObj.getString("user_gender");

            if (boxerDetails != null) {

                // Putting this here for now. Just recalculate it when we need to use
                //    it instead of dealing with storing it (short term) ECH

                String weightCountValue = "150";
                String traineeSkillLevelValue = "Beginner";

                if (boxerDetails.containsKey(DBAdapter.KEY_BOXER_WEIGHT)) {
                    weightCountValue = boxerDetails.get(DBAdapter.KEY_BOXER_WEIGHT);
                }

                if (boxerDetails.containsKey(DBAdapter.KEY_BOXER_SKILL_LEVEL)) {
                    traineeSkillLevelValue = boxerDetails.get(DBAdapter.KEY_BOXER_SKILL_LEVEL);
                }

                if (StringUtils.isBlank(gender) && boxerDetails.containsKey(DBAdapter.KEY_USER_GENDER)) {
                    gender = boxerDetails.get(DBAdapter.KEY_USER_GENDER);
                }

                EffectivePunchMassCalculator calculator = new EffectivePunchMassCalculator();
                effectivePunchMass = calculator.calculatePunchMassEffect(weightCountValue, traineeSkillLevelValue, gender);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            effectivePunchMass = EFDConstants.GUEST_TRAINING_EFFECTIVE_PUNCH_MASS;
        }

        return effectivePunchMass;
    }

    public String getCurrentRightDevice() {
        return currentRightDevice;
    }

    public String getCurrentLeftDevice() {
        return currentLeftDevice;
    }

    public void setCurrentRightDevice(String deviceString) {
        currentRightDevice = deviceString;
    }

    public void setCurrentLeftDevice(String deviceString) {
        currentLeftDevice = deviceString;
    }


    @Override
    protected void onResume() {
        super.onResume();
        DBAdapter.clearDBAdapter();
        db = DBAdapter.getInstance(this);
    }

    void initUI(){
        final NavigationTabBar navigationTabBar;

        viewPager = (ViewPager) findViewById(R.id.navigation_viewpager);

        mAdapter = new CustomViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);

        navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb_horizontal);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.profile),
                        getResources().getColor(R.color.active_color_ntb))
                        .selectedIcon(getResources().getDrawable(R.drawable.profile_selected))
                        .title("PROFILE")
                        .badgeTitle("NTB")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.training),
                        getResources().getColor(R.color.active_color_ntb))
                        .selectedIcon(getResources().getDrawable(R.drawable.training_selected))
                        .title("TRAINING")
                        .badgeTitle("with")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.trainingstats),
                        getResources().getColor(R.color.active_color_ntb))
                        .selectedIcon(getResources().getDrawable(R.drawable.training_stats_selected))
                        .title("STATS")
                        .badgeTitle("state")
                        .build()
        );

        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(viewPager, 0);

        navigationTabBar.setTitleMode(NavigationTabBar.TitleMode.ACTIVE);

        navigationTabBar.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
//                navigationTabBar.getModels().get(position).hideBadge();
                Fragment fragment = (Fragment) mAdapter.instantiateItem(viewPager, position);
                if (fragment != null) {

                }
            }

            @Override
            public void onPageScrollStateChanged(final int state) {

            }
        });

        navigationTabBar.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < navigationTabBar.getModels().size(); i++) {
                    final NavigationTabBar.Model model = navigationTabBar.getModels().get(i);
                    navigationTabBar.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            model.showBadge();
                        }
                    }, i * 100);
                }
            }
        }, 150);
    }

    private class CustomViewPagerAdapter extends FragmentStatePagerAdapter {

        private String[] titles = { "PROFILE", "TRAINING", "STATS" };
        private final FragmentManager mFragmentManager;

        public CustomViewPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            mFragmentManager = fragmentManager;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new ProfileFragment();
                case 1:
                    return new TrainingFragment();
                case 2:
                    return new TrainingStatsFragment();

                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public int getItemPosition(Object object)
        {
            return POSITION_NONE;
        }

    }
}
