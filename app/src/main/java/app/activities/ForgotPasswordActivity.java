package app.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.project.homey.R;

import org.json.JSONObject;

import app.logic.managers.DBManager;
import app.logic.managers.Services;
import callback.ServerCallBack;

public class ForgotPasswordActivity extends ActivityBase {

    private EditText emailEditText;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        emailEditText = (EditText) findViewById(R.id.email);
        Button resetPassButton = (Button) findViewById(R.id.resetButton);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setTitle("Please wait.");

        resetPassButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();

            if (!inputVerifier.isEmailOk(email)) {
                Toast.makeText(this, inputVerifier.getMessagesToPrint(), Toast.LENGTH_SHORT).show();
                return;
            }

            ((DBManager) (Services.GetService(DBManager.class))).ResetPassword(email, new ServerCallBack() {
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
