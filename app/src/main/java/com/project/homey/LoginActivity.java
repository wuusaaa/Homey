package com.project.homey;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    private Button buttonReg;
    private Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        buttonReg = (Button) findViewById(R.id.buttonRegistration);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonRegClick(v);
            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonLoginClick(v);
            }
        });


    }


    private void onButtonRegClick(View v) {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    private void onButtonLoginClick(View v) {
        //TODO check the credentials against the database
        Intent intent = new Intent(this, HomePageActivity.class);
        startActivity(intent);
    }
}