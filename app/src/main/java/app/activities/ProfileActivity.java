package app.activities;

import android.os.Bundle;
import android.view.View;

import com.project.homey.R;

public class ProfileActivity extends ActivityWithNavigatorBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }

    public void editButton_onClick(View view) {
    }
}
