package app.logic.Notification;

import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.project.homey.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import app.AppController;
import app.logic.appcomponents.User;
import app.logic.managers.DBManager;
import app.logic.managers.Services;
import app.logic.managers.SessionManager;
import callback.UpdateCallBack;
import callback.UserCallBack;


/**
 * Created by benro on 8/13/2017
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService
{
    private static final String TAG = "FCM Service";
    private final static String AUTH_KEY_FCM = "AAAAFuBcd3A:APA91bE2QzvvVOqKV-sX7P9v17Kylq-Rd5r_t0u1XPt5xrn4rUitJGPiKVziO5MQ6tqiiYycwFlhPYNyZbsECNRceqSundRAHzHRBoWgspKp_D-8Nfef5agnkdwNiCXkLZvZpJYM8ddI ";
    private final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage)
    {
        // TODO: Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated.

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_header_logo)
                        .setContentTitle("My notification")
                        .setContentText(remoteMessage.getNotification().getBody());
        NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(1, mBuilder.build());
    }

    public static void SendPushNotification(String userId, String message)
    {
        ((DBManager) Services.GetService(DBManager.class)).GetUser(Integer.parseInt(userId), new UserCallBack()
        {
            @Override
            public void onSuccess(User user)
            {
                AsyncTask sendNotificationTask = new AsyncTask()
                {
                    @Override
                    protected Object doInBackground(Object[] objects)
                    {
                        try
                        {
                            sendPushNotificationHelper(user.GetToken(), "Testing notifications");
                            return true;
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                            return true;
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                            return true;
                        }
                    }
                };
                sendNotificationTask.execute();
            }

            @Override
            public void onFailure(String error)
            {

            }
        });
    }

    private static String sendPushNotificationHelper(String token, String message) throws IOException, JSONException
    {
        String result = "";
        URL url = new URL(API_URL_FCM);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setDoOutput(true);

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "key=" + AUTH_KEY_FCM);
        conn.setRequestProperty("Content-Type", "application/json");

        JSONObject json = new JSONObject();

        json.put("to", token.trim());
        JSONObject info = new JSONObject();
        info.put("title", "notification title"); // Notification title
        info.put("body", "KARAMBA"); // Notification
        // body
        json.put("notification", info);
        try {
            OutputStreamWriter wr = new OutputStreamWriter(
                    conn.getOutputStream());
            wr.write(json.toString());
            wr.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }
            result = "Success";
        }
        catch (Exception e) {
            e.printStackTrace();
            result = "Failure";
        }
        System.out.println("GCM Notification is sent successfully");

        return result;
    }
}