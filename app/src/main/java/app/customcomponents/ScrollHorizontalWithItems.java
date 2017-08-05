package app.customcomponents;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.homey.R;

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
            CircleImageButton groupIcon = new CircleImageButton(this.getContext(), item.GetImage(), R.mipmap.ic_group_default);
            TextView textView = new TextView(this.getContext());

            verticalLinearLayout.setOrientation(LinearLayout.VERTICAL);

            textView.setLayoutParams(layoutParamsTextView);
            textView.setText(item.GetName());
            textView.setTextAlignment(TEXT_ALIGNMENT_CENTER);
            textView.setTextColor(Color.BLACK);

            groupIcon.setOnClickListener(v -> callBack.onSuccess(item));
            LinearLayout.LayoutParams imageButtonLayoutParams = new LinearLayout.LayoutParams(getDpSize(100), getDpSize(100));
            groupIcon.setScaleType(ImageView.ScaleType.FIT_CENTER);
            groupIcon.setLayoutParams(imageButtonLayoutParams);
            groupIcon.setBackgroundColor(getResources().getColor(R.color.gentle_gray, null));

            verticalLinearLayout.addView(groupIcon);
            verticalLinearLayout.addView(textView);
            linearLayout.addView(verticalLinearLayout);
        }


    }

    public int getDpSize(int size)
    {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (size * scale + 0.5f);
    }

    private void setLayoutParamsMargin(LayoutParams layoutParams) {
        layoutParams.leftMargin = 15;
        layoutParams.rightMargin = 15;
        layoutParams.topMargin = 15;
        layoutParams.bottomMargin = 15;
    }
}
