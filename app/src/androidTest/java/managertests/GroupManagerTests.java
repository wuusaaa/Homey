package managertests;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import app.logic.lib.Group;
import app.logic.managers.GroupManager;
import app.logic.managers.Services;
import callback.GroupCallBack;

/**
 * Created by barakm on 20/03/2017.
 */
@RunWith(AndroidJUnit4.class)
public class GroupManagerTests extends TestBase {

    private GroupManager groupManager;
    private Group newGroup = null;

    @Override
    public void BeforeTest() {
        super.BeforeTest();
        groupManager = (GroupManager) Services.GetService(GroupManager.class);
    }

    @Test
    public void AddGroupToListTest() {
        groupManager.AddNewGroup("testManagerGroup", null, new GroupCallBack() {
            @Override
            public void onSuccess(Group group) {
                newGroup = group;
            }

            @Override
            public void onFailure(String error) {
            }
        });
        TimeUtils.Wait();
        Assert.assertNotEquals("Number of group is not as expected", newGroup, null);

    }

}

