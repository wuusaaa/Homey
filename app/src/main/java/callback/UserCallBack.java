package callback;

import app.logic.appcomponents.User;

/**
 * Created by razze on 27/02/2017.
 */

public interface UserCallBack {

    void onSuccess(User user);

    void onFailure(String error);
}
