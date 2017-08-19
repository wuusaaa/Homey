package app.activities;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.widget.TextView;

import com.project.homey.R;

import app.logic.verifiers.InputVerifier;


public class ActivityBase extends AppCompatActivity {

    protected TextView screenName;
    protected InputVerifier inputVerifier = new InputVerifier();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
    }

    /**
     * Returns the screen width and height divided by desired values
     */
    // **************************************************************
    // Returns the screen width and height divided by desired values
    // **************************************************************
    protected Point getScreenSize(int widthDivider, int heightDivider) {
        Display display = getWindowManager().getDefaultDisplay();
        Point screenSize = new Point();
        try {
            display.getRealSize(screenSize);
        } catch (NoSuchMethodError err) {
            display.getSize(screenSize);
        }

        screenSize.x /= widthDivider;
        screenSize.y /= heightDivider;
        return screenSize;
    }

    protected void setScreenName(String screenName) {
        ((TextView) findViewById(R.id.textViewScreenName)).setText(screenName);
    }

    protected AppCompatActivity getAppCompatActivity() {
        return this;
    }
}
