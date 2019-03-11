package markmomo.com.moodtracker1.models;


import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import markmomo.com.moodtracker1.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SmileyFragment extends Fragment {
    //Create keys for our Bundle
    private static final String KEY_POSITION="position";
    private static final String KEY_COLOR="color";

    public SmileyFragment() { }

    //Method that will create a new instance of PageFragment, and add data to its bundle.
    public static SmileyFragment newInstance(int position, int color) {
        SmileyFragment frag = new SmileyFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, position);
        args.putInt(KEY_COLOR, color);
        frag.setArguments(args);
        return(frag);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Get layout of PageFragment
        View result = inflater.inflate(R.layout.fragment_smiley, container, false);

        //Get widgets from layout and serialise it
        LinearLayout rootView = result.findViewById(R.id.frag_smiley_root);
        ImageView imageView = result.findViewById(R.id.frag_smiley);

        //Get data from Bundle (created in method newInstance)
        int position = getArguments().getInt(KEY_POSITION, -1);
        int color = getArguments().getInt(KEY_COLOR, -1);

        //Get smiley from drawable
        @DrawableRes int smileys[] = new int [] {R.drawable.smiley_sad,R.drawable.smiley_disappointed,
                                                R.drawable.smiley_normal,R.drawable.smiley_happy,
                                                R.drawable.smiley_super_happy};
        //Update widgets with Bundle
        rootView.setBackgroundColor(color);

        //Update smiley on position
        for (int i = 0 ; i < position+1 ; i++){
            imageView.setImageResource(smileys[i]);
        }
        Log.e(getClass().getSimpleName(), "onCreateView called for fragment number "+position);
        return result;
    }
}
