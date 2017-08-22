package app.customcomponents;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.homey.R;

import java.util.function.Consumer;

import app.logic.appcomponents.Task;
import app.logic.managers.DragManager;
import app.logic.managers.Services;

/**
 * TODO: document your custom view class.
 */
public class TaskLayout extends LinearLayout {
    private TextView descriptionTextView;
    private TextView nameTextView;
    private CheckBox checkBox;
    private Task task;
    private CircleImageButton taskIcon;
    private LinearLayout taskInfo;

    public void setTask(Task task)
    {
        setName(task.GetName());
        setDescription(task.GetDescription());
        this.task = task;
        setImage();
    }

    public TaskLayout(Context context)
    {
        super(context);

        setOrientation(HORIZONTAL);
        LayoutParams textViewLayoutParams = new LayoutParams(getDpSize(280), LayoutParams.WRAP_CONTENT);
        textViewLayoutParams.setMargins(getDpSize(10), getDpSize(20), getDpSize(0), getDpSize(0));
        LayoutParams textViewLayoutParamsdesc = new LayoutParams(getDpSize(260), LayoutParams.WRAP_CONTENT);
        textViewLayoutParamsdesc.setMargins(getDpSize(10), getDpSize(0), getDpSize(0), getDpSize(0));

        // Task icon
        taskIcon = new CircleImageButton(getContext(), R.drawable.icon_task_def);
        LayoutParams imageButtonLayoutParams = new LayoutParams(getDpSize(80), getDpSize(80));
        taskIcon.setScaleType(ImageView.ScaleType.FIT_CENTER);
        taskIcon.setLayoutParams(imageButtonLayoutParams);
        taskIcon.setBackgroundColor(getResources().getColor(R.color.gentle_gray, null));

        // Task info
        taskInfo = new LinearLayout(getContext());
        taskInfo.setOrientation(VERTICAL);

        nameTextView = new TextView(getContext());
        nameTextView.setTextSize(20);
        nameTextView.setTypeface(null, Typeface.BOLD);
        nameTextView.setLayoutParams(textViewLayoutParams);

        descriptionTextView = new TextView(getContext());
        descriptionTextView.setTextSize(18);
        descriptionTextView.setLayoutParams(textViewLayoutParamsdesc);

        taskInfo.addView(nameTextView);
        taskInfo.addView(descriptionTextView);
        // Checkbox
        checkBox = new CheckBox(getContext());
        checkBox.setPadding(getDpSize(0), getDpSize(25), getDpSize(0), getDpSize(0));
        // Build layout
        this.addView(taskIcon);
        this.addView(taskInfo);
        this.addView(checkBox);
    }

    private void setImage()
    {
        Bitmap bitMapImage = BitmapFactory.decodeByteArray(task.GetImg(), 0, task.GetImg().length);
        if (bitMapImage != null)
        {
            taskIcon.setImage(bitMapImage);
        }
        else
        {
            taskIcon.setImage(R.drawable.icon_task_def);
        }
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

    public void setDragAndDrop()
    {
        DragManager dragManager = (DragManager) Services.GetService(DragManager.class);

        taskIcon.setOnTouchListener(new OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent)
            {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN && !task.isCompleted())
                {
                    ClipData data = ClipData.newPlainText("", "");
                    DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                            view);
                    view.startDragAndDrop(data, shadowBuilder, taskIcon, 1);
                    dragManager.setMovingTask(task);
                    return true;
                }
                else
                {
                    return false;
                }
            }
        });

    }
}
