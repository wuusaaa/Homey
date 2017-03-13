package callback;

import app.task.Task;

/**
 * Created by razze on 09/03/2017.
 */

public interface TaskCallBack {
    void onSuccess(Task task);

    void onFailure(String error);
}
