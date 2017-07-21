package app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.project.homey.R;

public class PlusActivity extends ActivityBase {

    Button addTaskButton;
    Button addGroupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plus);

        addTaskButton = (Button) findViewById(R.id.buttonAddTask);

        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlusActivity.this, AddTaskActivity.class);
                startActivity(intent);
            }
        });

        addGroupButton = (Button) findViewById(R.id.buttonAddGroup);

        addGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlusActivity.this, AddGroupActivity.class);
                startActivity(intent);
            }
        });
    }
}
