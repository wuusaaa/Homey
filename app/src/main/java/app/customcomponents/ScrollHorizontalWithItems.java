package app.customcomponents;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import app.activities.interfaces.IHasImage;
import app.activities.interfaces.IHasText;
import app.logic.appcomponents.Group;
import callback.GotoGroupPageCallBack;


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

    public <T extends IHasText & IHasImage> void SetUserGroups(ArrayList<T> userGroups, @LinearLayoutCompat.OrientationMode int orientation, GotoGroupPageCallBack callBack) {
        linearLayout.removeAllViews();
        linearLayout.setOrientation(orientation);

        //TODO: change the for to foreach with stream.
        for (int i = 0; i < userGroups.size(); i++) {
            Button button = new Button(this.getContext());
            button.setText(userGroups.get(i).GetName());
            button.setLayoutParams(new LinearLayout.LayoutParams(400, 400));

            LayoutParams layoutParamsName = new LayoutParams(new LinearLayout.LayoutParams(300, 300));
            layoutParamsName.leftMargin = 15;
            layoutParamsName.rightMargin = 15;
            layoutParamsName.topMargin = 15;
            layoutParamsName.bottomMargin = 15;
            button.setLayoutParams(layoutParamsName);
            GradientDrawable shape =  new GradientDrawable();
            shape.setCornerRadius( 500 );
            if(i%2==0) {
                shape.setColor(Color.parseColor("#D8EAF6"));
            }
            else {
                shape.setColor(Color.parseColor("#EAEAEA"));
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                button.setBackground(shape);
            }
            button.setTextColor(Color.WHITE);
            final int finalI = i;
            button.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                   callBack.onSuccess((Group)userGroups.get(finalI));
                }
            });
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
