package app.customcomponents;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;

/**
 * Created by talza on 25-Jul-17.
 */

public class Spacer extends LinearLayout {

    public Spacer(Context context) {
        super(context);
        this.setOrientation(HORIZONTAL);
        LayoutParams layoutParams = new LayoutParams(1000, 2);
        layoutParams.setMargins(300, 0, 0, 0);
        this.setLayoutParams(layoutParams);
        this.setBackgroundColor(Color.parseColor("#000000"));
    }
}
