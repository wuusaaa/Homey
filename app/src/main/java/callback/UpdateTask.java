package callback;

import android.content.Context;
import android.widget.Toast;

import com.project.homey.R;

/**
 * Created by barakm on 29/07/2017
 */

public class UpdateTask implements UpdateCallBack {
    private Context context;
    private int shouldPrintMassage;
    private int numOfTasks;
    private Runnable consumer;

    public UpdateTask(Context context, int numOfTasks) {
        this.context = context;
        shouldPrintMassage = 0;
        this.numOfTasks = numOfTasks;
    }

    public UpdateTask(Context baseContext, int size, Runnable loadTasks) {
        this.context = baseContext;
        shouldPrintMassage = 0;
        this.numOfTasks = size;
        this.consumer = loadTasks;
    }


    @Override
    public void onSuccess() {
        if (++shouldPrintMassage == numOfTasks) {
            Toast.makeText(context, R.string.tasksUpdatedSuccessfully, Toast.LENGTH_SHORT).show();
            consumer.run();
        }
    }

    //TODO: handle the error message
    @Override
    public void onFailure(String errorMessage) {
        Toast.makeText(context, R.string.tasksNotUpdatedSuccessfully, Toast.LENGTH_SHORT).show();
    }
}
