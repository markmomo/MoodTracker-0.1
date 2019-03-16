package markmomo.com.moodtracker1.controllers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
    private ArrayList<Integer> mMoods;
    private ArrayList<String> mNotes;
    private ViewPager mViewPager;
    private EditText mNoteBox;

    //Start HistoryActivity
    public void onHistoryIconClicked(View view) {
        Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
        updateMoodsAndNotes();
        intent.putIntegerArrayListExtra("mMoods history",mMoods);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
        mNoteBox = new EditText(this);

        this.loadMoodsData();
        this.loadNotesData();
        this.startAlarm();

        Log.e("TAG", "onCreate: onCreate: onCreate: onCreate: onCreate: onCreate: onCreate: ");
        Log.e("TAG", "mMoods = "+ mMoods);
        Log.e("TAG", "mNotes = "+ mNotes);
        Log.e("TAG", "DAY_COUNTER = "+ mPrefs.getInt(DAY_COUNTER,-8));

    }

    @Override
    protected void onStart() {
        super.onStart();
//        to test missing days without alarm
//        mPrefs.edit().putInt(DAY_COUNTER,1).apply();

        configureViewPager();

        updateMoodsAndNotes();
        saveMoodsData();
        loadMoodsData();
        saveNotesData();
        loadNotesData();

        onListeningViewPager();

        Log.e("TAG", "onStart: onStart: onStart: onStart: onStart: onStart: onStart: ");
        Log.e("TAG", "mMoods = "+ mMoods);
        Log.e("TAG", "mNotes = "+ mNotes);
        Log.e("TAG", "DAY_COUNTER = "+ mPrefs.getInt(DAY_COUNTER,-8));
    }

    @Override
    protected void onPause() {
        super.onPause();

        updateMoodsAndNotes();
        saveMoodsData();
        loadMoodsData();
        saveNotesData();
        loadNotesData();

        Log.e("TAG", "onPause: onPause: onPause: onPause: onPause: onPause: onPause: onPause: ");
        Log.e("TAG", "mMoods = "+ mMoods);
        Log.e("TAG", "mNotes = "+ mNotes);

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

        //display default mood and note if the day have changed
        if (mPrefs.getInt(DAY_COUNTER,0)>0){
            mViewPager.setCurrentItem(3);
            mNoteBox.setText(null);
        } else {
            mViewPager.setCurrentItem(mMoods.get(0));
            mNoteBox.setText(mNotes.get(0));
        }

        //Set widgets background color
        noteIcon.setBackgroundColor(pageAdapter.getMainIconsColor());
        historyIcon.setBackgroundColor(pageAdapter.getMainIconsColor());
    }

    private void onListeningViewPager(){

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }
            @Override
            public void onPageSelected(int position) {

                updateMoodsAndNotes();
                saveMoodsData();
                loadMoodsData();
                saveNotesData();
                loadNotesData();

                Log.e("TAG", "onPageSelected: onPageSelected: onPageSelected: onPageSelected: onPageSelected: onPageSelected: onPageSelected: ");
                Log.e("TAG", "mMoods = "+ mMoods);
                Log.e("TAG", "mNotes = "+ mNotes);
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

    //add entries to mMoods, mNotes and reset DAY_COUNTER
    private void updateMoodsAndNotes(){

        //keep mMoods and mNotes ArrayList size to 8 items
        while (mMoods.size() > 8){
            mMoods.remove(mMoods.size()-1);
        }
        while (mNotes.size() > 8){
            mNotes.remove(mNotes.size()-1);
        }

        // add empty entries on missing days and reset DAY_COUNTER
        if (mPrefs.getInt(DAY_COUNTER,0)>0){
            for(int i = 1;i < mPrefs.getInt(DAY_COUNTER,0);i++){
                mMoods.add(0,-1);
                mNotes.add(0,"");
            }
            mPrefs.edit().putInt(DAY_COUNTER,0).apply();
            mMoods.add(0,mViewPager.getCurrentItem());
            mNotes.add(1,mNoteBox.getText().toString());
            mNoteBox.setText(null);


        //update today entry
        }else {
            mMoods.remove(0);
            mMoods.add(0,mViewPager.getCurrentItem());
            mNotes.remove(0);
            mNotes.add(0,mNoteBox.getText().toString());
        }
    }

    private void saveMoodsData() {
        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mMoods);
        editor.putString("mMoods list", json);
        editor.apply();
    }

    private void loadMoodsData() {
        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("mMoods list", null);
        Type type = new TypeToken<ArrayList<Integer>>() {
        }.getType();
        mMoods = gson.fromJson(json, type);

        //initialize mMoods if empty
        if (mMoods == null || mMoods.isEmpty()) {
            mMoods = new ArrayList<>();
            mMoods.add(0,3);
            while (mMoods.size() != 8){
                mMoods.add(-1);
            }
        }
    }

    public void OnNoteIconClicked (View view) {
        updateMoodsAndNotes();
        saveMoodsData();
        loadMoodsData();
        saveNotesData();
        loadNotesData();
        displayNoteBox();
    }

    private void displayNoteBox(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setMessage("efface votre commentaire du jour");
        alert.setTitle("Commentaire");
        alert.setView(mNoteBox);


        alert.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                mNoteBox.setText(null);
            }
        });
        alert.setPositiveButton("Valider", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                updateMoodsAndNotes();
                saveMoodsData();
                loadMoodsData();
                saveNotesData();
                loadNotesData();
            }
        });
        alert.setCancelable(false);
        alert.create();
        if(mNoteBox.getParent() != null) {
            ((ViewGroup)mNoteBox.getParent()).removeView(mNoteBox); // <- fix
        }
        alert.show();
    }

    private void saveNotesData() {
        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mNotes);
        editor.putString("notes list", json);
        editor.apply();
    }

    private void loadNotesData() {
        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("notes list", null);
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        mNotes = gson.fromJson(json, type);

        //initialize mNotes if empty
        if (mNotes == null || mNotes.isEmpty()) {
            mNotes = new ArrayList<>();
            mNotes.add(0,"");
            while (mNotes.size() != 8){
                mNotes.add("");
            }
        }
    }
}
