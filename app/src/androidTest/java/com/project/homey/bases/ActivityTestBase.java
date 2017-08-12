package com.project.homey.bases;

import com.project.homey.elementsproxy.ViewInteractionProxy;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

/**
 * Created by barakm on 10/07/2017
 */

public abstract class ActivityTestBase extends TestBase {

    protected ViewInteractionProxy getViewById(final int viewID) {
        return new ViewInteractionProxy(onView(withId(viewID)));
    }

    protected ViewInteractionProxy getViewByIdWithParent(final String className, final int parentId) {
        return new ViewInteractionProxy(onView(allOf(withClassName(is(className)), withParent(withId(parentId)))));
    }


    protected ViewInteractionProxy getViewByText(final int viewText) {
        return new ViewInteractionProxy(onView(withText(viewText)));
    }

}
