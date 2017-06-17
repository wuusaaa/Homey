package app.customcomponents;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import app.activities.interfaces.IHasImage;
import app.activities.interfaces.IHasText;


/**
 * TODO: document your custom view class.
 */
public class ScrollHorizontalWithItems extends HorizontalScrollView {

    private final LinearLayout linearLayout = new LinearLayout(this.getContext());
    private TextView emptyMessage;

    public ScrollHorizontalWithItems(Context context) {
        super(context);

        this.addView(linearLayout);

        emptyMessage = new TextView(context);
        emptyMessage.setText("EmptyMessage");
        this.addView(emptyMessage);
    }

    public ScrollHorizontalWithItems(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.addView(linearLayout);
    }

    public ScrollHorizontalWithItems(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        this.addView(linearLayout);
    }

    public <T extends IHasText & IHasImage> void SetUserGroups(ArrayList<T> userGroups, @LinearLayoutCompat.OrientationMode int orientation) {
        linearLayout.removeAllViews();
        linearLayout.setOrientation(orientation);
        //TODO: change the for to foreach with stream.
        for (int i = 0; i < userGroups.size(); i++) {
            Button button = new Button(this.getContext());
            button.setText(userGroups.get(i).GetName());
            button.setLayoutParams(new LinearLayout.LayoutParams(400, 400));
            linearLayout.addView(button);
        }
    }

    public void SetEmptyMessage(String message) {

    }

    public void HideEmptyMessage() {

    }

    public void ShowEmptyMessage() {

    }
}
