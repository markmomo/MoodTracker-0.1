package markmomo.com.moodtracker1;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

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

