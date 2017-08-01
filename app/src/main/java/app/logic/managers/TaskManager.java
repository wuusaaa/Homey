package app.logic.managers;

import android.util.Log;

import java.util.ArrayList;

import app.logic.appcomponents.Task;
import app.logic.appcomponents.User;
import callback.TasksCallBack;
import callback.UsersCallBack;

/**
 * Created by Raz on 12/20/2016
 */

public class TaskManager extends ManagerBase {

    public void GetUserTasks(TasksCallBack tasksCallBack) {
        String userId = ((SessionManager) (Services.GetService(SessionManager.class))).getUser().GetUserId();
        ((DBManager) (Services.GetService(DBManager.class))).GetUserTasks(userId, tasksCallBack);
    }

    public void GetGroupTasks(TasksCallBack tasksCallBack,int groupId) {
        ((DBManager) (Services.GetService(DBManager.class))).GetGroupTasks(groupId, new TasksCallBack() {
            @Override
            public void onSuccess(ArrayList<Task> tasks) {
                tasksCallBack.onSuccess(tasks);
            }

            @Override
            public void onFailure(String error) {
                Log.d("debug", error);
                tasksCallBack.onFailure(error);

            }
        });
    }

    public void GetTaskUsersByTaskId(UsersCallBack usersCallBack, int taskId) {
        ((DBManager) (Services.GetService(DBManager.class))).GetTaskUsersByTaskId(taskId, new UsersCallBack() {
            @Override
            public void onSuccess(ArrayList<User> users) {
                usersCallBack.onSuccess(users);
            }

            @Override
            public void onFailure(String error) {
                Log.d("debug", error);
                usersCallBack.onFailure(error);

            }
        });
    }
}
