package app.customcomponents;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;


public class TaskLayout extends View {

    private TextView descriptionTextView;
    private CheckBox checkBox;

    public TaskLayout(Context context) {
        super(context);
        descriptionTextView = new TextView(this.getContext());
        checkBox = new CheckBox(this.getContext());
    }

    public void setDescription(String description) {
        descriptionTextView.setText(description);
    }

    public boolean isChecked() {
        return checkBox.isChecked();
    }


}
