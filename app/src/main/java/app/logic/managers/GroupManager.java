package app.logic.managers;

import java.util.ArrayList;

import app.logic.lib.Group;
import callback.GroupsCallBack;
import db.DBManager;

/**
 * Created by barakm on 09/03/2017.
 */

public class GroupManager extends ManagerBase {


    private ArrayList<Group> groupsList;

    public GroupManager() {
        DBManager dbManager = Services.GetService();
        dbManager.GetUserGroups(0, new GroupsCallBack() {
            @Override
            public void onSuccess(ArrayList<Group> groups) {
                groupsList = groups;

            }

            @Override
            public void onFailure(String error) {
                System.out.println(error);
            }
        });
    }

    public int GetHowManyGroups() {
        return groupsList.size();
    }

    public void AddNewGroup(Group group) {
        groupsList.add(group);
    }
}
