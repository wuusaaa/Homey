package app.activities;

import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.widget.Button;

import com.project.homey.R;

/**
 * Created by barakm on 24/07/2017
 */

public class ActivityWithNavigatorBase extends ActivityBase {

    @Override
    protected void onStart() {
        super.onStart();

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(item ->
        {
            Intent intent;

            switch (item.getItemId()) {
                case R.id.assignment:
                    intent = new Intent(this, TaskActivity.class);
                    startActivity(intent);
                    break;
                case R.id.settings:
                    intent = new Intent(this, SettingsActivity.class);
                    startActivity(intent);
                    break;
                case R.id.today:
                    intent = new Intent(this, ProfileActivity.class);
                    startActivity(intent);
                    break;
                case R.id.groups:
                    intent = new Intent(this, GroupPageActivity.class);
                    startActivity(intent);
                    break;
                case R.id.home:
                    intent = new Intent(this, HomePageActivity.class);
                    startActivity(intent);
                    break;
                default:
                    break;
            }

            return true;
        });
    }

    protected void setNavigator() {
        Button assign = (Button) findViewById(R.id.assignment);
        assign.setOnClickListener(event ->
        {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        });
    }
}
