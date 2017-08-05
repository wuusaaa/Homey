package app.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.project.homey.R;

import app.customcomponents.HomeyProgressDialog;
import app.database.SQLiteHandler;
import app.logic.appcomponents.User;
import app.logic.managers.DBManager;
import app.logic.managers.EnvironmentManager;
import app.logic.managers.Services;
import app.logic.managers.SessionManager;
import callback.UserCallBack;

public class LoginActivity extends ActivityBase {
    private Button loginButton;
    private Button forgotPassButton;
    private Button linkToRegisterButton;
    private EditText inputEmailEditText;
    private EditText inputPasswordEditText;
    private HomeyProgressDialog pDialog;
    private SQLiteHandler db;
    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        if (requestCode == 99) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                mFusedLocationClient.getLastLocation()
                        .addOnSuccessListener(this, location -> {
                            // Got last known location. In some rare situations this can be null.
//                                if (location != null) {
//                                    Toast.makeText(getApplicationContext(),
//                                            location.toString(), Toast.LENGTH_LONG)
//                                            .show();
//                                }
                        });

            } else {

                // permission denied, boo! Disable the
                // functionality that depends on this permission.
            }
            return;
        }

        // other 'switch' lines to check for other
        // permissions this app might request
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
// SQLite app.database handler
        db = new SQLiteHandler(getApplicationContext());

// Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        99);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }


        inputEmailEditText = (EditText) findViewById(R.id.email);
        inputPasswordEditText = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.buttonLogin);
        linkToRegisterButton = (Button) findViewById(R.id.btnLinkToRegisterScreen);
        forgotPassButton = (Button) findViewById(R.id.buttonLinkToforgotPasswordScreen);

        // Progress dialog
        pDialog = new HomeyProgressDialog(this);
        pDialog.setCancelable(false);

        ((SessionManager) (Services.GetService(SessionManager.class))).setContext(getApplicationContext());


        // Check if user is already logged in or not
        if (((SessionManager) (Services.GetService(SessionManager.class))).isLoggedIn()) {
            ((EnvironmentManager) (Services.GetService(EnvironmentManager.class))).SetScreenName("Home Page");
            // User is already logged in. Take him to HomePage activity
            Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);
            startActivity(intent);
            finish();
        }

        //forgot password click event
        forgotPassButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(),
                    ForgotPasswordActivity.class);
            startActivity(intent);
        });

        // Login button Click Event
        loginButton.setOnClickListener(view -> {
            String email = inputEmailEditText.getText().toString().trim();
            String password = inputPasswordEditText.getText().toString().trim();

            // Check for empty data in the form
            if (!email.isEmpty() && !password.isEmpty()) {
                // login user
                checkLogin(email, password);
            } else {
                // Prompt user to enter credentials
                Toast.makeText(getApplicationContext(),
                        R.string.pleaseEnterTheCredentials, Toast.LENGTH_LONG)
                        .show();
            }
        });

        // Link to Register Screen
        linkToRegisterButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(),
                    RegistrationActivity.class);
            startActivity(intent);
            finish();
        });

    }

    /**
     * function to verify login details in mysql db
     */
    private void checkLogin(final String email, final String password) {
        pDialog.showDialog();

        //TODO  DBManager
        ((DBManager) (Services.GetService(DBManager.class))).Login(email, password, new UserCallBack() {
            @Override
            public void onSuccess(User user) {
                // user successfully logged in
                Toast.makeText(getApplicationContext(), R.string.successfullyLoggedIn, Toast.LENGTH_LONG).show();
                // Create login session
                ((SessionManager) (Services.GetService(SessionManager.class))).setLogin(true);

                ((SessionManager) (Services.GetService(SessionManager.class))).setUser(user);

                db.addUser(user.getName(), user.getEmail(), user.GetUserId() + "", user.getCreatedAt(), user.GetScore(), user.GetLevel());

                pDialog.hideDialog();

                // Launch HomePage activity
                Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(String error) {
                pDialog.hideDialog();
                if (error.equals("JSON ERROR") || error.equals("Volley ERROR")) {
                    Toast.makeText(getApplicationContext(), "Connection error, please try again", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.loginCredentialsAreWrongPleaseTryAgain, Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}