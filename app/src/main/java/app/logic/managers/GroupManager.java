package app.logic.managers;

import java.util.ArrayList;

import app.logic.lib.Group;
import callback.GroupCallBack;

/**
 * Created by barakm on 09/03/2017.
 */

public class GroupManager extends ManagerBase {

    private ArrayList<Group> groupsList;
    private DBManager dbManager = (DBManager) Services.GetService(DBManager.class);

    public GroupManager() {
        super();
    }

    public int GetHowManyGroups() {
        return groupsList.size();
    }

    public void AddNewGroup(Group group, GroupCallBack groupCallBack) {
        dbManager.AddGroup(group.GetName(), null, group.GetCreated(), groupCallBack);
    }
}
