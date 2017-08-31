package app.activities;

import android.view.View;
import android.widget.ImageButton;

import com.project.homey.R;

import app.logic.managers.ActivityChangeManager;
import app.logic.managers.Services;

/**
 * Created by barakm on 24/07/2017
 */

public class ActivityWithHeaderBase extends ActivityBase
{
    protected ImageButton buttonPlus;
    protected ImageButton buttonHomePage;

    @Override
    protected void onStart() {
        super.onStart();

        buttonPlus = (ImageButton) findViewById(R.id.imageButtonPlus);
        buttonHomePage = (ImageButton) findViewById(R.id.imageViewHomePage);

        buttonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View buttonPlus) {
                ((ActivityChangeManager) Services.GetService(ActivityChangeManager.class)).SetPlusActivity(ActivityWithHeaderBase.this);
            }
        });

        buttonHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View buttonSettings) {
                ((ActivityChangeManager) Services.GetService(ActivityChangeManager.class)).SetHomeActivity(ActivityWithHeaderBase.this);
            }
        });
    }
}
