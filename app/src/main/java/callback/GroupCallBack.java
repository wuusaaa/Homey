package callback;

import app.logic.lib.Group;

/**
 * Created by razze on 09/03/2017.
 */

public interface GroupCallBack {
    void onSuccess(Group group);

    void onFailure(String error);
}
