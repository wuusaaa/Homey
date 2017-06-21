package app.customcomponents;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.homey.R;

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

            FrameLayout.LayoutParams layoutParamsName = new FrameLayout.LayoutParams(new LinearLayout.LayoutParams(300, 300));
            layoutParamsName.leftMargin = 15;
            layoutParamsName.rightMargin = 15;
            layoutParamsName.topMargin = 15;
            layoutParamsName.bottomMargin = 15;
            button.setLayoutParams(layoutParamsName);

            if(i%2==0) {
                button.setBackgroundResource(R.color.colorPrimary);
            }
            else {
                button.setBackgroundResource(R.color.colorAccent);
            }
            button.setTextColor(Color.WHITE);
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
