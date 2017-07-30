package app.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.project.homey.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import app.customcomponents.HomeyProgressDialog;
import app.enums.TaskStatus;
import app.logic.appcomponents.Group;
import app.logic.appcomponents.Task;
import app.logic.managers.DBManager;
import app.logic.managers.GroupManager;
import app.logic.managers.Services;
import app.logic.managers.SessionManager;
import callback.GroupsCallBack;
import callback.TaskCallBack;

public class AddTaskActivity extends ActivityWithHeaderBase {

    EditText editTextName;
    EditText editTextDesc;
    EditText editTextLocation;
    EditText editTextStatus;
    EditText editTextStart;
    EditText editTextEnd;
    Button buttonAddTask;
    private Spinner dropdown;
    private HomeyProgressDialog pDialog;
    private ArrayList<Group> userGroups;
    private int selectedGroupId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        // Progress dialog
        pDialog = new HomeyProgressDialog(this);

        dropdown = (Spinner) findViewById(R.id.spinnerTaskGroup);

        Context context = this;
        List<String> items = new ArrayList<>();
        pDialog.showDialog();
        ((GroupManager) (Services.GetService(GroupManager.class))).GetUserGroups(new GroupsCallBack() {
            @Override
            public void onSuccess(ArrayList<Group> groups) {
                userGroups = groups;
                for (Group group : groups) {
                    items.add(group.GetName());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, items);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dropdown.setAdapter(adapter);
                dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        getSelectedGroupId((String) parent.getItemAtPosition(position));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // TODO Auto-generated method stub
                    }
                });
                pDialog.hideDialog();
            }

            @Override
            public void onFailure(String error) {
                pDialog.hideDialog();
                //TODO handle connection error
            }
        });

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        dropdown.setAdapter(adapter);
//        dropdown.setOnItemSelectedListener(this);
        editTextName = (EditText) findViewById(R.id.editTextTaskName);
        editTextDesc = (EditText) findViewById(R.id.editTextTaskDesc);
        editTextLocation = (EditText) findViewById(R.id.editTextTaskLocation);
        editTextStart = (EditText) findViewById(R.id.editTextTaskStartDate);
        editTextEnd = (EditText) findViewById(R.id.editTextTaskEndDate);
        buttonAddTask = (Button) findViewById(R.id.buttonAddTaskToDB);
        String userId = ((SessionManager) (Services.GetService(SessionManager.class))).getUser().GetUserId();
        buttonAddTask.setOnClickListener(v -> ((DBManager) (Services.GetService(DBManager.class)))
                .AddTask(editTextName.getText().toString(),
                        editTextDesc.getText().toString(), userId, selectedGroupId,
                        TaskStatus.INCOMPLETE.value(),
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
                        }));

    }


    private void getSelectedGroupId(String GroupName) {
        for (Group group : userGroups) {
            if (group.GetName().equals(GroupName)) {
                selectedGroupId = Integer.parseInt(group.GetId());
            }
        }
    }
}
