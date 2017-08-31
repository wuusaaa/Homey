package app.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.project.homey.R;

import app.database.SQLiteHandler;
import app.logic.appcomponents.User;
import app.logic.managers.DBManager;
import app.logic.managers.Services;
import app.logic.managers.SessionManager;
import callback.UserCallBack;

//TODO add birthday

public class RegistrationActivity extends ActivityBase {
    private Button registerButton;
    private Button linkToLoginButton;
    private EditText inputFullNameEditText;
    private EditText inputEmailEditText;
    private EditText inputPasswordEditText;
    private ProgressDialog pDialog;
    private EditText confirmPasswordEditText;
    private SQLiteHandler db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        inputFullNameEditText = (EditText) findViewById(R.id.name);
        inputEmailEditText = (EditText) findViewById(R.id.email);
        inputPasswordEditText = (EditText) findViewById(R.id.password);
        registerButton = (Button) findViewById(R.id.btnRegister);
        linkToLoginButton = (Button) findViewById(R.id.btnLinkToLoginScreen);
        confirmPasswordEditText = (EditText) findViewById(R.id.confirmPassword);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // SQLite app.database handler
        db = new SQLiteHandler(getApplicationContext());

        // Check if user is already logged in or not
        if (((SessionManager) (Services.GetService(SessionManager.class))).isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(RegistrationActivity.this,
                    HomePageActivity.class);
            startActivity(intent);
            finish();
        }

        // Register Button Click event
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = inputFullNameEditText.getText().toString().trim();
                String email = inputEmailEditText.getText().toString().trim();
                String password = inputPasswordEditText.getText().toString().trim();
                String confPassword = confirmPasswordEditText.getText().toString().trim();


                if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !confPassword.isEmpty()) {

                    if (!inputVerifier.isEmailOk(email)) {
                        Toast.makeText(RegistrationActivity.this, inputVerifier.getMessagesToPrint(), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (confPassword.equals(password)) {
                        RegistrationActivity.this.registerUser(name, email, password);
                    } else {
                        Toast.makeText(RegistrationActivity.this.getApplicationContext(),
                                "Your passwords does not match!", Toast.LENGTH_LONG)
                                .show();
                    }
                } else {
                    Toast.makeText(RegistrationActivity.this.getApplicationContext(),
                            "Please enter all credentials!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

        // Link to Login Screen
        linkToLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegistrationActivity.this.getApplicationContext(),
                        LoginActivity.class);
                RegistrationActivity.this.startActivity(i);
                RegistrationActivity.this.finish();
            }
        });

    }

    /**
     * Function to store user in MySQL app.database. will post params(name,
     * email, password) to register url
     */
    private void registerUser(final String name, final String email, final String password) {
        pDialog.setMessage("Registering ...");
        showDialog();

        ((DBManager) (Services.GetService(DBManager.class))).RegisterUser(name, email, password, new UserCallBack() {
            @Override
            public void onSuccess(User user) {
                hideDialog();
                db.addUser(user.GetName(), user.getEmail(), user.GetUserId() + "", user.getCreatedAt(),user.GetScore(),user.GetLevel(),user.GetImage(),user.GetToken());

                Toast.makeText(getApplicationContext(), "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();

                // Launch login activity
                Intent intent = new Intent(
                        RegistrationActivity.this,
                        LoginActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(String error) {
                hideDialog();
                if (error.equals("JSON ERROR") || error.equals("Volley ERROR")) {
                    Toast.makeText(getApplicationContext(), "Connection error, please try again", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}