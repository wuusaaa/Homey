package app.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;

import com.project.homey.R;

import layout.FragmentProfileEdit;
import layout.FragmentProfileInfo;

public class ProfileActivity extends ActivityWithHeaderBase {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setDefaultFragment();
        setPageInfo();
    }

    private void setDefaultFragment()
    {
        Fragment detailsFragment;
        detailsFragment = new FragmentProfileInfo();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.proifleActivityFragment, detailsFragment);
        transaction.commit();
    }

    public void onCancelClick(View view)
    {
        Fragment detailsFragment;
        detailsFragment = new FragmentProfileInfo();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.proifleActivityFragment, detailsFragment);
        transaction.commit();
    }

    private void setPageInfo()
    {

    }

    public void editButton_onClick(View view)
    {
        Bundle bundle = new Bundle();
        bundle.putString("firstName", "Ben");
        bundle.putString("lastName", "Rosencveig");
        Fragment editFragment;
        editFragment = new FragmentProfileEdit();
        editFragment.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.proifleActivityFragment, editFragment);
        transaction.commit();
    }
}
