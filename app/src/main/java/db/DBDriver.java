package db;

import android.util.Log;

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
import services.EnvironmentManager;

/**
 * Created by Raz on 12/20/2016.
 *
 * DBDriver - uses PHP api to run the queries to the SQL DB
 */

public class DBDriver {
    //TODO probably should be a singleton

    private static final String TAG = DBDriver.class.getSimpleName();

    /**
     * A function that gets a sql query and a callback method and runs the query with the help of the PHP api service
     *
     * @param query
     * @param callback
     */
    public void RunSqlQuery(final String query, final ServerCallBack callback) {
        // Tag used to cancel the request
        String tag_string_req = "sql_query";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                EnvironmentManager.GetInstance().GetAPIServerURL(), new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject result = new JSONObject(response);
                    callback.onSuccess(result);

                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();

                    callback.onFailure("Json Obj error");
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Query Error: " + error.getMessage());
                callback.onFailure("Query Error: " + error.getMessage());
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to query api
                Map<String, String> params = new HashMap<String, String>();
                params.put("token", EnvironmentManager.GetInstance().GetServerSecurityToken());
                params.put("query", query);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }
}
