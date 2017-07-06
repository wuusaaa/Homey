package app.activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.project.homey.R;

import app.logic.appcomponents.Group;

/**
 * Created by Raz on 7/6/2017.
 */

public class GroupPageActivity extends AppCompatActivity {
    private Group group;
    private TextView groupName;
    private TextView groupDescription;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_page);
        Bundle b = this.getIntent().getExtras();
        if (b != null)
            group = b.getParcelable("group");
        groupDescription = (TextView) findViewById(R.id.groupDescription);
        groupName = (TextView) findViewById(R.id.groupName);
        groupName.setText(group.GetName());
        groupDescription.setText(group.GetDescription());
    }
}
