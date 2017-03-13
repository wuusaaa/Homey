package app.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.project.homey.R;

import app.logic.lib.User;
import app.logic.managers.SessionManager;
import callback.UserCallBack;
import db.DBManager;
import db.SQLiteHandler;

public class LoginActivity extends Activity {
    private static final String TAG = LoginActivity.class.getSimpleName();
    private Button loginButton;
    private Button forgotPassButton;
    private Button linkToRegisterButton;
    private EditText inputEmailEditText;
    private EditText inputPasswordEditText;
    private ProgressDialog pDialog;
    private SQLiteHandler db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputEmailEditText = (EditText) findViewById(R.id.email);
        inputPasswordEditText = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.btnLogin);
        linkToRegisterButton = (Button) findViewById(R.id.btnLinkToRegisterScreen);
        forgotPassButton = (Button) findViewById(R.id.buttonLinkToforgotPasswordScreen);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        SessionManager.GetInstance().set_context(getApplicationContext());

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Check if user is already logged in or not
        if (SessionManager.GetInstance().isLoggedIn()) {
            // User is already logged in. Take him to HomePage activity
            Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);
            startActivity(intent);
            finish();
        }

        //forgot password click event
        forgotPassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),
                        ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        // Login button Click Event
        loginButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String email = inputEmailEditText.getText().toString().trim();
                String password = inputPasswordEditText.getText().toString().trim();

                // Check for empty data in the form
                if (!email.isEmpty() && !password.isEmpty()) {
                    // login user
                    checkLogin(email, password);
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Please enter the credentials!", Toast.LENGTH_LONG)
                            .show();
                }
            }

        });

        // Link to Register Screen
        linkToRegisterButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        RegistrationActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    /**
     * function to verify login details in mysql db
     */
    private void checkLogin(final String email, final String password) {
        showDialog();

        //TODO  DBManager
        new DBManager().Login(email, password, new UserCallBack() {
            @Override
            public void onSuccess(User user) {
                // user successfully logged in
                Toast.makeText(getApplicationContext(), "Successfully logged in!", Toast.LENGTH_LONG).show();
                // Create login session
                SessionManager.GetInstance().setLogin(true);

                SessionManager.GetInstance().setUser(user);

                db.addUser(user.getName(), user.getEmail(), user.getUid(), user.getCreatedAt());

                hideDialog();

                // Launch HomePage activity
                Intent intent = new Intent(LoginActivity.this,
                        HomePageActivity.class);
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