package callback;

import app.logic.appcomponents.Task;

/**
 * Created by barakm on 25/07/2017
 */

public interface GoToTaskPageCallBack {
    void onSuccess(Task task);

    void onFailure(String error);
}
