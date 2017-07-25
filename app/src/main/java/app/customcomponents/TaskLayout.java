package app.customcomponents;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import app.logic.appcomponents.Task;
import callback.GoToTaskPageCallBack;


/**
 * TODO: document your custom view class.
 */
public class TaskLayout extends LinearLayout {
    private TextView descriptionTextView;
    private CheckBox checkBox;

    public TaskLayout(Context context) {
        super(context);
        setOrientation(HORIZONTAL);
        LayoutParams textViewLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        textViewLayoutParams.setMargins(15, 15, 0, 15);

        descriptionTextView = new TextView(getContext());
        descriptionTextView.setTextSize(36);
        descriptionTextView.setLayoutParams(textViewLayoutParams);

        checkBox = new CheckBox(getContext());

        this.addView(checkBox);
        this.addView(descriptionTextView);
    }

    public String getDescriptionText() {
        return descriptionTextView.getText().toString();
    }

    public boolean getCheckBox() {
        return checkBox.isChecked();
    }

    public void setDescription(String descriptionText) {
        this.descriptionTextView.setText(descriptionText);
    }

    public void setCheckBox(boolean checked) {
        this.checkBox.setChecked(checked);
    }

    public void SetOnClick(GoToTaskPageCallBack callback, Task task) {
        descriptionTextView.setOnClickListener(event ->
        {
            callback.onSuccess(task);
        });
    }
}