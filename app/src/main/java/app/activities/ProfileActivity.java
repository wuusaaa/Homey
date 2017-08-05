package app.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.project.homey.R;

import app.logic.appcomponents.User;
import app.logic.managers.Services;
import app.logic.managers.SessionManager;
import layout.FragmentProfileEdit;
import layout.FragmentProfileInfo;

public class ProfileActivity extends ActivityWithHeaderBase
{
    private User user;
    private Fragment currentFragment;
    private boolean isEditable;
    private boolean isChoosingPicture = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }

    @Override
    protected void onStart() {
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
        Bitmap bitmap = BitmapFactory.decodeByteArray(user.GetImage(), 0 , user.GetImage().length);
        if (bitmap != null)
        {
            ((ImageView) findViewById(R.id.imageButtonUsersProfilePicture)).setImageBitmap(bitmap);
        }
        else
        {
            ((ImageView) findViewById(R.id.imageButtonUsersProfilePicture)).setImageResource(R.mipmap.ic_profile_default);
        }
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
        setImage();
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

    public void onProfileChoosePictureClick(View view)
    {
        isChoosingPicture = true;
        ((FragmentProfileEdit)currentFragment).onChoosePictureClick();
    }
}
