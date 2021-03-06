package layout;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.project.homey.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import app.customcomponents.CircleImageButton;
import app.customcomponents.HomeyProgressDialog;
import app.enums.TaskStatus;
import app.logic.appcomponents.Group;
import app.logic.appcomponents.Task;
import app.logic.managers.ActivityChangeManager;
import app.logic.managers.DBManager;
import app.logic.managers.GroupManager;
import app.logic.managers.Services;
import app.logic.managers.SessionManager;
import app.logic.verifiers.InputVerifier;
import callback.GroupsCallBack;
import callback.TaskCallBack;

import static android.app.Activity.RESULT_OK;

public class FragmentAddTask extends Fragment {

    private InputVerifier inputVerifier = new InputVerifier();
    private static final int RESULT_LOAD_IMAGE = 1;
    private Spinner dropdown;
    private int spinnerPosition = 0;
    private boolean hasPicture = false;
    private byte[] choosedPicture;
    private ArrayList<Group> userGroups;
    private int selectedGroupId;
    boolean firstTime = true;
    private CircleImageButton taskImage;
    private HomeyProgressDialog pDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_task, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        if (firstTime)
        {
            pDialog = new HomeyProgressDialog(getContext());
            pDialog.setMessage("Loading...");
            pDialog.setCancelable(false);

            firstTime = false;
            dropdown = (Spinner) getView().findViewById(R.id.spinnerTaskGroups);
            taskImage = (CircleImageButton) getView().findViewById(R.id.imageViewAddTask);
            taskImage.setImage(R.mipmap.ic_task_default);
            taskImage.setScaleType(ImageView.ScaleType.FIT_CENTER);

            pDialog.showDialog();
            ((GroupManager) Services.GetService(GroupManager.class)).GetUserGroups(new GroupsCallBack()
            {
                @Override
                public void onSuccess(ArrayList<Group> groups)
                {
                    if (groups.isEmpty())
                    {
                        Toast.makeText(getContext(), "You need to have groups first. ", Toast.LENGTH_SHORT).show();
                        ((ActivityChangeManager) Services.GetService(ActivityChangeManager.class)).SetPlusActivity(getContext());
                    }
                    else
                    {
                        userGroups = groups;
                        setUserGroupsSpinner();
                        pDialog.hide();
                    }
                }

                @Override
                public void onFailure(String error) {
                    pDialog.hide();
                    //TODO handle connection error
                }
            });
        }
    }

    private void setUserGroupsSpinner()
    {
        List<String> items = new ArrayList<>();

        for (Group group : userGroups) {
            items.add(group.GetName());
            if (spinnerPosition != 0) {
                dropdown.setSelection(spinnerPosition);
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                getSelectedGroupId((String) parent.getItemAtPosition(position));
                spinnerPosition = dropdown.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
                spinnerPosition = 0;
            }
        });
    }

    private void getSelectedGroupId(String GroupName) {
        for (Group group : userGroups) {
            if (group.GetName().equals(GroupName)) {
                selectedGroupId = Integer.parseInt(group.GetId());
            }
        }
    }

    public void onAddTaskClick() {
        String name = ((EditText) getView().findViewById(R.id.editTextTaskName)).getText().toString();
        String description = ((EditText) getView().findViewById(R.id.editTextTaskDesc)).getText().toString();
        String userId = ((SessionManager) (Services.GetService(SessionManager.class))).getUser().GetUserId();
        String taskScore = ((EditText) getView().findViewById(R.id.editTextTaskScore)).getText().toString();
        Date startDate = new Date();
        String location = "";
        Date endDate = new Date();
        String status = TaskStatus.INCOMPLETE.value();

        if (!inputVerifier.isNameFieldOk(name)) {
            Toast.makeText(getContext(), inputVerifier.getMessagesToPrint(), Toast.LENGTH_SHORT).show();
            return;
        }

        if (!hasPicture) {
            choosedPicture = new byte[]{0};
        }

        if (taskScore.isEmpty()) {
            taskScore = "0";
        }


        ((DBManager) (Services.GetService(DBManager.class))).AddTask(
                name, description, userId, selectedGroupId, status, location, startDate, endDate,
                Integer.parseInt(taskScore), choosedPicture,
                new TaskCallBack() {
                    @Override
                    public void onSuccess(Task task) {
                        Toast.makeText(getContext(), "Task added!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(String result) {
                        Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void onChooseImageClicked() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), RESULT_LOAD_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri image = data.getData();
            choosedPicture = Services.GetBytes(image, getContext());
            hasPicture = true;
            taskImage.setImageBytes(choosedPicture, R.mipmap.ic_task_default);
        }
    }
}
