package app.logic.managers;

import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import app.enums.TaskProperty;
import app.enums.TaskStatus;
import app.logic.appcomponents.Task;
import app.logic.appcomponents.User;
import callback.TasksCallBack;
import callback.UpdateCallBack;
import callback.UsersCallBack;

/**
 * Created by Raz on 12/20/2016
 */

public class TaskManager extends ManagerBase {

    public void GetUserTasks(TasksCallBack tasksCallBack) {
        String userId = ((SessionManager) (Services.GetService(SessionManager.class))).getUser().GetUserId();
        ((DBManager) (Services.GetService(DBManager.class))).GetUserTasks(userId, tasksCallBack);
    }

    public void GetGroupTasks(TasksCallBack tasksCallBack,int groupId)
    {
        ((DBManager) (Services.GetService(DBManager.class))).GetGroupTasks(groupId, new TasksCallBack() {
            @Override
            public void onSuccess(List<Task> tasks) {
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

    public void CompleteTask(Task taskToComplete, User user, UpdateCallBack callBack)
    {
        taskToComplete.setStatus(TaskStatus.COMPLETED);
        ((DBManager) Services.GetService(DBManager.class)).UpdateTask(
                taskToComplete.GetTaskId(),
                TaskProperty.STATUS,
                taskToComplete.getStatus(),
                callBack);

        changePoints(user, taskToComplete.getScore());
    }

    public void UnCompleteTask(Task taskToUnComplete, User user, UpdateCallBack callBack)
    {
        taskToUnComplete.setStatus(TaskStatus.INCOMPLETE);
        ((DBManager) Services.GetService(DBManager.class)).UpdateTask(
                taskToUnComplete.GetTaskId(),
                TaskProperty.STATUS,
                taskToUnComplete.getStatus(),
                callBack);

        changePoints(user, -1 * taskToUnComplete.getScore());
    }

    private void changePoints(User user, int points)
    {
        if (user == null)
        {
            return;
        }

        ((DBManager) Services.GetService(DBManager.class)).UpdateUser(
                user.GetUserId(),
                "score",
                user.GetScore() + points,
                new UpdateCallBack() {
                    @Override
                    public void onSuccess() {}

                    @Override
                    public void onFailure(String errorMessage) {}
                });
        user.SetScore(user.GetScore() + points);
        ((SessionManager) Services.GetService(SessionManager.class)).ResetUser(user);
    }
}
