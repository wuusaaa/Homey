package app.activities;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;


public class ActivityBase extends AppCompatActivity {


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
}
