package callback;

import app.activities.interfaces.IHasImage;
import app.activities.interfaces.IHasText;

/**
 * Created by pdc5np on 7/6/2017
 */

public interface GotoGroupPageCallBack {

    <T extends IHasImage & IHasText> void onSuccess(T item);

    void onFailure(String error);
}
