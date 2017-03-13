package app.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.project.homey.R;

import org.json.JSONObject;

import callback.ServerCallBack;
import db.DBManager;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText emailEditText;
    private Button resetPassButton;
    private ProgressDialog pDialog;
    //TODO
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        emailEditText = (EditText) findViewById(R.id.email);
        resetPassButton = (Button) findViewById(R.id.resetButton);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setTitle("Please wait.");

        resetPassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DBManager().ResetPassword(emailEditText.getText().toString(), new ServerCallBack() {
                    @Override
                    public void onSuccess(JSONObject result) {
                        hideDialog();
                        Toast.makeText(getApplicationContext(),
                                "Password has been reset! Please check your email.", Toast.LENGTH_LONG)
                                .show();
                    }

                    @Override
                    public void onFailure(String error) {
                        hideDialog();
                        Toast.makeText(getApplicationContext(),
                                error, Toast.LENGTH_LONG)
                                .show();
                    }
                });
                showDialog();
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
