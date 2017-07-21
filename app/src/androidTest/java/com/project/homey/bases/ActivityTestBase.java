package com.project.homey.bases;

import com.project.homey.elementsproxy.ViewInteractionProxy;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by barakm on 10/07/2017
 */

public abstract class ActivityTestBase extends TestBase {

    protected ViewInteractionProxy getViewById(final int viewID) {
        return new ViewInteractionProxy(onView(withId(viewID)));
    }


    protected ViewInteractionProxy getViewByText(final int viewText) {
        return new ViewInteractionProxy(onView(withText(viewText)));
    }

}
