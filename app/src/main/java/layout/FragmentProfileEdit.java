package layout;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.project.homey.R;

import app.customcomponents.CircleImageButton;
import app.logic.appcomponents.User;
import app.logic.managers.DBManager;
import app.logic.managers.Services;
import app.logic.managers.SessionManager;
import callback.UpdateCallBack;

import static android.app.Activity.RESULT_OK;


public class FragmentProfileEdit extends Fragment
{
    private static final int RESULT_LOAD_IMAGE = 1;
    private byte[] choosedPicture;
    private boolean hasFirstNameChanged = false;
    private boolean hasPicture = false;
    private boolean isFirstRun = true;
    private EditText editTextFirstName;
    private CircleImageButton circleImage;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_profile_edit, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        if (isFirstRun)
        {
            isFirstRun = false;

            editTextFirstName = (EditText) getView().findViewById(R.id.profileEditFirstName);
            circleImage = (CircleImageButton) getView().findViewById(R.id.imageViewEditProfile);

            String firstName = getArguments().getString("firstName");

            editTextFirstName.setText(firstName);
            circleImage.setImage(R.mipmap.ic_profile_default);
            circleImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
            setTextListeners();
        }
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
        }

        if (hasPicture)
        {
            user.setImg(choosedPicture);
            dataBaseManager.UpdateUser(user.GetUserId(), "profile_pic", Base64.encodeToString(user.GetImage(),Base64.DEFAULT), new UpdateCallBack() {
                @Override
                public void onSuccess() {
                    Toast.makeText(c, "Successfully changed picture", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(String errorMessage) {
                    Toast.makeText(c, "Failed to change picture", Toast.LENGTH_SHORT).show();
                }
            });
        }

        ((SessionManager) Services.GetService(SessionManager.class)).setUser(user);
    }

    public void onChoosePictureClick()
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
            choosedPicture = Services.GetBytes(image, getContext());
            hasPicture = true;
            circleImage.setImageBytes(choosedPicture, R.mipmap.ic_profile_default);
            circleImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
        }
    }
}
