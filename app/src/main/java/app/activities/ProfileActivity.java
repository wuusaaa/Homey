package app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.project.homey.R;

public class ProfileActivity extends ActivityBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }

    public void editButton_onClick(View view) {
        Intent intent = new Intent(this, EditProfileActivity.class);
        startActivity(intent);
    }
}
