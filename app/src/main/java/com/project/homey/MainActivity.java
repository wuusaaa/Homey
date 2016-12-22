package com.project.homey;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button getMeToReg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getMeToReg = (Button) findViewById(R.id.button);
        getMeToReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRegButtonClick(v);
            }
        });
    }
    private void onRegButtonClick(View v){
        Intent intent = new Intent(this, ActivityLogin.class);
        startActivity(intent);
    }
}
