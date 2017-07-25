package app.customcomponents;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * Created by talza on 23-Jul-17.
 */

public class Separator extends LinearLayout {


    public Separator(Context context) {
        super(context);
        this.setMinimumHeight(1);
        LayoutParams layoutParams = new LayoutParams(MATCH_PARENT, 2);
        this.setLayoutParams(layoutParams);
        this.setBackgroundColor(Color.parseColor("#000000"));
    }


}
