package app.logic.managers;

import android.util.Log;

import java.util.ArrayList;

import app.logic.appcomponents.Task;
import callback.TasksCallBack;

/**
 * Created by Raz on 12/20/2016.
 */

public class TaskManager extends ManagerBase {

    public void GetUserTasks(TasksCallBack tasksCallBack) {
        int userId = ((SessionManager) (Services.GetService(SessionManager.class))).getUser().GetUserId();
        ((DBManager) (Services.GetService(DBManager.class))).GetUserTasks(userId, new TasksCallBack() {
            @Override
            public void onSuccess(ArrayList<Task> tasks) {
                tasksCallBack.onSuccess(tasks);
            }

            @Override
            public void onFailure(String error) {
                Log.d("debug", error);
            }
        });

//                ArrayList < Task > tasks = new ArrayList<>();
//        for (int i = 1; i <= 15; i++) {
//            tasks.add(new Task(String.format("example%s", String.valueOf(i)), 5, null, null, null, 0, null, null));
//        }
//        tasksCallBack.onSuccess(tasks);
    }
}
