package managertests;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.runner.RunWith;

import app.logic.appcomponents.User;
import app.logic.managers.DBManager;
import app.logic.managers.Services;
import callback.UserCallBack;

/**
 * Created by barakm on 20/03/2017.
 */

@RunWith(AndroidJUnit4.class)
public abstract class TestBase {

    @Before
    public void BeforeTest() {

        ((DBManager) (Services.GetService(DBManager.class))).Login("test@gmail.com", "test", new UserCallBack() {
            @Override
            public void onSuccess(User user) {
            }

            @Override
            public void onFailure(String error) {
            }
        });
        TimeUtils.Wait();
    }
}
