package app.logic.managers;

import java.util.ArrayList;

import app.logic.lib.Group;

/**
 * Created by barakm on 09/03/2017.
 */

public class GroupManager extends ManagerBase {

    private ArrayList<Group> groupsList;

    public GroupManager() {
        super();
    }

    public int GetHowManyGroups() {
        return groupsList.size();
    }

    public void AddNewGroup(Group group) {
        groupsList.add(group);
    }

    public String stam() {
        return "stam";
    }
}
