package app.logic.managers;

import java.util.ArrayList;

import app.logic.appcomponents.Task;
import callback.TasksCallBack;

/**
 * Created by Raz on 12/20/2016.
 */

public class TaskManager extends ManagerBase {

    public void GetUserTasks(TasksCallBack tasksCallBack) {
        ArrayList<Task> tasks = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            tasks.add(new Task(String.format("example%s", String.valueOf(i)), null, null, null, 0, null, null));
        }
        tasksCallBack.onSuccess(tasks);
    }
}
