package app.customcomponents;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.project.homey.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import app.activities.interfaces.IHasText;
import app.activities.interfaces.IonClicked;
import app.enums.TaskStatus;
import app.logic.appcomponents.Task;
import callback.GoToTaskPageCallBack;

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

    public <T extends IHasText> void SetTasks(ArrayList<T> tasks, GoToTaskPageCallBack callBack, IonClicked checkBoxCallBack) {
        linearLayout.removeAllViews();

        linearLayout.setOrientation(LinearLayout.VERTICAL);
        AtomicInteger i = new AtomicInteger(0);

        tasks.stream()
                .filter(task -> !((Task) (task)).getStatus().equals(TaskStatus.COMPLETED.value()))
                .forEach(task -> {
                    TaskLayout taskLayout = new TaskLayout(this.getContext());
                    taskLayout.setId(createIdForTaskLayout(i));
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

    private int createIdForTaskLayout(AtomicInteger i) {
        return R.id.homePageActivityTasksHolder + i.get() + 1;
    }


}
