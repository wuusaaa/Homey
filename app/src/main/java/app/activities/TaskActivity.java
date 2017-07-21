package app.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.project.homey.R;

import app.customcomponents.HomeyProgressDialog;
import app.customcomponents.ScrollVerticalWithItems;


public class TaskActivity extends AppCompatActivity {

    private HomeyProgressDialog pDialog;
    private LinearLayout tasksHolderLayout;
    private ScrollVerticalWithItems scrollVerticalWithItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        scrollVerticalWithItems = (ScrollVerticalWithItems) findViewById(R.id.taskActivityTasksHolder);
        pDialog = new HomeyProgressDialog(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        loadTaskInformation();
    }

    private void loadTaskInformation() {
        pDialog.showDialog();
    }
}
