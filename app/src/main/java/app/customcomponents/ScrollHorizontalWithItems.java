package app.customcomponents;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import app.activities.interfaces.IHasImage;
import app.activities.interfaces.IHasText;


/**
 * TODO: document your custom view class.
 */
public class ScrollHorizontalWithItems extends HorizontalScrollView {
    public ScrollHorizontalWithItems(Context context) {
        super(context);

    }

    public ScrollHorizontalWithItems(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollHorizontalWithItems(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public <T extends IHasText & IHasImage> void SetUserGroups(ArrayList<T> userGroups, @LinearLayoutCompat.OrientationMode int orientation) {
        final LinearLayout linearLayout = new LinearLayout(this.getContext());
        linearLayout.setOrientation(orientation);
        this.detachAllViewsFromParent();
        this.addView(linearLayout);
        //TODO: change the for to foreach with stream.
        for (int i = 0; i < userGroups.size(); i++) {
            Button button = new Button(this.getContext());
            button.setText(userGroups.get(i).GetName());
            button.setLayoutParams(new LinearLayout.LayoutParams(400, 400));
            linearLayout.addView(button);
        }
    }
}
