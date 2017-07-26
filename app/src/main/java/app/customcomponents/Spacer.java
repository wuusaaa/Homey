package app.customcomponents;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;

/**
 * Created by talza on 25-Jul-17
 */

public class Spacer extends LinearLayout {

    public Spacer(Context context) {
        super(context);
        this.setOrientation(HORIZONTAL);
        LayoutParams layoutParams = new LayoutParams(getDpSize(310), getDpSize(1));
        layoutParams.setMargins(getDpSize(90), 0, 0, 0);
        this.setLayoutParams(layoutParams);
        this.setBackgroundColor(Color.parseColor("#E0E0E0"));
    }

    public int getDpSize(int size) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (size * scale + 0.5f);
    }
}
