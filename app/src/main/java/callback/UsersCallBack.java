package callback;

import java.util.ArrayList;

import app.logic.appcomponents.User;

/**
 * Created by razze on 09/03/2017.
 */

public interface UsersCallBack {
    void onSuccess(ArrayList<User> users);

    void onFailure(String error);
}
