package app.customcomponents;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

import app.activities.interfaces.IHasText;


/**
 * TODO: document your custom view class.
 */
public class ScrollVerticalWithItems extends ScrollView {

    public ScrollVerticalWithItems(Context context) {
        super(context);
    }

    public ScrollVerticalWithItems(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollVerticalWithItems(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public <T extends IHasText> void SetUserTasks(ArrayList<T> userTasks) {

        GridLayout gridLayout = new GridLayout(this.getContext());
        this.addView(gridLayout);
        //TODO: change the for to foreach with stream.
        for (int i = 0; i < userTasks.size(); i++) {
            CheckBox checkBox = new CheckBox(this.getContext());
            TextView textView = new TextView(this.getContext());
            textView.setText(userTasks.get(i).GetName());
            textView.setLayoutParams(new GridLayout.LayoutParams(GridLayout.spec(i), GridLayout.spec(1)));
            checkBox.setLayoutParams(new GridLayout.LayoutParams(GridLayout.spec(i), GridLayout.spec(0)));
            gridLayout.addView(textView);
            gridLayout.addView(checkBox);
        }
    }
}
