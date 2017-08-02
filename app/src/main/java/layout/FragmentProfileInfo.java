package layout;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.homey.R;

import app.logic.appcomponents.User;


public class FragmentProfileInfo extends Fragment {
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_info, container, false);
    }

    @Override
    public void onStart()
    {
        super.onStart();

        ((TextView) getView().findViewById(R.id.textViewFirstNameLabel)).setText(user.GetName());
        //TODO: change to last name.
        ((TextView) getView().findViewById(R.id.textViewLastNameLabel)).setText(user.GetName());
        ((TextView) getView().findViewById(R.id.textViewEmailLabel)).setText(user.getEmail());

        //TODO: set #Completed tasks and #Active tasks
        //((TextView) getView().findViewById(R.id.textViewCompletedTasks)).setText(#COMPLETED);
        //((TextView) getView().findViewById(R.id.textViewLastNameLabel)).setText(#textViewActiveTasks);
    }

    public void onSavedChangesClick()
    {

    }
}