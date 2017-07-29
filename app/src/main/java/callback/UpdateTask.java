package callback;

import android.content.Context;
import android.widget.Toast;

import com.project.homey.R;

/**
 * Created by barakm on 29/07/2017
 */

public class UpdateTask implements UpdateCallBack {
    private Context context;

    public UpdateTask(Context context) {
        this.context = context;
    }

    @Override
    public void onSuccess() {
        Toast.makeText(context, R.string.tasksUpdatedSuccessfully, Toast.LENGTH_SHORT).show();
    }

    //TODO: handle the error message
    @Override
    public void onFailure(String errorMessage) {
        Toast.makeText(context, R.string.tasksNotUpdatedSuccessfully, Toast.LENGTH_SHORT).show();
    }
}
