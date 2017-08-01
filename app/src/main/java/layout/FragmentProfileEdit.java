package layout;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.project.homey.R;


public class FragmentProfileEdit extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_profile_edit, container, false);
    }

    @Override
    public void onStart()
    {
        super.onStart();

        String firstName = getArguments().getString("firstName");
        String lastName = getArguments().getString("lastName");
        ((EditText)getView().findViewById(R.id.profileEditFirstName)).setText(firstName);
        ((EditText)getView().findViewById(R.id.profileEditLastName)).setText(lastName);
    }

    public void onSaveChangesClick(View view)
    {

    }
}
