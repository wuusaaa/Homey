package callback;

import java.util.List;

import app.logic.appcomponents.Task;

/**
 * Created by razze on 09/03/2017
 */

public interface TasksCallBack {
    void onSuccess(List<Task> tasks);

    void onFailure(String error);
}
