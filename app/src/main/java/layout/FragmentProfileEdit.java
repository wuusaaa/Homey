package layout;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.project.homey.R;

import app.logic.appcomponents.User;
import app.logic.managers.DBManager;
import app.logic.managers.Services;
import callback.UpdateCallBack;


public class FragmentProfileEdit extends Fragment
{
    private boolean hasFirstNameChanged = false;
    private boolean hasLastNameChanged = false;
    private EditText editTextFirstName;
    private EditText editTextlastName;

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
        editTextFirstName = (EditText)getView().findViewById(R.id.profileEditFirstName);
        editTextlastName = (EditText)getView().findViewById(R.id.profileEditLastName);
        editTextFirstName.setText(firstName);
        editTextlastName.setText(lastName);
        setTextListeners();
    }

    private void setTextListeners()
    {
        editTextFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                hasFirstNameChanged = true;
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                hasFirstNameChanged = true;
            }

            @Override
            public void afterTextChanged(Editable editable) {
                hasFirstNameChanged = true;
            }
        });
        editTextlastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                hasLastNameChanged = true;
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                hasLastNameChanged = true;
            }

            @Override
            public void afterTextChanged(Editable editable) {
                hasLastNameChanged = true;
            }
        });
    }

    public void onSaveChangesClick(User user, Context c)
    {
        DBManager dataBaseManager = (DBManager) Services.GetService(DBManager.class);
        if (hasFirstNameChanged)
        {
            user.setName(editTextFirstName.getText().toString());
            dataBaseManager.UpdateUser(user.GetUserId(), "name", editTextFirstName.getText().toString(), new UpdateCallBack() {
                @Override
                public void onSuccess() {
                    Toast.makeText(c, "Successfully changed name", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(String errorMessage) {
                    Toast.makeText(c, "Failed to change name", Toast.LENGTH_SHORT).show();
                }
            });
            user.setName(editTextFirstName.getText().toString());
        }

        //TODO: Change last nam

    }
}
