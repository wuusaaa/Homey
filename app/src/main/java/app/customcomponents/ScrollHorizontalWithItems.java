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
import java.util.concurrent.atomic.AtomicInteger;

import app.activities.interfaces.IHasImage;
import app.activities.interfaces.IHasText;
import callback.GotoGroupPageCallBack;

public class ScrollHorizontalWithItems extends HorizontalScrollView {

    private final LinearLayout linearLayout;

    public ScrollHorizontalWithItems(Context context) {
        super(context);

        linearLayout = new LinearLayout(context);
        this.addView(linearLayout);
    }

    public ScrollHorizontalWithItems(Context context, AttributeSet attrs) {
        super(context, attrs);

        linearLayout = new LinearLayout(context);
        this.addView(linearLayout);
    }

    public ScrollHorizontalWithItems(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        linearLayout = new LinearLayout(context);
        this.addView(linearLayout);
    }

    public <T extends IHasText & IHasImage> void SetScrollerItems(ArrayList<T> items, @LinearLayoutCompat.OrientationMode int orientation, GotoGroupPageCallBack callBack) {
        linearLayout.removeAllViews();

        linearLayout.setOrientation(orientation);
        LayoutParams layoutParamsTextView = new LayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        LayoutParams layoutParamsImageButton = new LayoutParams(new LinearLayout.LayoutParams(300, 300));
        AtomicInteger i = new AtomicInteger(0);

        setLayoutParamsMargin(layoutParamsImageButton);
        setLayoutParamsMargin(layoutParamsTextView);

        for (T item : items) {
            LinearLayout verticalLinearLayout = new LinearLayout(this.getContext());
            ImageButton imageButton = new ImageButton(this.getContext());
            TextView textView = new TextView(this.getContext());

            verticalLinearLayout.setOrientation(LinearLayout.VERTICAL);

            textView.setLayoutParams(layoutParamsTextView);
            textView.setText(item.GetName());
            textView.setTextAlignment(TEXT_ALIGNMENT_CENTER);
            textView.setTextColor(Color.BLACK);

            GradientDrawable shape = new GradientDrawable();
            shape.setCornerRadius(500);

            if (i.getAndIncrement() % 2 == 0) {
                shape.setColor(Color.parseColor("#D8EAF6"));
            } else {
                shape.setColor(Color.parseColor("#EAEAEA"));
            }

            imageButton.setLayoutParams(layoutParamsImageButton);
            imageButton.setBackground(shape);
            imageButton.setOnClickListener(v -> callBack.onSuccess(item));

            verticalLinearLayout.addView(imageButton);
            verticalLinearLayout.addView(textView);
            linearLayout.addView(verticalLinearLayout);
        }


    }

    private void setLayoutParamsMargin(LayoutParams layoutParams) {
        layoutParams.leftMargin = 15;
        layoutParams.rightMargin = 15;
        layoutParams.topMargin = 15;
        layoutParams.bottomMargin = 15;
    }
}
