package app.activities;

import android.os.Bundle;
import android.view.View;

import com.project.homey.R;

public class ProfileActivity extends ActivityWithHeaderBase {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setPageInfo();
    }

    private void setPageInfo()
    {

    }

    public void editButton_onClick(View view)
    {

    }
}
