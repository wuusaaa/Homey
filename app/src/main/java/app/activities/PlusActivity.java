package app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.project.homey.R;

public class PlusActivity extends AppCompatActivity {

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
    }
}
