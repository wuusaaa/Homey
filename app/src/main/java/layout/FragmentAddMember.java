package layout;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.project.homey.R;

import java.util.ArrayList;
import java.util.List;

import app.customcomponents.CircleImageButton;
import app.customcomponents.HomeyProgressDialog;
import app.logic.appcomponents.Group;
import app.logic.appcomponents.User;
import app.logic.managers.DBManager;
import app.logic.managers.Services;
import app.logic.managers.SessionManager;
import callback.GroupsCallBack;
import callback.UpdateCallBack;

public class FragmentAddMember extends Fragment
{
    private EditText editTextEmail;
    private Spinner groupSpinner;
    private CircleImageButton selectedGroupImage;
    private Button buttonAddMember;
    private ArrayList<Group> myGroups;
    private Group selectedGroup = null;
    private HomeyProgressDialog pDialog;
    private DBManager dbManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_add_member, container, false);
    }

    @Override
    public void onStart()
    {
        super.onStart();

        pDialog = new HomeyProgressDialog(this.getContext());
        dbManager = (DBManager) Services.GetService(DBManager.class);

        editTextEmail = (EditText) getView().findViewById(R.id.editTextEmail);
        groupSpinner = (Spinner) getView().findViewById(R.id.spinnerAddMemberGroups);
        selectedGroupImage = (CircleImageButton) getView().findViewById(R.id.imageViewAddMember);
        buttonAddMember = (Button) getView().findViewById(R.id.buttonAddMemberFragment);

        selectedGroupImage.setImage(R.mipmap.ic_group_default);
        selectedGroupImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
        setSpinnerItems();

        groupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                selectedGroup = getSelectedGroup((String) adapterView.getItemAtPosition(groupSpinner.getSelectedItemPosition()));
                setGroupImage();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {
                selectedGroup = null;
            }
        });
    }

    private void setGroupImage()
    {
        if (selectedGroup != null)
        {
            selectedGroupImage.setImageBytes(selectedGroup.GetImage(), R.mipmap.ic_group_default);
        }
        else
        {
            selectedGroupImage.setImage(R.mipmap.ic_group_default);
        }
    }

    private Group getSelectedGroup(String groupName)
    {
        for (int i=0; i< myGroups.size(); i++)
        {
            if (myGroups.get(i).GetName().equals(groupName))
            {
                return myGroups.get(i);
            }
        }

        return null;
    }

    private void setSpinnerItems()
    {
        User self = ((SessionManager) Services.GetService(SessionManager.class)).getUser();
        pDialog.showDialog();
        dbManager.GetGroupsThatUserIsAdmin(self.GetUserId(),
                new GroupsCallBack() {
            @Override
            public void onSuccess(ArrayList<Group> groups)
            {
                myGroups = groups;
                List<String> items = new ArrayList<>();
                groups.forEach( group -> items.add(group.GetName()));
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, items);
                groupSpinner.setAdapter(adapter);
                pDialog.hideDialog();
            }

            @Override
            public void onFailure(String error)
            {
                Toast.makeText(getContext(), "Could not find groups you control.", Toast.LENGTH_SHORT).show();
                pDialog.hideDialog();
            }
        });
    }

    public void onAddMemberClicked()
    {
        String email = editTextEmail.getText().toString();
        String groupName = selectedGroup.GetName();

        dbManager.AddUserToGroup(email, selectedGroup.GetId(), new UpdateCallBack() {
            @Override
            public void onSuccess()
            {
                Toast.makeText(getContext(), "Added group: " + groupName, Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(), "Added email: " + email, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String errorMessage)
            {
                Toast.makeText(getContext(), "Could not add " + email + " to group.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
