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

    public <T extends IHasText> void SetTasks(List<T> tasks, Consumer<Task> callBack, Consumer<TaskLayout> checkBoxCallBack) {
        linearLayout.removeAllViews();
        taskLayouts.clear();

        linearLayout.setOrientation(LinearLayout.VERTICAL);
        AtomicInteger i = new AtomicInteger(0);

        tasks.stream()
                .filter(task -> !((Task) (task)).getStatus().equals(TaskStatus.COMPLETED.value()))
                .forEach(task -> {
                    TaskLayout taskLayout = new TaskLayout(this.getContext());
                    taskLayout.setTask((Task) task);
                    taskLayout.SetTaskLayoutOnClick(callBack);
                    taskLayout.setCheckBoxOnClick(checkBoxCallBack);
                    linearLayout.addView(taskLayout);

                    taskLayouts.add(taskLayout);

                    if (i.getAndIncrement() != tasks.size() - 1) {
                        linearLayout.addView(new Spacer(getContext()));
                    }
                });
    }

    public void visibleAllItems(Consumer<Task> callBack, Consumer<TaskLayout> checkBoxCallBack) {
        List<Task> taskToDisplay = new ArrayList<>();
        taskLayouts.forEach(taskLayout -> taskToDisplay.add(taskLayout.getTask()));
        SetTasks(taskToDisplay, callBack, checkBoxCallBack);
    }

    public void filterMyTasks(Consumer<Task> callBack, Consumer<TaskLayout> checkBoxCallBack) {
        String userId = ((SessionManager) (Services.GetService(SessionManager.class))).getUser().GetUserId();
        List<Task> tasksToDisplay = new ArrayList<>();
        List<TaskLayout> taskLayoutsCopy = new ArrayList<>();
        AtomicInteger i = new AtomicInteger(1);

        taskLayoutsCopy.addAll(taskLayouts);

        taskLayouts.forEach(taskLayout -> ((DBManager) Services.GetService(DBManager.class)).GetTaskUsersByTaskId(Integer.parseInt(taskLayout.getTask().GetTaskId()), new UsersCallBack() {
            @Override
            public void onSuccess(ArrayList<User> users) {
                if (isUserAssignee(userId, users)) {
                    tasksToDisplay.add(taskLayout.getTask());
                }

                if (i.getAndIncrement() == taskLayouts.size()) {
                    SetTasks(tasksToDisplay, callBack, checkBoxCallBack);
                    taskLayouts.addAll(taskLayoutsCopy);
                }
            }

            @Override
            public void onFailure(String error) {

            }
        }));

    }


    public void filterOthers(Consumer<Task> callBack, Consumer<TaskLayout> checkBoxCallBack) {
        String userId = ((SessionManager) (Services.GetService(SessionManager.class))).getUser().GetUserId();
        List<Task> tasksToDisplay = new ArrayList<>();
        List<TaskLayout> taskLayoutsCopy = new ArrayList<>();
        AtomicInteger i = new AtomicInteger(1);

        taskLayoutsCopy.addAll(taskLayouts);

        taskLayouts.forEach(taskLayout -> ((DBManager) Services.GetService(DBManager.class)).GetTaskUsersByTaskId(Integer.parseInt(taskLayout.getTask().GetTaskId()), new UsersCallBack() {
            @Override
            public void onSuccess(ArrayList<User> users) {
                if (!isUserAssignee(userId, users)) {
                    tasksToDisplay.add(taskLayout.getTask());
                }

                if (i.getAndIncrement() == taskLayouts.size()) {
                    SetTasks(tasksToDisplay, callBack, checkBoxCallBack);
                    taskLayouts.addAll(taskLayoutsCopy);
                }
            }

            @Override
            public void onFailure(String error) {

            }
        }));
    }

    private boolean isUserAssignee(String userId, ArrayList<User> users) {
        AtomicBoolean isIdFound = new AtomicBoolean(false);
        users.forEach(user -> {
            if (user.GetUserId().equals(userId))
                isIdFound.set(true);
        });

        return isIdFound.get();
    }
}
