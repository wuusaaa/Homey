package app.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.project.homey.R;

import java.util.Date;

import app.logic.appcomponents.Task;
import app.logic.managers.DBManager;
import app.logic.managers.Services;
import callback.TaskCallBack;

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
            //TODO get the correct group id
            @Override
            public void onClick(View v) {
                Task task = new Task(editTextName.getText().toString(), 10, editTextDesc.getText().toString(), editTextStatus.getText().toString(), editTextLocation.getText().toString(), 5, new Date(editTextStart.getText().toString()), new Date(editTextEnd.getText().toString()));
                ((DBManager) (Services.GetService(DBManager.class)))
                        .AddTask(editTextName.getText().toString(),
                                editTextDesc.getText().toString(), 5, 10,
                                editTextStatus.getText().toString(),
                                editTextLocation.getText().toString(),
                                new Date(editTextStart.getText().toString()),
                                new Date(editTextEnd.getText().toString()),
                                new TaskCallBack() {
                    @Override
                    public void onSuccess(Task result) {
                            Toast.makeText(AddTaskActivity.this, "Task added!", Toast.LENGTH_SHORT).show();
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
