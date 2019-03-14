package markmomo.com.moodtracker1.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import static markmomo.com.moodtracker1.controllers.MainActivity.DAY_COUNTER;

/**
 * Created by markm On 14/03/2019.
 */
public class AlarmReceiver extends BroadcastReceiver {
    SharedPreferences mPrefs;


    @Override
    public void onReceive(Context context, Intent intent) {

        //increment DAY_COUNTER by 1
        mPrefs = context.getSharedPreferences("myPrefs",Context.MODE_PRIVATE);
        mPrefs.edit().putInt(DAY_COUNTER, mPrefs.getInt(DAY_COUNTER ,0)+1).apply();

        Toast.makeText(context,"dayCounter = "+mPrefs.getInt(DAY_COUNTER,0),Toast.LENGTH_SHORT).show();
        Log.e("TAG", "onReceive: onReceive: onReceive: onReceive: onReceive: onReceive: onReceive: ");

    }
}
