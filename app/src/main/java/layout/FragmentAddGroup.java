package layout;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.project.homey.R;

import app.activities.GroupPageActivity;
import app.customcomponents.CircleImageButton;
import app.logic.appcomponents.Group;
import app.logic.managers.ActivityChangeManager;
import app.logic.managers.EnvironmentManager;
import app.logic.managers.GroupManager;
import app.logic.managers.Services;
import app.logic.verifiers.InputVerifier;
import callback.GroupCallBack;

import static android.app.Activity.RESULT_OK;


public class FragmentAddGroup extends Fragment {

    private static final int RESULT_LOAD_IMAGE = 1;
    private InputVerifier inputVerifier = new InputVerifier();
    private boolean hasPicture = false;
    private byte[] chosePicture;
    private CircleImageButton groupImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_group, container, false);
    }

    @Override
    public void onStart()
    {
        super.onStart();

        groupImage = (CircleImageButton) getView().findViewById(R.id.chosenGroupImage);

        if (!hasPicture) {
            groupImage.setImage(R.mipmap.ic_group_default);
            groupImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
        }
    }

    public void onCreateGroup() {
        GroupManager groupManager = (GroupManager) Services.GetService(GroupManager.class);
        String name = ((EditText) getView().findViewById(R.id.editTextGroupName)).getText().toString();


        if (!inputVerifier.isNameFieldOk(name)) {
            Toast.makeText(getContext(), inputVerifier.getMessagesToPrint(), Toast.LENGTH_SHORT).show();
        }


        if (!hasPicture) {
            chosePicture = new byte[]{0};
        }

        groupManager.AddNewGroup(name, chosePicture, new GroupCallBack() {
            @Override
            public void onSuccess(Group group)
            {
                ((ActivityChangeManager)Services.GetService(ActivityChangeManager.class)).SetGroupActivity(getContext(), group);
            }

            @Override
            public void onFailure(String error) {
                ((EditText) getView().findViewById(R.id.editTextGroupName)).setText(error);
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
            chosePicture = Services.GetBytes(image, getContext());
            hasPicture = true;
            groupImage.setImageBytes(chosePicture, R.mipmap.ic_group_default);
        }
    }
}
