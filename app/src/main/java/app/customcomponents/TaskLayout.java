package app.customcomponents;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.homey.R;

import app.logic.appcomponents.Task;
import callback.GoToTaskPageCallBack;


/**
 * TODO: document your custom view class.
 */
public class TaskLayout extends LinearLayout {
    private TextView descriptionTextView;
    private TextView nameTextView;
    private CheckBox checkBox;
    private Task task;
    private ImageButton groupIcon;
    private LinearLayout taskInfo;

    public void setTask(Task task) {
        setName(task.GetName());
        setDescription(task.GetDescription());
        this.task = task;
    }

    public TaskLayout(Context context) {
        super(context);

        setOrientation(HORIZONTAL);
        LayoutParams textViewLayoutParams = new LayoutParams(1000, LayoutParams.WRAP_CONTENT);
        textViewLayoutParams.setMargins(15, 15, 0, 15);
        // Group icon
        groupIcon = new ImageButton(getContext());
        LayoutParams imageButtonLayoutParams = new LayoutParams(300, 300);
        groupIcon.setImageResource(R.drawable.profilepicturetest);
        groupIcon.setScaleType(ImageView.ScaleType.FIT_CENTER);
        groupIcon.setLayoutParams(imageButtonLayoutParams);
        GradientDrawable shape = new GradientDrawable();
        shape.setCornerRadius(500);
        groupIcon.setBackground(shape);
        // Task info
        taskInfo = new LinearLayout(getContext());
        taskInfo.setOrientation(VERTICAL);

        nameTextView = new TextView(getContext());
        nameTextView.setTextSize(20);
        nameTextView.setTypeface(null, Typeface.BOLD);
        nameTextView.setLayoutParams(textViewLayoutParams);

        descriptionTextView = new TextView(getContext());
        descriptionTextView.setTextSize(18);
        descriptionTextView.setLayoutParams(textViewLayoutParams);

        taskInfo.addView(nameTextView);
        taskInfo.addView(descriptionTextView);
        // Checkbox
        checkBox = new CheckBox(getContext());
        // Build layout
        this.addView(groupIcon);
        this.addView(taskInfo);
        this.addView(checkBox);
    }

    public String getDescription() {
        return task.GetDescription();
    }

    private void setDescription(String descriptionText) {
        this.descriptionTextView.setText(descriptionText);
    }

    private void setName(String nameText) {
        this.nameTextView.setText(nameText);
    }

    public boolean isChecked() {
        return checkBox.isChecked();
    }

    public void setCheckBox(boolean checked) {
        this.checkBox.setChecked(checked);
    }

    public void SetOnClick(GoToTaskPageCallBack callback) {
        this.setBackgroundResource(R.drawable.task_layout_selector);

        this.setOnClickListener(event ->
        {
            this.setPressed(true);
            callback.onSuccess(task);
        });
    }
}