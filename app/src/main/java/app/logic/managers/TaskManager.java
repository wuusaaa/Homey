package app.logic.managers;

import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import app.enums.TaskProperty;
import app.enums.TaskStatus;
import app.logic.Notification.MyFirebaseMessagingService;
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

    public void CompleteTask(Task taskToComplete, UpdateCallBack callBack)
    {
        // Update DB that the task is complete:
        taskToComplete.setStatus(TaskStatus.COMPLETED);
        ((DBManager) Services.GetService(DBManager.class)).UpdateTask(
                taskToComplete.GetTaskId(),
                TaskProperty.STATUS,
                taskToComplete.getStatus(),
                callBack);

        sendAdminsNotification(taskToComplete);

        // Make the points change to all of the task's participants:
        ((DBManager) Services.GetService(DBManager.class)).GetTaskUsersByTaskId(Integer.parseInt(taskToComplete.GetTaskId()), new UsersCallBack()
        {
            @Override
            public void onSuccess(ArrayList<User> users)
            {
                users.forEach(new Consumer<User>() {
                    @Override
                    public void accept(User user) {
                        changePoints(user, taskToComplete.getScore());
                    }
                });
            }

            @Override
            public void onFailure(String error)
            {

            }
        });
    }

    private void sendAdminsNotification(Task completedTask)
    {
        User submittedUser = ((SessionManager) Services.GetService(SessionManager.class)).getUser();
        ((DBManager) Services.GetService(DBManager.class)).GetGroupAdmins(completedTask.GetGroupId(), new UsersCallBack()
        {
            @Override
            public void onSuccess(ArrayList<User> admins)
            {
                admins.forEach(new Consumer<User>() {
                    @Override
                    public void accept(User admin) {
                        if (!admin.GetId().equals(submittedUser.GetId())) {
                            MyFirebaseMessagingService.SendPushNotification(admin.GetUserId(),
                                    submittedUser.GetName() + " Submitted task: '" + completedTask.GetName() + "'.");
                        }

                    }
                });
            }

            @Override
            public void onFailure(String error)
            {

            }
        });
    }

    public void UnCompleteTask(Task taskToUnComplete, UpdateCallBack callBack)
    {
// Update DB that the task is complete:
        taskToUnComplete.setStatus(TaskStatus.INCOMPLETE);
        ((DBManager) Services.GetService(DBManager.class)).UpdateTask(
                taskToUnComplete.GetTaskId(),
                TaskProperty.STATUS,
                taskToUnComplete.getStatus(),
                callBack);


        // Make the points change to all of the task's participants:
        ((DBManager) Services.GetService(DBManager.class)).GetTaskUsersByTaskId(Integer.parseInt(taskToUnComplete.GetTaskId()), new UsersCallBack()
        {
            @Override
            public void onSuccess(ArrayList<User> users) {
                users.forEach(new Consumer<User>() {
                    @Override
                    public void accept(User user) {
                        changePoints(user, -1 * taskToUnComplete.getScore());
                        MyFirebaseMessagingService.SendPushNotification(user.GetUserId(),
                                "Task '" + taskToUnComplete.GetName() + "' unsubmitted and assigned back.");
                    }
                });
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    private void changePoints(User user, int points)
    {
        User self = ((SessionManager) Services.GetService(SessionManager.class)).getUser();
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

        // Update local DB:
        if (self.GetId().equals(user.GetId()))
        {
            self.SetScore(self.GetScore() + points);
            ((SessionManager) Services.GetService(SessionManager.class)).ResetUser(self);
        }
    }
}
