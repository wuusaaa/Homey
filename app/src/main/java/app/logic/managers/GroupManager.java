package app.logic.managers;

import android.util.Log;

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
    int creatorId = ((SessionManager) Services.GetService(SessionManager.class)).getUser().GetUserId();

    public void AddNewGroup(String groupName, byte[] img, GroupCallBack groupCallBack) {

        dbManager.AddGroup(creatorId, groupName, img, groupCallBack);
    }

    public void GetUserGroups(GroupsCallBack groupsCallBack) {
        creatorId = ((SessionManager) Services.GetService(SessionManager.class)).getUser().GetUserId();
        dbManager.GetUserGroups(creatorId, new GroupsCallBack() {
            @Override
            public void onSuccess(ArrayList<Group> groups) {
                groupsCallBack.onSuccess(groups);
            }

            @Override
            public void onFailure(String error) {
                Log.d("debug", error);
            }
        });

    }
}
