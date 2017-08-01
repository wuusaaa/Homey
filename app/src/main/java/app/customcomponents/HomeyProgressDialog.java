package app.customcomponents;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by Raz on 7/6/2017
 */

public class HomeyProgressDialog extends ProgressDialog {

    //will count the amount of show dialog requests
    private int dialogSemaphore = 0;

    public HomeyProgressDialog(Context context) {
        super(context);
    }

    public void showDialog() {
        dialogSemaphore++;
        if (!this.isShowing())
            this.show();
    }

    public void hideDialog() {
        if (this.isShowing())
            dialogSemaphore--;
        //only hide the loader if there is no other loading component
        if (dialogSemaphore == 0) {
            this.dismiss();
        }
    }
}
