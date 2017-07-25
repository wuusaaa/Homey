package app.customcomponents;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
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

    public ScrollHorizontalWithItems(Context context) {
        super(context);

        this.addView(linearLayout);
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
            LinearLayout verticalLinearLayout = new LinearLayout(this.getContext());
            ImageButton imageButton = new ImageButton(this.getContext());
            TextView textView = new TextView(this.getContext());

            verticalLinearLayout.setOrientation(LinearLayout.VERTICAL);

            LayoutParams layoutParamsImageButton = new LayoutParams(new LinearLayout.LayoutParams(300, 300));
            setLayoutParamsMargin(layoutParamsImageButton);
            imageButton.setLayoutParams(layoutParamsImageButton);

            LayoutParams layoutParamsTextView = new LayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            setLayoutParamsMargin(layoutParamsTextView);
            textView.setLayoutParams(layoutParamsTextView);

            textView.setText(userGroups.get(i).GetName());
            textView.setTextAlignment(TEXT_ALIGNMENT_CENTER);

            GradientDrawable shape = new GradientDrawable();
            shape.setCornerRadius(500);

            if (i % 2 == 0) {
                shape.setColor(Color.parseColor("#D8EAF6"));
            } else {
                shape.setColor(Color.parseColor("#EAEAEA"));
            }

            imageButton.setBackground(shape);

            textView.setTextColor(Color.BLACK);
            final int finalI = i;
            imageButton.setOnClickListener(v -> callBack.onSuccess((Group) userGroups.get(finalI)));

            verticalLinearLayout.addView(imageButton);
            verticalLinearLayout.addView(textView);
            linearLayout.addView(verticalLinearLayout);
        }
    }

    private void setLayoutParamsMargin(LayoutParams layoutParamsImageButton) {
        layoutParamsImageButton.leftMargin = 15;
        layoutParamsImageButton.rightMargin = 15;
        layoutParamsImageButton.topMargin = 15;
        layoutParamsImageButton.bottomMargin = 15;
    }
}
