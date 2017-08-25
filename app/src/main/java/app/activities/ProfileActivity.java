package app.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.project.homey.R;

import app.customcomponents.CircleImageButton;
import app.logic.appcomponents.User;
import app.logic.managers.ActivityChangeManager;
import app.logic.managers.DBManager;
import app.logic.managers.Services;
import app.logic.managers.SessionManager;
import callback.UserCallBack;
import layout.FragmentProfileEdit;
import layout.FragmentProfileInfo;

public class ProfileActivity extends ActivityWithHeaderBase
{
    private User user;
    private Fragment currentFragment;
    private boolean isEditable;
    private boolean isChoosingPicture = false;
    private CircleImageButton circleImageProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        circleImageProfile = (CircleImageButton) findViewById(R.id.imageButtonUsersProfilePicture);
        circleImageProfile.setScaleType(ImageView.ScaleType.FIT_CENTER);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        if (!isChoosingPicture)
        {
            isChoosingPicture = false;
            user = getIntent().getExtras().getParcelable("user");
            isEditable = user.GetUserId().
                    equals(((SessionManager) Services.GetService(SessionManager.class)).getUser().GetUserId());
            setDefaultFragment();
        }

        setImage();
    }

    private void setImage()
    {
        circleImageProfile.setImageBytes(user.GetImage(), R.mipmap.ic_profile_default);
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
        circleImageProfile.setVisibility(View.VISIBLE);
    }

    public void onSaveChangesClick(View view)
    {
        ((FragmentProfileEdit) currentFragment).onSaveChangesClick(user, this);
        setDetailsFragment();
        setImage();
    }

    public void editButton_onClick(View view)
    {
        if (isEditable)
        {
            circleImageProfile.setVisibility(View.INVISIBLE);
            Bundle bundle = new Bundle();
            bundle.putString("firstName", user.GetName());
            bundle.putString("lastName", user.GetName()); //TODO: change to last name.
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

    public void onProfileChoosePictureClick(View view)
    {
        isChoosingPicture = true;
        ((FragmentProfileEdit)currentFragment).onChoosePictureClick();
    }

    public void onLogoutClick(View view)
    {
        ((ActivityChangeManager) Services.GetService(ActivityChangeManager.class)).SetLogOutActivity(this);
    }
}
