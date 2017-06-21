package app.customcomponents;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.project.homey.R;

import java.util.ArrayList;

import app.activities.interfaces.IHasText;


/**
 * TODO: document your custom view class.
 */
public class ScrollVerticalWithItems extends ScrollView {

    GridLayout gridLayout;
    ColorStateList colorStateList1;
    ColorStateList colorStateList2;
    int screenWidth=Resources.getSystem().getDisplayMetrics().widthPixels;
    int screenHeight=Resources.getSystem().getDisplayMetrics().heightPixels;

    public ScrollVerticalWithItems(Context context) {
        super(context);

        initColorScheme();
        gridLayout = new GridLayout(context);
        this.addView(gridLayout);


    }

    public ScrollVerticalWithItems(Context context, AttributeSet attrs) {
        super(context, attrs);

        initColorScheme();
        gridLayout = new GridLayout(context);
        this.addView(gridLayout);
    }

    public ScrollVerticalWithItems(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        initColorScheme();
        gridLayout = new GridLayout(context);
        this.addView(gridLayout);
    }

    private void initColorScheme(){
        //checkbox colors scheme 1
        colorStateList1 = new ColorStateList(
                new int[][]{

                        new int[]{-android.R.attr.state_enabled}, //disabled
                        new int[]{android.R.attr.state_enabled} //enabled
                },
                new int[] {

                        Color.parseColor("#FF4081") //disabled
                        ,Color.parseColor("#3F51B5") //enabled

                }
        );
        //checkbox colors scheme 2
        colorStateList2 = new ColorStateList(
                new int[][]{

                        new int[]{-android.R.attr.state_enabled}, //disabled
                        new int[]{android.R.attr.state_enabled} //enabled
                },
                new int[] {

                        Color.parseColor("#3F51B5"), //disabled
                        Color.parseColor("#FF4081") //enabled

                }
        );
    }

    public <T extends IHasText> void SetUserTasks(ArrayList<T> userTasks) {

        gridLayout.removeAllViews();
        //TODO: change the for to foreach with stream.
        for (int i = 0; i < userTasks.size(); i++) {
            AppCompatCheckBox checkBox = new AppCompatCheckBox(this.getContext());
            Button gotoTaskButton=new Button(this.getContext());
            gotoTaskButton.setLineSpacing(1f,1f);
            TextView taskNameTextView = new TextView(this.getContext());
            taskNameTextView.setText(userTasks.get(i).GetName());
            taskNameTextView.setLayoutParams(new GridLayout.LayoutParams(GridLayout.spec(i), GridLayout.spec(1)));
            TextView taskDescTextView = new TextView(this.getContext());
            taskDescTextView.setText(userTasks.get(i).GetDescription());
            taskDescTextView.setLayoutParams(new GridLayout.LayoutParams(GridLayout.spec(i), GridLayout.spec(2)));
            checkBox.setLayoutParams(new GridLayout.LayoutParams(GridLayout.spec(i), GridLayout.spec(0)));
            //name layout
            taskNameTextView.setWidth((int)(gridLayout.getWidth()*0.2));

            taskNameTextView.setHeight((int)(screenHeight*0.1));
            if(i%2==0) {
                taskNameTextView.setBackgroundResource(R.color.colorPrimary);
            }
            else {
                taskNameTextView.setBackgroundResource(R.color.colorAccent);
            }
            taskNameTextView.setTextColor(Color.WHITE);
//            FrameLayout.LayoutParams layoutParamsName = new FrameLayout.LayoutParams(new GridLayout.LayoutParams(GridLayout.spec(i), GridLayout.spec(0)));
//            layoutParamsName.leftMargin = 15;
//            layoutParamsName.rightMargin = 15;
//            layoutParamsName.topMargin = 15;
//            layoutParamsName.bottomMargin = 15;
//            taskNameTextView.setLayoutParams(layoutParamsName);

            //checkbox layout

            checkBox.setWidth((int)(gridLayout.getWidth()*0.1));
            checkBox.setHeight((int)(screenHeight*0.1));
            if(i%2==0) {
                checkBox.setSupportButtonTintList(colorStateList2);
            }
            else {
                checkBox.setSupportButtonTintList(colorStateList1);
            }

            //description layout
            taskDescTextView.setWidth((int)(gridLayout.getWidth()*0.65));
            taskDescTextView.setHeight((int)(screenHeight*0.1));
            if(i%2==0) {
                taskDescTextView.setBackgroundResource(R.color.colorAccent);
            }
            else {
                taskDescTextView.setBackgroundResource(R.color.colorPrimary);
            }
            taskDescTextView.setTextColor(Color.WHITE);
//            FrameLayout.LayoutParams layoutParamsDesc = new FrameLayout.LayoutParams(new GridLayout.LayoutParams(GridLayout.spec(i), GridLayout.spec(0)));
//            layoutParamsDesc.leftMargin = 25;
//            layoutParamsDesc.rightMargin = 15;
//            layoutParamsDesc.topMargin = 15;
//            layoutParamsDesc.bottomMargin = 15;
//            taskNameTextView.setLayoutParams(layoutParamsDesc);

            //general layout
            gridLayout.addView(checkBox);
            gridLayout.addView(taskNameTextView);
            gridLayout.addView(taskDescTextView);

        }
    }
}
