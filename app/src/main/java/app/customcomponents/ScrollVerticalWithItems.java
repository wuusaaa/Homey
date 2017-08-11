package app.customcomponents;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import app.activities.interfaces.IHasText;
import app.enums.TaskStatus;
import app.logic.appcomponents.Task;
import app.logic.appcomponents.User;
import app.logic.managers.DBManager;
import app.logic.managers.Services;
import app.logic.managers.SessionManager;
import callback.UsersCallBack;

public class ScrollVerticalWithItems extends ScrollView {

    private final LinearLayout linearLayout;

    private final List<TaskLayout> taskLayouts = new ArrayList<>();
    private final List<TaskLayout> spinnerOwner = new ArrayList<>();
    private final List<TaskLayout> spinnerStatus = new ArrayList<>();

    public ScrollVerticalWithItems(Context context) {
        super(context);

        linearLayout = new LinearLayout(context);
        this.addView(linearLayout);
    }

    public ScrollVerticalWithItems(Context context, AttributeSet attrs) {
        super(context, attrs);

        linearLayout = new LinearLayout(context);
        this.addView(linearLayout);
    }

    public ScrollVerticalWithItems(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        linearLayout = new LinearLayout(context);
        this.addView(linearLayout);
    }

    public <T extends IHasText> void SetTasks(List<T> tasks, Consumer<Task> callBack, Consumer<TaskLayout> checkBoxCallBack)
    {
        linearLayout.removeAllViews();
        taskLayouts.clear();
        spinnerStatus.clear();
        spinnerOwner.clear();

        linearLayout.setOrientation(LinearLayout.VERTICAL);
        AtomicInteger i = new AtomicInteger(0);

        tasks.forEach(task ->
        {
            TaskLayout taskLayout = new TaskLayout(this.getContext());
            taskLayout.setTask((Task) task);
            taskLayout.SetTaskLayoutOnClick(callBack);
            taskLayout.setCheckBoxOnClick(checkBoxCallBack);
            linearLayout.addView(taskLayout);

            if (((Task) task).getStatus().equals(TaskStatus.COMPLETED.value()))
                taskLayout.setCheckBox(true);

            taskLayouts.add(taskLayout);
            spinnerOwner.add(taskLayout);
            spinnerStatus.add(taskLayout);

            if (i.getAndIncrement() != tasks.size() - 1) {
                linearLayout.addView(new Spacer(getContext()));
            }
        });
    }

    //--------Filter function by owner--------//
    public void showAllTasksOwners() {
        spinnerOwner.clear();
        spinnerOwner.addAll(taskLayouts);
        setFilteredTasks();
    }

    public void filterMyTasks() {
        String userId = ((SessionManager) (Services.GetService(SessionManager.class))).getUser().GetUserId();
        AtomicInteger i = new AtomicInteger(1);

        spinnerOwner.clear();

        taskLayouts.forEach(taskLayout -> ((DBManager) Services.GetService(DBManager.class)).GetTaskUsersByTaskId(Integer.parseInt(taskLayout.getTask().GetTaskId()), new UsersCallBack() {
            @Override
            public void onSuccess(ArrayList<User> users)
            {
                if (isUserAssignee(userId, users)) {
                    spinnerOwner.add(taskLayout);
                }

                if (i.getAndIncrement() == taskLayouts.size()) {
                    setFilteredTasks();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        }));
    }

    public void filterOthers() {
        String userId = ((SessionManager) (Services.GetService(SessionManager.class))).getUser().GetUserId();
        AtomicInteger i = new AtomicInteger(1);

        spinnerOwner.clear();

        taskLayouts.forEach(taskLayout -> ((DBManager) Services.GetService(DBManager.class)).GetTaskUsersByTaskId(Integer.parseInt(taskLayout.getTask().GetTaskId()), new UsersCallBack() {
            @Override
            public void onSuccess(ArrayList<User> users) {
                if (!isUserAssignee(userId, users)) {
                    spinnerOwner.add(taskLayout);
                }

                if (i.getAndIncrement() == taskLayouts.size()) {
                    setFilteredTasks();
                }
            }

            @Override
            public void onFailure(String error) {
            }
        }));
    }
    //-------------------------------------------//


    //--------Filter function by tasks status--------//
    public void showAllTasks() {
        spinnerStatus.clear();
        spinnerStatus.addAll(taskLayouts);
        setFilteredTasks();
    }

    public void showCompletedTasks() {
        spinnerStatus.clear();

        taskLayouts.stream()
                .filter(taskLayout -> taskLayout.getTask().getStatus().equals(TaskStatus.COMPLETED.value()))
                .forEach(spinnerStatus::add);

        setFilteredTasks();
    }

    public void showIncompleteTasks() {
        spinnerStatus.clear();

        taskLayouts.stream()
                .filter(taskLayout -> !taskLayout.getTask().getStatus().equals(TaskStatus.COMPLETED.value()))
                .forEach(spinnerStatus::add);

        setFilteredTasks();
    }

    //-------------------------------------------//

    private boolean isUserAssignee(String userId, ArrayList<User> users) {
        AtomicBoolean isIdFound = new AtomicBoolean(false);

        users.forEach(user -> {
            if (user.GetUserId().equals(userId))
                isIdFound.set(true);
        });

        return isIdFound.get();
    }

    private void setFilteredTasks() {
        //TODO: BARAK FIX THIS FACAKTA !
        AtomicInteger i = new AtomicInteger(0);
        List<TaskLayout> intersectTasks = intersection();

        linearLayout.removeAllViews();
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        intersectTasks.forEach(taskLayout -> {
            linearLayout.addView(taskLayout);

            if (i.getAndIncrement() != spinnerOwner.size() - 1) {
                linearLayout.addView(new Spacer(getContext()));
            }
        });
    }

    private List<TaskLayout> intersection() {

        return spinnerOwner.stream().filter(spinnerStatus::contains).collect(Collectors.toList());
    }

    //Allow assign task to user by drag and drop:
    public void AllotDragAndDrop()
    {
        taskLayouts.forEach(taskLayout -> taskLayout.setDragAndDrop());
    }

}

