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
import layout.FragmentAddTask;

public class PlusActivity extends ActivityBase {

    private Fragment currentFragment;
    private boolean isChoosePicture = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plus);

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

    //************* Set Fragments: ****************

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

    public void setAddGroupFragment(View view)
    {
        Fragment addGroupFragment = new FragmentAddGroup();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.PlusActivityFragmentHolder, addGroupFragment);
        transaction.commit();
        currentFragment = addGroupFragment;
    }

    public  void setAddTaskFragment(View view)
    {
        Fragment addTaskFragment = new FragmentAddTask();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.PlusActivityFragmentHolder, addTaskFragment);
        transaction.commit();
        currentFragment = addTaskFragment;
    }

    //************* Set Fragments end. ************

    //************* On Click methods: *************

    public void onCreateGroupClicked(View view)
    {
        if (!((EditText) findViewById(R.id.editTextGroupName)).getText().toString().isEmpty())
        {
            ((FragmentAddGroup)currentFragment).onCreateGroup();
        }
    }

    public void onGroupChooseImageClicked(View view)
    {
        isChoosePicture = true;
        ((FragmentAddGroup)currentFragment).onChooseImageClicked();
    }

    public void onTaskChooseImageClicked(View view)
    {
        isChoosePicture = true;
        ((FragmentAddTask)currentFragment).onChooseImageClicked();
    }

    public void onAddTaskClicked(View view)
    {

        if (!((EditText) findViewById(R.id.editTextTaskName)).getText().toString().isEmpty())
        {
            ((FragmentAddTask)currentFragment).onAddTaskClick();
        }
    }

    //********** On Click methods end. ************

}
