package app.customcomponents;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * Created by talza on 23-Jul-17
 */

public class Separator extends LinearLayout {


    public Separator(Context context) {
        super(context);
        LayoutParams layoutParams = new LayoutParams(MATCH_PARENT, getDpSize(1));
        this.setLayoutParams(layoutParams);
        this.setBackgroundColor(Color.parseColor("#D1CFCF"));
    }

    public int getDpSize(int size) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (size * scale + 0.5f);
    }

}
