package app.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.project.homey.R;


public class ActivityBase extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_base);
    }
}
