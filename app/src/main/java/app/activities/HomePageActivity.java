package app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.project.homey.R;

import app.logic.managers.SessionManager;


public class HomePageActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    private TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        txt = (TextView) findViewById(R.id.textView11);
        txt.setText("Welcome " + SessionManager.GetInstance().getUser().getName());

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.today:
                        intent = new Intent(HomePageActivity.this, TestActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.assignment:

                        break;
                    case R.id.settings:
                        intent = new Intent(HomePageActivity.this, SettingsActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }
}
