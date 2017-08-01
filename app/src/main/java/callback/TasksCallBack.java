package callback;

import java.util.ArrayList;

import app.logic.appcomponents.Task;

/**
 * Created by razze on 09/03/2017
 */

public interface TasksCallBack {
    void onSuccess(ArrayList<Task> tasks);

    void onFailure(String error);
}
