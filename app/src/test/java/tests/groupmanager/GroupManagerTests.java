package tests.groupmanager;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import app.logic.managers.GroupManager;

/**
 * Created by barakm on 12/03/2017.
 */

public class GroupManagerTests {

    private GroupManager groupManager;

    @Before
    public void BeforeTest() {
        //groupManager = new GroupManager();
    }

    @Test
    public void AddGroupToListTest() {
        int howManyGroups = groupManager.GetHowManyGroups();
        Assert.assertEquals("Number of group is not as expected", groupManager.GetHowManyGroups(), howManyGroups + 1);
    }

//    @Test
//    public void DeleteGroupFromListTest(){
//        int howMany
//    }

}

