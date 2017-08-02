package app.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.project.homey.R;

import app.logic.appcomponents.User;
import app.logic.managers.DBManager;
import app.logic.managers.Services;
import app.logic.managers.SessionManager;
import layout.FragmentProfileEdit;
import layout.FragmentProfileInfo;

public class ProfileActivity extends ActivityWithHeaderBase
{
    private User user;
    private Fragment currentFragment;
    private boolean isEditable;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }

    @Override
    protected void onStart() {
        super.onStart();
        user = getIntent().getExtras().getParcelable("user");
        isEditable = user.GetUserId().
                equals(((SessionManager) Services.GetService(SessionManager.class)).getUser().GetUserId());
        setDefaultFragment();
    }

    private void setDefaultFragment()
    {
        Fragment detailsFragment;
        detailsFragment = new FragmentProfileInfo();
        ((FragmentProfileInfo) detailsFragment).setUser(user);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.proifleActivityFragment, detailsFragment);
        transaction.commit();
        currentFragment = detailsFragment;
    }

    public void onCancelClick(View view)
    {
        setDetailsFragment();
    }

    private void setDetailsFragment()
    {
        Fragment detailsFragment;
        detailsFragment = new FragmentProfileInfo();
        ((FragmentProfileInfo) detailsFragment).setUser(user);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.proifleActivityFragment, detailsFragment);
        transaction.commit();
        currentFragment = detailsFragment;
    }

    public void onSaveChangesClick(View view)
    {
        ((FragmentProfileEdit) currentFragment).onSaveChangesClick(user, this);
        setDetailsFragment();
    }

    public void editButton_onClick(View view)
    {
        if (isEditable)
        {
            Bundle bundle = new Bundle();
            bundle.putString("firstName", user.getName());
            bundle.putString("lastName", user.getName()); //TODO: change to last name.
            Fragment editFragment;
            editFragment = new FragmentProfileEdit();
            editFragment.setArguments(bundle);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.proifleActivityFragment, editFragment);
            transaction.commit();
            currentFragment = editFragment;
        }
    }
}
