package callback;

import java.util.ArrayList;

import lib.User;

/**
 * Created by razze on 09/03/2017.
 */

public interface UsersCallBack {
    void onSuccess(ArrayList<User> users);

    void onFailure(String error);
}
