package app.activities;

import android.support.design.widget.BottomNavigationView;

/**
 * Created by barakm on 24/07/2017
 */

public class ActivityWithNavigatorBase extends ActivityBase {

    protected BottomNavigationView bottomNavigationView;

    protected int getMenuId() {
        return 0;
    }
}
