package app.logic.managers;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import app.logic.appcomponents.Task;
import app.logic.appcomponents.User;
import callback.UpdateCallBack;

/**
 * Created by benro on 8/11/2017.
 */

public class DragManager extends ManagerBase
{
    private Task movingTask;
    private User droppedUser;

    public void setMovingTask(Task task)
    {
        movingTask = task;
    }

    public void setDroppedUser(User user)
    {
        droppedUser = user;
    }

    public Task getMovingTask()
    {
        return movingTask;
    }

    public User getDroppedUser()
    {
        return droppedUser;
    }

    public void AssignTaskToUser(Context context)
    {
        if (movingTask != null && droppedUser != null)
        {
            ((DBManager) Services.GetService(DBManager.class)).AddUserToTask(droppedUser.GetUserId(), movingTask.GetTaskId(), new UpdateCallBack() {
                @Override
                public void onSuccess()
                {
                    Toast.makeText(context, movingTask.GetName() + "Assigned to task: "+ droppedUser.GetName(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(String errorMessage)
                {
                    Toast.makeText(context, "User is allready on this task", Toast.LENGTH_SHORT).show();
                    //TODO: set this message from the server:
                    //Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
