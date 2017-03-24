package callback;

import app.logic.appcomponents.Task;

/**
 * Created by razze on 09/03/2017.
 */

public interface TaskCallBack {
    void onSuccess(Task task);

    void onFailure(String error);
}
