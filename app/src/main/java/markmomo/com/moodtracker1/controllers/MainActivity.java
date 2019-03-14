package markmomo.com.moodtracker1.controllers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

import markmomo.com.moodtracker1.R;
import markmomo.com.moodtracker1.adapters.PageAdapter;
import markmomo.com.moodtracker1.receivers.AlarmReceiver;

public class MainActivity extends AppCompatActivity {

    public static SharedPreferences mPrefs;
    public static final String DAY_COUNTER = "DAY_COUNTER";
    public static ArrayList<Integer> moods;
    private ViewPager mViewPager;


    //Start HistoryActivity
    public void historyIconIsClicked(View view) {
        Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
        updateMoods();
        intent.putIntegerArrayListExtra("moods history",moods);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);

        this.loadMoodsData();
        this.startAlarm();

        Log.e("TAG", "onCreate: onCreate: onCreate: onCreate: onCreate: onCreate: onCreate: ");
        Log.e("TAG", "moods = "+ moods);
        Log.e("TAG", "DAY_COUNTER = "+ mPrefs.getInt(DAY_COUNTER,-8));

    }

    @Override
    protected void onStart() {
        super.onStart();
//        to test missing days without alarm
//        mPrefs.edit().putInt(DAY_COUNTER,1).apply();

        this.configureViewPager();
        this.updateMoods();
        this.listenViewPager();

        Log.e("TAG", "onStart: onStart: onStart: onStart: onStart: onStart: onStart: ");
        Log.e("TAG", "moods = "+ moods);
        Log.e("TAG", "DAY_COUNTER = "+ mPrefs.getInt(DAY_COUNTER,-8));
    }

    @Override
    protected void onPause() {
        super.onPause();

        saveMoodsData();

        Log.e("TAG", "onPause: onPause: onPause: onPause: onPause: onPause: onPause: onPause: ");
        Log.e("TAG", "moods = "+ moods);

    }

    private void configureViewPager() {
        // Get widgets from layout
        ImageView noteIcon = findViewById(R.id.act_main_note_icon);
        ImageView historyIcon = findViewById(R.id.act_main_history_icon);
        mViewPager = findViewById(R.id.act_main_view_pager);

        //Set Adapter PageAdapter and glue it together
        PageAdapter pageAdapter;
        pageAdapter = new PageAdapter(getSupportFragmentManager(), getResources().getIntArray(R.array.viewPagerColors));
        mViewPager.setAdapter(pageAdapter);

        //display default mood if the day have changed
        if (mPrefs.getInt(DAY_COUNTER,0)>0){
            mViewPager.setCurrentItem(3);
        } else {
            mViewPager.setCurrentItem(moods.get(0));
        }

        //Set widgets background color
        noteIcon.setBackgroundColor(pageAdapter.getMainIconsColor());
        historyIcon.setBackgroundColor(pageAdapter.getMainIconsColor());
    }

    private void listenViewPager(){

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }
            @Override
            public void onPageSelected(int position) {

                updateMoods();

                Log.e("TAG", "onPageSelected: onPageSelected: onPageSelected: onPageSelected: onPageSelected: onPageSelected: onPageSelected: ");
                Log.e("TAG", "moods = "+ moods);
            }
            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }

    private void startAlarm() {
        //declarations, initializations
        AlarmManager alarmMgr;
        PendingIntent alarmIntent;
        alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);

        // Set the alarm to start at
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(Calendar.HOUR_OF_DAY, 13);
        cal.set(Calendar.MINUTE, 10);

//        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
//                1000 * 60 * 20, alarmIntent);


        alarmMgr.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_FIFTEEN_MINUTES/15,
                AlarmManager.INTERVAL_FIFTEEN_MINUTES/15, alarmIntent);
    }

    //add entries to moods and reset DAY_COUNTER
    private void updateMoods(){
        while (moods.size() > 8){
            moods.remove(moods.size()-1);
        }
        if (mPrefs.getInt(DAY_COUNTER,0)>0){
            for(int i = 1;i < mPrefs.getInt(DAY_COUNTER,0);i++){
                moods.add(0,-1);
            }
            mPrefs.edit().putInt(DAY_COUNTER,0).apply();
            moods.add(0,mViewPager.getCurrentItem());
        }else {
            moods.remove(0);
            moods.add(0,mViewPager.getCurrentItem());
        }
    }

    private void saveMoodsData() {
        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(moods);
        editor.putString("task list", json);
        editor.apply();
    }

    private void loadMoodsData() {
        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<Integer>>() {
        }.getType();
        moods = gson.fromJson(json, type);

        //initialize moods if empty
        if (moods == null || moods.isEmpty()) {
            moods = new ArrayList<>();
            moods.add(0,3);
            while (moods.size() != 8){
                moods.add(-1);
            }
        }
    }
}
