package layout;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.homey.R;

import java.util.List;

import app.logic.appcomponents.Task;
import app.logic.appcomponents.User;
import app.logic.managers.DBManager;
import app.logic.managers.Services;
import callback.TasksCallBack;


public class FragmentProfileInfo extends Fragment {
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_info, container, false);
    }

    @Override
    public void onStart()
    {
        super.onStart();

        // Set name:
        ((TextView) getView().findViewById(R.id.textViewFirstNameLabel)).setText(user.GetName());

        // Set Email:
        ((TextView) getView().findViewById(R.id.textViewEmailLabel)).setText(user.getEmail());

        // Set number of points
        ((TextView) getView().findViewById(R.id.textViewEmailLabel)).setText("Points: " + user.GetScore());

        // Set here active tasks number and completed task number:
        ((DBManager) Services.GetService(DBManager.class)).GetUserTasks(user.GetUserId(), new TasksCallBack()
        {
            @Override
            public void onSuccess(List<Task> tasks)
            {
                int completedTasksNumber = countCompletedTasks(tasks);
                //Set number of completed tasks:
                ((TextView) getView().findViewById(R.id.textViewCompletedTasks)).setText(
                        "Completed tasks: " + Integer.toString(completedTasksNumber));

                // Set active Tasks:
                ((TextView) getView().findViewById(R.id.textViewActiveTasks)).setText(
                        "Active tasks: " + Integer.toString(tasks.size() - completedTasksNumber));
            }

            @Override
            public void onFailure(String error)
            {

            }
        });
    }

    private int countCompletedTasks(List<Task> tasks)
    {
        int count = 0;
        for (Task task : tasks)
        {
            if (task.isCompleted())
            {
                count++;
            }
        }

        return count;
    }
}