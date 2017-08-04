package layout;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.project.homey.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import app.logic.appcomponents.Group;
import app.logic.managers.GroupManager;
import app.logic.managers.Services;
import callback.GroupCallBack;
import static android.app.Activity.RESULT_OK;


public class FragmentAddGroup extends Fragment {

    private static final int RESULT_LOAD_IMAGE = 1;
    private byte[] choosedPicture;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_add_group, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        choosedPicture = null;
    }

    public void onCreateGroup()
    {
        GroupManager groupManager = (GroupManager) Services.GetService(GroupManager.class);
        String name = ((EditText) getView().findViewById(R.id.editTextGroupName)).getText().toString();
        groupManager.AddNewGroup(name, choosedPicture, new GroupCallBack() {
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
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), RESULT_LOAD_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null)
        {
            Uri image = data.getData();
            choosedPicture = getBytes(image);
            ((ImageButton)getView().findViewById(R.id.choosenGroupImage)).setImageURI(image);
        }
    }

    private byte[] getBytes(Uri image)
    {
        try
        {
            InputStream inputStream = getView().getContext().getContentResolver().openInputStream(image);
            ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];

            int len = 0;
            while ((len = inputStream.read(buffer)) != -1) {
                byteBuffer.write(buffer, 0, len);
            }
            return byteBuffer.toByteArray();
        }
        catch (Exception e)
        {
            Toast.makeText(getContext(), "Failed to load image", Toast.LENGTH_SHORT).show();
            return null;
        }
    }
}
