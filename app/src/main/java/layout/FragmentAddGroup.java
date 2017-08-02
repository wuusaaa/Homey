package layout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.project.homey.R;

import app.logic.appcomponents.Group;
import app.logic.managers.GroupManager;
import app.logic.managers.Services;
import callback.GroupCallBack;
import static android.app.Activity.RESULT_OK;


public class FragmentAddGroup extends Fragment {

    private static final int RESULT_LOAD_IMAGE = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_add_group, container, false);
    }

    public void onCreateGroup()
    {
        GroupManager groupManager = (GroupManager) Services.GetService(GroupManager.class);
        String name = ((EditText) getView().findViewById(R.id.editTextGroupName)).getText().toString();
        byte[] arr = {0, 5, 3, 2, 4, 5};
        groupManager.AddNewGroup(name, arr, new GroupCallBack() {
            @Override
            public void onSuccess(Group group) {
                ((EditText) getView().findViewById(R.id.editTextGroupName)).setText("blabla");
            }

            @Override
            public void onFailure(String error) {
                ((EditText) getView().findViewById(R.id.editTextGroupName)).setText(error);
            }
        });
    }

    public void onChooseImageClicked()
    {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, RESULT_LOAD_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null)
        {
            Uri image = data.getData();
            ((ImageButton)getView().findViewById(R.id.choosenGroupImage)).setImageURI(image);
        }
    }
}
