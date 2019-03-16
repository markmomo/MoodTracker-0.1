package markmomo.com.moodtracker1.controllers;

import android.content.Intent;
import android.support.annotation.ColorRes;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import markmomo.com.moodtracker1.R;

public class HistoryActivity extends AppCompatActivity {

    private ArrayList<ImageButton> mButtons;
    private ArrayList<ConstraintLayout> mCstLayouts;
    private @ColorRes int mColor;
    private ArrayList<String> mNotes;
    private ArrayList<Integer> mMoods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        //INITIALIZATION LAYOUTS AND WIDGETS
        ImageButton mButton1,mButton2,mButton3,mButton4,mButton5,mButton6,mButton7;
        ConstraintLayout mDay1Left,mDay2Left,mDay3Left,mDay4Left,mDay5Left,mDay6Left,mDay7Left;
        mDay1Left = findViewById(R.id.act_history_day1);
        mDay2Left = findViewById(R.id.act_history_day2);
        mDay3Left = findViewById(R.id.act_history_day3);
        mDay4Left = findViewById(R.id.act_history_day4);
        mDay5Left = findViewById(R.id.act_history_day5);
        mDay6Left = findViewById(R.id.act_history_day6);
        mDay7Left = findViewById(R.id.act_history_day7);
        mButton1 = findViewById(R.id.act_history_day1_Btn);
        mButton2 = findViewById(R.id.act_history_day2_Btn);
        mButton3 = findViewById(R.id.act_history_day3_Btn);
        mButton4 = findViewById(R.id.act_history_day4_Btn);
        mButton5 = findViewById(R.id.act_history_day5_Btn);
        mButton6 = findViewById(R.id.act_history_day6_Btn);
        mButton7 = findViewById(R.id.act_history_day7_Btn);
        mCstLayouts = new ArrayList<>();
        mCstLayouts.add(mDay1Left);mCstLayouts.add(mDay2Left);mCstLayouts.add(mDay3Left);mCstLayouts.add(mDay4Left);
        mCstLayouts.add(mDay5Left);mCstLayouts.add(mDay6Left);mCstLayouts.add(mDay7Left);
        mButtons = new ArrayList<>();
        mButtons.add(mButton1);mButtons.add(mButton2);mButtons.add(mButton3);mButtons.add(mButton4);
        mButtons.add(mButton5);mButtons.add(mButton6);mButtons.add(mButton7);

        //load data from MainActivity
        Intent intent = getIntent ();
        mMoods = intent.getIntegerArrayListExtra("mMoods history");
        mNotes = intent.getStringArrayListExtra("mNotes history");

        //display management of history colors, sizes, and icons.
        for (int i = 0; i < 7; i++){
            displayHistory(mMoods.get(i+1), i+1);
        }
        noteIconIsDisplayed ();
    }

    public void button7IsClicked(View view){
        Toast.makeText(this, mNotes.get(7), Toast.LENGTH_SHORT).show();
        Log.e("HistoryActivity Button", "button7IsClicked: ");
    }
    public void button6IsClicked(View view){
        Toast.makeText(this, mNotes.get(6), Toast.LENGTH_SHORT).show();
        Log.e("HistoryActivity Button", "button6IsClicked: ");
    }
    public void button5IsClicked(View view){
        Toast.makeText(this, mNotes.get(5), Toast.LENGTH_SHORT).show();
        Log.e("HistoryActivity Button", "button5IsClicked: ");
    }
    public void button4IsClicked(View view){
        Toast.makeText(this, mNotes.get(4), Toast.LENGTH_SHORT).show();
        Log.e("HistoryActivity Button", "button4IsClicked: ");
    }
    public void button3IsClicked(View view){
        Toast.makeText(this, mNotes.get(3), Toast.LENGTH_SHORT).show();
        Log.e("HistoryActivity Button", "button3IsClicked: ");
    }
    public void button2IsClicked(View view){
        Toast.makeText(this, mNotes.get(2), Toast.LENGTH_SHORT).show();
        Log.e("HistoryActivity Button", "button2IsClicked: ");
    }
    public void button1IsClicked(View view){

        Toast.makeText(this, mNotes.get(1), Toast.LENGTH_SHORT).show();
        Log.e("HistoryActivity Button", "button1IsClicked: ");
    }

    private void displayHistory (int moodNumber, int position) {

        switch (moodNumber){
            case 0 :
                mColor = R.color.faded_red;
                break;
            case 1 :
                mColor = R.color.warm_grey;
                break;
            case 2 :
                mColor = R.color.cornflower_blue_65;
                break;
            case 3 :
                mColor = R.color.light_sage;
                break;
            case 4 :
                mColor = R.color.banana_yellow;
                break;
            default:
                mColor = R.color.myGrey;
        }
        //choose the color and size on the child of activity_history LinearLayout
        switch (position) {
            case 1:
                chooseWidthAndColor(moodNumber,mCstLayouts.get(position-1),mButtons.get(position-1));
                break;
            case 2:
                chooseWidthAndColor(moodNumber,mCstLayouts.get(position-1),mButtons.get(position-1));
                break;
            case 3:
                chooseWidthAndColor(moodNumber,mCstLayouts.get(position-1),mButtons.get(position-1));
                break;
            case 4:
                chooseWidthAndColor(moodNumber,mCstLayouts.get(position-1),mButtons.get(position-1));
                break;
            case 5:
                chooseWidthAndColor(moodNumber,mCstLayouts.get(position-1),mButtons.get(position-1));
                break;
            case 6:
                chooseWidthAndColor(moodNumber,mCstLayouts.get(position-1),mButtons.get(position-1));
                break;
            case 7:
                chooseWidthAndColor(moodNumber,mCstLayouts.get(position-1),mButtons.get(position-1));
        }
    }

    private int getWidthScreen() {
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        return outMetrics.widthPixels;
    }

    private void chooseWidthAndColor(int number , ConstraintLayout constraint, ImageButton imgButton) {

        switch (number){
            case 0 :
                constraint.setMaxWidth(getWidthScreen()/5);
                break;
            case 1 :
                constraint.setMaxWidth((getWidthScreen()/5)*2);
                break;
            case 2 :
                constraint.setMaxWidth((getWidthScreen()/5)*3);
                break;
            case 3 :
                constraint.setMaxWidth((getWidthScreen()/5)*4);
                break;
        }
        constraint.setBackgroundColor(getResources().getColor(mColor));
        imgButton.setBackgroundColor(getResources().getColor(mColor));
    }

    private void noteIconIsDisplayed (){

        for (int i = 1; i < 8; i++){

            if (mNotes.get(i).length() < 1) {
                mButtons.get(i-1).setVisibility(View.GONE);
            }
        }
    }
}

