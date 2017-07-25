package app.activities;

import android.content.Intent;
import android.widget.ImageButton;

import com.project.homey.R;

/**
 * Created by barakm on 24/07/2017
 */

public class ActivityWithHeaderBase extends ActivityBase {

    protected ImageButton buttonPlus;
    protected ImageButton buttonSettings;

    @Override
    protected void onStart() {
        super.onStart();

        buttonPlus = (ImageButton) findViewById(R.id.imageButtonPlus);
        buttonSettings = (ImageButton) findViewById(R.id.imageViewSettings);

        buttonPlus.setOnClickListener(buttonPlus ->
        {
            Intent intent = new Intent(this, PlusActivity.class);
            startActivity(intent);
        });

        buttonSettings.setOnClickListener(buttonSettings ->
        {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        });
    }
}
