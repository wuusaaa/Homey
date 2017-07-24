package app.customcomponents;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * TODO: document your custom view class.
 */
public class TaskLayout extends LinearLayout {
    private TextView descriptionTextView;
    private CheckBox checkBox;

    public TaskLayout(Context context) {
        super(context);
        setOrientation(HORIZONTAL);
        descriptionTextView = new TextView(getContext());
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
}
