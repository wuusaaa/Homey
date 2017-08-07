package app.logic.managers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import app.activities.GroupPageActivity;
import app.activities.HomePageActivity;
import app.activities.LogoutActivity;
import app.activities.PlusActivity;
import app.activities.ProfileActivity;
import app.activities.SettingsActivity;
import app.activities.TaskActivity;
import app.logic.appcomponents.Group;
import app.logic.appcomponents.Task;
import app.logic.appcomponents.User;

/**
 * Created by benro on 8/7/2017.
 */

public class ActivityChangeManager extends ManagerBase
{
    public void SetGroupActivity(Context context, Group group)
    {
        Intent intent = new Intent(context, GroupPageActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("group", group);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public void SetTaskActivity(Context context, Task task)
    {
        Intent intent = new Intent(context, TaskActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("task", task);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public void SetHomeActivity(Context context)
    {
        Intent intent = new Intent(context, HomePageActivity.class);
        context.startActivity(intent);
    }

    public void SetPlusActivity(Context context)
    {
        Intent intent = new Intent(context, PlusActivity.class);
        context.startActivity(intent);
    }

    public void SetSettingsActivity(Context context)
    {
        Intent intent = new Intent(context, SettingsActivity.class);
        context.startActivity(intent);
    }

    public void SetProfileActivity(Context context, User user)
    {
        Intent intent = new Intent(context, ProfileActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("user", user);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public void SetLogOutActivity(Context context)
    {
        Intent intent = new Intent(context, LogoutActivity.class);
        context.startActivity(intent);
    }
}
