package app.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.project.homey.R;

import layout.EmptyFragment;
import layout.FragmentAddGroup;

public class PlusActivity extends ActivityBase {

    private Button addTaskButton;
    private Button addGroupButton;
    private Fragment currentFragment;
    private boolean isChoosePicture = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plus);

        addTaskButton = (Button) findViewById(R.id.buttonAddTask);

        addTaskButton.setOnClickListener(v -> {
            Intent intent = new Intent(PlusActivity.this, AddTaskActivity.class);
            startActivity(intent);
        });

        addGroupButton = (Button) findViewById(R.id.buttonAddGroup);

        addGroupButton.setOnClickListener(v -> {
            setAddGroupFragment();
        });

        startFragment();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!isChoosePicture)
        {
            setDefaultFragment();
        }

        isChoosePicture = false;
    }

    private void setDefaultFragment()
    {
        Fragment emptyFragment = new EmptyFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.PlusActivityFragmentHolder, emptyFragment);
        transaction.commit();
        currentFragment = emptyFragment;
    }

    private void startFragment()
    {
        Fragment emptyFragment = new EmptyFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.PlusActivityFragmentHolder, emptyFragment);
        transaction.commit();
        currentFragment = emptyFragment;
    }

    private void setAddGroupFragment()
    {
        Fragment addGroupFragment = new FragmentAddGroup();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.PlusActivityFragmentHolder, addGroupFragment);
        transaction.commit();
        currentFragment = addGroupFragment;
    }

    public void onCreateGroupClicked(View view)
    {
        if (!((EditText) findViewById(R.id.editTextGroupName)).getText().toString().isEmpty())
        {
            ((FragmentAddGroup)currentFragment).onCreateGroup();
        }
    }

    public void onChooseImageClicked(View view)
    {
        isChoosePicture = true;
        ((FragmentAddGroup)currentFragment).onChooseImageClicked();
    }
}
