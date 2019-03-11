package markmomo.com.moodtracker1.controllers;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import markmomo.com.moodtracker1.R;
import markmomo.com.moodtracker1.adapters.PageAdapter;

public class MainActivity extends AppCompatActivity {

    //Start HistoryActivity
    public void historyIconIsClicked (View view){
        Intent intent = new Intent(MainActivity.this,HistoryActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Configure ViewPager
        this.configureViewPager();
    }

    private void configureViewPager(){
        // Get widgets from layout
        ImageView noteIcon = findViewById(R.id.act_main_note_icon);
        ImageView historyIcon = findViewById(R.id.act_main_history_icon);
        ViewPager pager = findViewById(R.id.act_main_view_pager);

        //Set Adapter PageAdapter and glue it together
        PageAdapter pageAdapter;
        pageAdapter = new PageAdapter(getSupportFragmentManager(), getResources().getIntArray(R.array.viewPagerColors));
        pager.setAdapter(pageAdapter);

        //Set widgets background color
        noteIcon.setBackgroundColor(pageAdapter.getMainIconsColor());
        historyIcon.setBackgroundColor(pageAdapter.getMainIconsColor());
    }
}

