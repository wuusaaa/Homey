package callback;

import app.logic.appcomponents.Group;

/**
 * Created by pdc5np on 7/6/2017.
 */

public interface GotoGroupPageCallBack {
    void onSuccess(Group group);

    void onFailure(String error);
}
