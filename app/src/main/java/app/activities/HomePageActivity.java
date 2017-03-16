package app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.project.homey.R;

import app.logic.managers.Services;
import app.logic.managers.SessionManager;


public class HomePageActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Button plusButton;

    private TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        plusButton = (Button) findViewById(R.id.buttonPlus);
        txt = (TextView) findViewById(R.id.textView11);
        txt.setText("Welcome " + ((SessionManager) (Services.GetService(SessionManager.class))).getUser().getName());

        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, PlusActivity.class);
                startActivity(intent);
            }
        });

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
