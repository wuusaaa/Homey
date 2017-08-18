package app.logic.Notification;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Map;

import app.AppController;
import app.logic.managers.SessionManager;
import callback.UpdateCallBack;


/**
 * Created by benro on 8/13/2017
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FCM Service";
    private static final String SERVER_KEY =
            "AAAAFuBcd3A:APA91bE2QzvvVOqKV-sX7P9v17Kylq-Rd5r_t0u1XPt5xrn4rUitJGPiKVziO5MQ6tqiiYycwFlhPYNyZbsECNRceqSundRAHzHRBoWgspKp_D-8Nfef5agnkdwNiCXkLZvZpJYM8ddI";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO: Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated.

        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
    }


    //Trying to generate notification to my self.
    public void generateNotification(String message, Context context) {
        String token = SessionManager.getToken();

        sendNotification(token, "BIMBA", SERVER_KEY, new UpdateCallBack() {
            @Override
            public void onSuccess() {
                Toast.makeText(context, "NOTIFICATION SENT", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendNotification(final String to, final String data, final String token, final UpdateCallBack callBack) {
        String tag_string_req = "post";
        StringRequest strReq = new StringRequest(Request.Method.POST, "https://fcm.googleapis.com/fcm/send", response -> {

            //your response here
        }, error -> callBack.onFailure("VOLLEY ERROR")) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                params.put("Authorization:key", token);

                return params;
            }

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<>();
                params.put("to", to);
                params.put("data", data);

                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}