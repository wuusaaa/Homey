package app.customcomponents;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.homey.R;

import java.util.function.Consumer;

import app.logic.appcomponents.Task;


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
        LayoutParams textViewLayoutParams = new LayoutParams(getDpSize(260), LayoutParams.WRAP_CONTENT);
        textViewLayoutParams.setMargins(getDpSize(15), getDpSize(5), getDpSize(0), getDpSize(5));
        // Group icon
        groupIcon = new ImageButton(getContext());

        LayoutParams imageButtonLayoutParams = new LayoutParams(getDpSize(100), getDpSize(100));
        groupIcon.setScaleType(ImageView.ScaleType.FIT_CENTER);
        groupIcon.setLayoutParams(imageButtonLayoutParams);
        groupIcon.setBackgroundColor(getResources().getColor(R.color.gentle_gray, null));

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profilepicturetest);
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        roundedBitmapDrawable.setCircular(true);
        groupIcon.setImageDrawable(roundedBitmapDrawable);

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

    public void SetTaskLayoutOnClick(Consumer<Task> callback) {
        this.setBackgroundResource(R.drawable.task_layout_selector);

        this.setOnClickListener(event -> {
            this.setPressed(true);
            callback.accept(task);
        });
    }

    public void setCheckBoxOnClick(Consumer<TaskLayout> checkBoxCallBack) {
        checkBox.setOnClickListener(c -> checkBoxCallBack.accept(this));
    }

    public Task getTask() {
        return task;
    }

    public int getDpSize(int size) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (size * scale + 0.5f);
    }
}
