package app.logic.managers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import app.activities.GroupPageActivity;
import app.activities.HomePageActivity;
import app.activities.LogoutActivity;
import app.activities.PlusActivity;
import app.activities.ProfileActivity;
import app.activities.SettingsActivity;
import app.activities.TaskActivity;
import app.customcomponents.HomeyProgressDialog;
import app.logic.appcomponents.Group;
import app.logic.appcomponents.Task;
import app.logic.appcomponents.User;
import callback.UsersCallBack;

/**
 * Created by benro on 8/7/2017
 */

public class ActivityChangeManager extends ManagerBase
{
    public void SetGroupActivity(Context context, Group group)
    {
        HomeyProgressDialog pDialog = new HomeyProgressDialog(context);
        final List<Boolean> isAdmin = new ArrayList<>();

        ((DBManager) Services.GetService(DBManager.class)).GetGroupAdmins(Integer.parseInt(group.GetId()), new UsersCallBack() {
            @Override
            public void onSuccess(ArrayList<User> users) {
                isAdmin.add(false);
                User theUser = ((SessionManager) Services.GetService(SessionManager.class)).getUser();
                for (User user : users)
                {
                    if (user.GetUserId().equals(theUser.GetUserId()))
                    {
                        isAdmin.remove(0);
                        isAdmin.add(true);
                        break;
                    }
                }

                Intent intent = new Intent(context, GroupPageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("group", group);
                bundle.putBoolean("isAdmin", isAdmin.get(0));
                intent.putExtras(bundle);
                context.startActivity(intent);
            }

            @Override
            public void onFailure(String error) {
            }
        });
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
        Bundle bundle = new Bundle();
        bundle.putInt("fragmentNumber", PlusActivity.NONE);
        intent.putExtras(bundle);
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

    public void SetAddTaskActivity(Context context, Group group)
    {

    }

    public void SetAddMemberActivity(Context context, String groupName)
    {
        Intent intent = new Intent(context, PlusActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("fragmentNumber", PlusActivity.ADD_MEMBER);
        bundle.putString("groupName", groupName);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
