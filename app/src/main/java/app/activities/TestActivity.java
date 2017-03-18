package app.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.project.homey.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import app.logic.managers.DBManager;
import app.logic.managers.Services;
import callback.ServerCallBack;

public class TestActivity extends AppCompatActivity {

    Button btn;
    EditText txt;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        btn = (Button) findViewById(R.id.button);
        txt = (EditText) findViewById(R.id.editText);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DBManager) (Services.GetService(DBManager.class))).test(new ServerCallBack() {
                    @Override
                    public void onSuccess(JSONObject result) {
                        try {
                            JSONArray res = new JSONArray(result.getString("res"));
                            txt.setText(res.getJSONObject(4).getString("name"));
                            Toast.makeText(getApplicationContext(),
                                    result.getString("res"), Toast.LENGTH_LONG)
                                    .show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(String result) {
                        Toast.makeText(getApplicationContext(),
                                result, Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }
        });
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.today:

                        break;
                    case R.id.assignment:

                        break;
                    case R.id.settings:

                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }
}
