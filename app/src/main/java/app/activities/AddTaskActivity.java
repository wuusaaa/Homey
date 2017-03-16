package app.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.project.homey.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import app.logic.managers.DBManager;
import app.task.Task;
import callback.ServerCallBack;

public class AddTaskActivity extends AppCompatActivity {

    EditText editTextName;
    EditText editTextDesc;
    EditText editTextLocation;
    EditText editTextStatus;
    EditText editTextStart;
    EditText editTextEnd;
    Button buttonAddTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        editTextName = (EditText) findViewById(R.id.editTextTaskName);
        editTextDesc = (EditText) findViewById(R.id.editTextTaskDesc);
        editTextLocation = (EditText) findViewById(R.id.editTextTaskLocation);
        editTextStatus = (EditText) findViewById(R.id.editTextTaskStatus);
        editTextStart = (EditText) findViewById(R.id.editTextTaskStartDate);
        editTextEnd = (EditText) findViewById(R.id.editTextTaskEndDate);
        buttonAddTask = (Button) findViewById(R.id.buttonAddTaskToDB);
        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task task = new Task(editTextName.getText().toString(), editTextDesc.getText().toString(), editTextStatus.getText().toString(), editTextLocation.getText().toString(), 5, new Date(editTextStart.getText().toString()), new Date(editTextEnd.getText().toString()));
                new DBManager().AddTask(task, new ServerCallBack() {
                    @Override
                    public void onSuccess(JSONObject result) {
                        try {
                            Toast.makeText(AddTaskActivity.this, result.getString("res").toString(), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(String result) {
                        Toast.makeText(AddTaskActivity.this, result, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}
