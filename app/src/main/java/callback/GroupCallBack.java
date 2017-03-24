package callback;

import app.logic.appcomponents.Group;

/**
 * Created by razze on 09/03/2017.
 */

public interface GroupCallBack {
    void onSuccess(Group group);

    void onFailure(String error);
}
