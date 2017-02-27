package db;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import app.AppController;
import callback.ServerCallBack;
import callback.UserCallBack;
import lib.User;
import services.EnvironmentManager;

/**
 * Created by Raz on 12/20/2016.
 */

public class DBManager {

    private DBDriver driver = new DBDriver();

    public void Login(final String email, final String password, final UserCallBack callBack) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                EnvironmentManager.GetInstance().GetLoginURL(), new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {

                        // Now store the user in SQLite
                        String uid = jObj.getString("uid");

                        JSONObject userObj = jObj.getJSONObject("user");
                        String name = userObj.getString("name");
                        String email = userObj.getString("email");
                        String created_at = userObj.getString("created_at");

                        // Inserting row in users table
                        User user = new User(name, email, uid, created_at);

                        callBack.onSuccess(user);
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        callBack.onFailure(errorMsg);
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    callBack.onFailure("JSON ERROR");
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                callBack.onFailure("Volley ERROR");
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    /**
     * Function to store user in MySQL database. will post params(name,
     * email, password) to register url
     */
    public void RegisterUser(final String name, final String email, final String password, final UserCallBack callBack) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                EnvironmentManager.GetInstance().GetRegistrationURL(), new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite
                        String uid = jObj.getString("uid");

                        JSONObject userObj = jObj.getJSONObject("user");
                        String name = userObj.getString("name");
                        String email = userObj.getString("email");
                        String created_at = userObj
                                .getString("created_at");

                        // Inserting row in users table
                        User user = new User(name, email, uid, created_at);

                        callBack.onSuccess(user);

                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        callBack.onFailure(errorMsg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    callBack.onFailure("JSON ERROR");
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                callBack.onFailure("Volley ERROR");
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    //TODO delete this when finishing testing
    public void test(final ServerCallBack callBack) {
        driver.RunSqlQuery("SELECT * FROM users", new ServerCallBack() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    boolean error = result.getBoolean("error");
                    if (!error) {
                        callBack.onSuccess(new JSONObject().put("res", result.getString("result")));
                    } else {
                        callBack.onFailure(result.getString("error_msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String result) {
                callBack.onFailure(result);
            }
        });
    }
}
