package app.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.project.homey.R;

import layout.EmptyFragment;
import layout.FragmentAddGroup;
import layout.FragmentAddMember;
import layout.FragmentAddTask;

public class PlusActivity extends ActivityBase {
    public static final int NONE = 0;
    public static final int ADD_GROUP = 1;
    public static final int ADD_TASK = 2;
    public static final int ADD_MEMBER = 3;

    private Fragment currentFragment;
    private boolean isChoosePicture = false;
    private boolean isFirst = true;
    private String defaultGroupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plus);

        setDefaultFragment();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!isChoosePicture) {
            setDefaultFragment();
        }

        isChoosePicture = false;
    }

    //************* Set Fragments: ****************

    private void setDefaultFragment() {
        Fragment fragment;
        Bundle bundle = getIntent().getExtras();
        int fragmentToDisplay = bundle.getInt("fragmentNumber");

        switch (fragmentToDisplay) {
            case NONE:
                fragment = new EmptyFragment();
                //nothing to do, default fragment already placed.
                break;
            case ADD_GROUP:
                fragment = new FragmentAddGroup();
                break;
            case ADD_TASK:
                fragment = new FragmentAddTask();
                //Get here when you press "plus" when you are in a group page.
                //TODO: insert group name inside.
                break;
            case ADD_MEMBER:
                fragment = new FragmentAddMember();
                ((FragmentAddMember) fragment).SetDefaultGroupName(bundle.getString("groupName"));
                break;
            default:
                fragment = new EmptyFragment();
                //nothing to do, default fragment already placed.
                break;
        }

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.PlusActivityFragmentHolder, fragment);
        transaction.commit();
        currentFragment = fragment;
    }

    public void setAddGroupFragment(View view) {
        Fragment addGroupFragment = new FragmentAddGroup();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.PlusActivityFragmentHolder, addGroupFragment);
        transaction.commit();
        currentFragment = addGroupFragment;
    }

    public void setAddTaskFragment(View view) {
        Fragment addTaskFragment = new FragmentAddTask();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.PlusActivityFragmentHolder, addTaskFragment);
        transaction.commit();
        currentFragment = addTaskFragment;
    }

    public void setAddMemberFragment(View view) {
        Fragment addMemberFragment = new FragmentAddMember();
        ((FragmentAddMember) addMemberFragment).SetDefaultGroupName(defaultGroupName);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.PlusActivityFragmentHolder, addMemberFragment);
        transaction.commit();
        currentFragment = addMemberFragment;
    }

    //************* Set Fragments end. ************

    //************* On Click methods: *************

    public void onCreateGroupClicked(View view) {
        if (!((EditText) findViewById(R.id.editTextGroupName)).getText().toString().isEmpty()) {
            ((FragmentAddGroup) currentFragment).onCreateGroup();
        }
    }

    public void onGroupChooseImageClicked(View view) {
        isChoosePicture = true;
        ((FragmentAddGroup) currentFragment).onChooseImageClicked();
    }

    public void onTaskChooseImageClicked(View view) {
        isChoosePicture = true;
        ((FragmentAddTask) currentFragment).onChooseImageClicked();
    }

    public void onAddTaskClicked(View view) {

        ((FragmentAddTask) currentFragment).onAddTaskClick();
    }

    public void onAddMemberClicked(View view) {
        ((FragmentAddMember) currentFragment).onAddMemberClicked();
    }

    //********** On Click methods end. ************

}
