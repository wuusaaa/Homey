package layout;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
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
import app.logic.Notification.MyFirebaseMessagingService;
import app.logic.appcomponents.Group;
import app.logic.appcomponents.User;
import app.logic.managers.DBManager;
import app.logic.managers.Services;
import app.logic.managers.SessionManager;
import app.logic.verifiers.InputVerifier;
import callback.GroupsCallBack;
import callback.UpdateCallBack;
import callback.UserCallBack;

public class FragmentAddMember extends Fragment {
    private InputVerifier inputVerifier = new InputVerifier();
    private EditText editTextEmail;
    private Spinner groupSpinner;
    private CircleImageButton selectedGroupImage;
    private Button buttonAddMember;
    private ArrayList<Group> myGroups;
    private Group selectedGroup = null;
    private HomeyProgressDialog pDialog;
    private DBManager dbManager;
    private String defaultGroupName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_member, container, false);
    }

    @Override
    public void onStart() {
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

        groupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedGroup = getSelectedGroup((String) adapterView.getItemAtPosition(groupSpinner.getSelectedItemPosition()));
                setGroupImage();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selectedGroup = null;
            }
        });
    }

    private void setGroupImage() {
        if (selectedGroup != null) {
            selectedGroupImage.setImageBytes(selectedGroup.GetImage(), R.mipmap.ic_group_default);
        } else {
            selectedGroupImage.setImage(R.mipmap.ic_group_default);
        }
    }

    private Group getSelectedGroup(String groupName) {
        for (int i = 0; i < myGroups.size(); i++) {
            if (myGroups.get(i).GetName().equals(groupName)) {
                return myGroups.get(i);
            }
        }

        return null;
    }

    private void setSpinnerItems() {
        User self = ((SessionManager) Services.GetService(SessionManager.class)).getUser();
        Context context = getContext();
        pDialog.showDialog();
        dbManager.GetGroupsThatUserIsAdmin(self.GetUserId(),
                new GroupsCallBack() {
                    @Override
                    public void onSuccess(ArrayList<Group> groups) {
                        myGroups = groups;
                        List<String> items = new ArrayList<>();
                        groups.forEach(group -> items.add(group.GetName()));
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, items);
                        groupSpinner.setAdapter(adapter);
                        pDialog.hideDialog();

                        if (defaultGroupName != null) {
                            setSpinnerSelection(defaultGroupName);
                        }
                    }

                    @Override
                    public void onFailure(String error) {
                        Toast.makeText(getContext(), "Could not find groups you control.", Toast.LENGTH_SHORT).show();
                        pDialog.hideDialog();
                    }
                });
    }

    public void onAddMemberClicked()
    {
        String email = editTextEmail.getText().toString();
        String groupName = selectedGroup.GetName();

//        if (!inputVerifier.isEmailOk(email)) {
//            Toast.makeText(getContext(), inputVerifier.getMessagesToPrint(), Toast.LENGTH_SHORT).show();
//            return;
//        }

        dbManager.AddUserToGroup(email, selectedGroup.GetId(), new UpdateCallBack()
        {
            @Override
            public void onSuccess()
            {
                Toast.makeText(getContext(), "Added user successfully.", Toast.LENGTH_SHORT).show();
                //Todo: uncomment
//                dbManager.GetUserByEmail(email, new UserCallBack()
//                {
//                    @Override
//                    public void onSuccess(User user)
//                    {
//                        MyFirebaseMessagingService.SendPushNotification(user.GetUserId(), "You joined group: "+selectedGroup.GetName());
//                    }
//
//                    @Override
//                    public void onFailure(String error)
//                    {
//
//                    }
//                })
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void SetDefaultGroupName(String defaultGroupName) {
        this.defaultGroupName = defaultGroupName;
    }

    private void setSpinnerSelection(String groupName) {
        int position = 0;
        for (int i = 0; i < groupSpinner.getCount(); i++) {
            if (groupSpinner.getItemAtPosition(i).toString().equals(groupName)) {
                position = i;
                break;
            }
        }

        if (position != 0) {
            groupSpinner.setSelection(position);
        }
    }
}
