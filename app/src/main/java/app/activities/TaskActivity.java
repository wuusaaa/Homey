package app.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.project.homey.R;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import app.activities.interfaces.IHasImage;
import app.activities.interfaces.IHasText;
import app.customcomponents.CircleImageButton;
import app.customcomponents.ScrollHorizontalWithItems;
import app.enums.TaskProperty;
import app.enums.TaskStatus;
import app.logic.appcomponents.Group;
import app.logic.appcomponents.Task;
import app.logic.appcomponents.User;
import app.logic.managers.ActivityChangeManager;
import app.logic.managers.DBManager;
import app.logic.managers.Services;
import app.logic.managers.SessionManager;
import app.logic.managers.TaskManager;
import callback.GotoGroupPageCallBack;
import callback.GroupCallBack;
import callback.UpdateCallBack;
import callback.UpdateTask;
import callback.UserCallBack;
import callback.UsersCallBack;


public class TaskActivity extends ActivityWithHeaderBase {

    private Task myTask;
    private Group taskGroup;
    private User taskCreator;
    private ScrollHorizontalWithItems taskAssignees;
    private List<User> taskAssigneesList;
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        myTask = getIntent().getExtras().getParcelable("task");
        dbManager = (DBManager) Services.GetService(DBManager.class);

        setActivityComponents();
        setTaskInfo();
    }

    private void setActivityComponents() {
        Context context = this;

        //Get task's creator user.
        dbManager.GetUser(myTask.GetCreatorId(), new UserCallBack() {
            @Override
            public void onSuccess(User user) {
                taskCreator = user;
                ((TextView) findViewById(R.id.taskActivityCreatorLabel)).setText(taskCreator.GetName());
                CircleImageButton creatorImage = (CircleImageButton) findViewById(R.id.taskActivityCreatorImage);
                creatorImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((ActivityChangeManager) Services.GetService(ActivityChangeManager.class)).SetProfileActivity(context, taskCreator);
                    }
                });

                //CREATOR IMAGE
                setButtonImage(creatorImage, taskCreator.GetImage(), R.mipmap.ic_profile_default);
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(TaskActivity.this, "Creator id " + error, Toast.LENGTH_SHORT).show();
            }
        });

        //Get task's Group.
        dbManager.GetGroup(myTask.GetGroupId(), new GroupCallBack() {
            @Override
            public void onSuccess(Group group) {
                taskGroup = group;
                ((TextView) findViewById(R.id.taskActivityGroupLabel)).setText(taskGroup.GetName());
                CircleImageButton groupImage = (CircleImageButton) findViewById(R.id.taskActivityGroupImage);

                groupImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((ActivityChangeManager) Services.GetService(ActivityChangeManager.class)).SetGroupActivity(context, taskGroup);
                    }
                });

                //GROUP IMAGE
                setButtonImage(groupImage, taskGroup.GetImage(), R.mipmap.ic_task_default);
            }

            @Override
            public void onFailure(String error) {
                ((TextView) findViewById(R.id.taskActivityGroupLabel)).setText("error");
                Toast.makeText(TaskActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setButtonImage(CircleImageButton buttonImage, byte[] image, int defaultImgId) {
        buttonImage.setImageBytes(image, defaultImgId);
    }

    private void setTaskInfo() {
        setScreenName(myTask.GetName());
        Context context = this;

//        if (myTask.GetStartTime() != null) {
//            ((TextView) findViewById(R.id.taskActivityStartDate)).setText(myTask.GetStartTime().toString());
//        } else {
//            ((TextView) findViewById(R.id.taskActivityStartDate)).setText("-");
//        }
//
//        if (myTask.GetEndTime() != null) {
//            ((TextView) findViewById(R.id.taskActivityEndDate)).setText(myTask.GetEndTime().toString());
//        } else {
//            ((TextView) findViewById(R.id.taskActivityEndDate)).setText("-");
//        }

        ((TextView) findViewById(R.id.taskActivityScore)).setText(String.valueOf(myTask.getScore()));

        ((TextView) findViewById(R.id.taskActivityDescription)).setText(myTask.GetDescription());

        //TASK IMAGE:
        CircleImageButton taskImageButton = (CircleImageButton) findViewById(R.id.taskActivityTaskImage);
        setButtonImage(taskImageButton, myTask.GetImg(), R.mipmap.ic_task_default);

        //Task Participants:
        dbManager.GetTaskUsersByTaskId(Integer.parseInt(myTask.GetTaskId()), new UsersCallBack() {
            @Override
            public void onSuccess(ArrayList<User> users) {
                taskAssigneesList = users;
                taskAssignees = (ScrollHorizontalWithItems) findViewById(R.id.taskActivityTaskAssignee);
                Button takeButton = (Button) findViewById(R.id.taskActivityTakeButton);
                takeButton.setEnabled(true);

                taskAssignees.SetScrollerItems(users, LinearLayoutCompat.HORIZONTAL, new GotoGroupPageCallBack() {
                    @Override
                    public <T extends IHasImage & IHasText> void onSuccess(T user) {
                        ((ActivityChangeManager) Services.GetService(ActivityChangeManager.class)).SetProfileActivity(context, (User) user);
                    }

                    @Override
                    public void onFailure(String error) {

                    }
                });
            }

            @Override
            public void onFailure(String error) {
            }
        });
    }

    public void buttonCompleteOnClicked(View view)
    {
        UpdateCallBack updateCallBack = new UpdateTask(this.getBaseContext(), 1);
        User user = ((SessionManager) Services.GetService(SessionManager.class)).getUser();

        ((TaskManager) Services.GetService(TaskManager.class)).CompleteTask(myTask, updateCallBack);
    }

    public void buttonTakeOnClicked(View view) {
        String userId = ((SessionManager) (Services.GetService(SessionManager.class))).getUser().GetUserId();
        List<User> taskAssigneesStream = taskAssigneesList.stream().filter(new Predicate<User>() {
            @Override
            public boolean test(User user) {
                return user.GetUserId().equals(userId);
            }
        }).collect(Collectors.toList());

        if (taskAssigneesStream.size() == 0) {
            dbManager.AddUserToTask(userId, myTask.GetTaskId(), new UpdateCallBack() {
                @Override
                public void onSuccess() {
                    Toast.makeText(getBaseContext(), "You get the task!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(String errorMessage) {
                    Toast.makeText(getBaseContext(), "There was a problem, try again.", Toast.LENGTH_SHORT).show();

                }
            });
        } else {

            Toast.makeText(getBaseContext(), "You got this task already", Toast.LENGTH_SHORT).show();
        }
    }

    public void buttonDeleteTaskOnClicked(View view) {
        User selfUser = ((SessionManager) Services.GetService(SessionManager.class)).getUser();

        if (selfUser.GetId().equals(taskCreator.GetId())) {
            dbManager.RemoveTask(myTask.GetTaskId(), new UpdateCallBack() {
                @Override
                public void onSuccess() {
                    Toast.makeText(TaskActivity.this, "Task Deleted!", Toast.LENGTH_SHORT).show();
                    ((ActivityChangeManager) Services.GetService(ActivityChangeManager.class)).back(new Supplier<AppCompatActivity>() {
                        @Override
                        public AppCompatActivity get() {
                            return getAppCompatActivity();
                        }
                    });

                }

                @Override
                public void onFailure(String errorMessage) {
                    Toast.makeText(TaskActivity.this, "Couldn't delete Task", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else
        {
            Toast.makeText(TaskActivity.this, "Only task creator can delete it!", Toast.LENGTH_SHORT).show();
        }
    }
}
