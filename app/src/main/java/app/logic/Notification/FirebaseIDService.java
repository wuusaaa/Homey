package app.logic.Notification;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import app.logic.appcomponents.User;
import app.logic.managers.DBManager;
import app.logic.managers.Services;
import app.logic.managers.SessionManager;
import callback.UpdateCallBack;

/**
 * Created by benro on 8/13/2017
 */

public class FirebaseIDService extends FirebaseInstanceIdService {
    private static final String TAG = "FirebaseIDService";

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // TODO: Implement this method to send any registration to your app's servers.
        sendRegistrationToServer(refreshedToken);
    }

    /**
     * Persist token to third-party servers.
     * <p>
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        //Sending device id to the server:
        User user = ((SessionManager) Services.GetService(SessionManager.class)).getUser();
        if (user != null) {
            ((DBManager) Services.GetService(DBManager.class)).UpdateUser(user.GetUserId(), "token", token, new UpdateCallBack() {
                @Override
                public void onSuccess() {
                }

                @Override
                public void onFailure(String errorMessage) {
                }
            });
        }
    }
}
