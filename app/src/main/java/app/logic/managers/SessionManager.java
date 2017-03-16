package app.logic.managers;

/**
 * Created by razze on 18/02/2017.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import java.util.HashMap;

import app.logic.lib.User;
import db.SQLiteHandler;

public class SessionManager extends ManagerBase {

    public SessionManager() {
        super();
    }

    public void set_context(Context _context) {
        this._context = _context;
        this.pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        this.editor = pref.edit();
    }


    /**
     * provide the instance of this class
     *
     * @return EnvironmentManager
     */

    private User user;

    // LogCat tag
    private static String TAG = SessionManager.class.getSimpleName();

    // Shared Preferences
    private SharedPreferences pref;

    private Editor editor;
    private Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "AndroidHiveLogin";

    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";

    public void setLogin(boolean isLoggedIn) {

        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);

        // commit changes
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }

    public boolean isLoggedIn() {
        boolean res = pref.getBoolean(KEY_IS_LOGGEDIN, false);

        if (res && user == null) {
            SQLiteHandler db = new SQLiteHandler(_context);
            HashMap<String, String> userCred = db.getUserDetails();
            user = new User(userCred.get("name"), userCred.get("email"), userCred.get("uid"), userCred.get("created_at"));
        }

        return res;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}