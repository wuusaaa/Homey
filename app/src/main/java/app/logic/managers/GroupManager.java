package app.logic.managers;

import java.util.ArrayList;

import app.logic.appcomponents.Group;
import callback.GroupCallBack;
import callback.GroupsCallBack;

/**
 * Created by barakm on 09/03/2017.
 */

public class GroupManager extends ManagerBase {

    private ArrayList<Group> groupsList;
    private DBManager dbManager = (DBManager) Services.GetService(DBManager.class);

    public void AddNewGroup(String groupName, byte[] img, GroupCallBack groupCallBack) {
        dbManager.AddGroup(groupName, img, groupCallBack);
    }

    public void GetUserGroups(GroupsCallBack groupsCallBack) {
        ArrayList<Group> groups = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            groups.add(new Group(String.valueOf(i), String.format("example%s", i), null));
        }
        groupsCallBack.onSuccess(groups);
    }
}
