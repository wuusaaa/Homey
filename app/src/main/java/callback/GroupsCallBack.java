package callback;

import java.util.ArrayList;

import app.logic.appcomponents.Group;

/**
 * Created by razze on 09/03/2017.
 */

public interface GroupsCallBack {
    void onSuccess(ArrayList<Group> groups);

    void onFailure(String error);
}
