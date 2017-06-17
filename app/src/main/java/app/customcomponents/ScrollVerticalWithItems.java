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

    GridLayout gridLayout;

    public ScrollVerticalWithItems(Context context) {
        super(context);

        gridLayout = new GridLayout(context);
        this.addView(gridLayout);
    }

    public ScrollVerticalWithItems(Context context, AttributeSet attrs) {
        super(context, attrs);

        gridLayout = new GridLayout(context);
        this.addView(gridLayout);
    }

    public ScrollVerticalWithItems(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        gridLayout = new GridLayout(context);
        this.addView(gridLayout);
    }

    public <T extends IHasText> void SetUserTasks(ArrayList<T> userTasks) {

        gridLayout.removeAllViews();
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
