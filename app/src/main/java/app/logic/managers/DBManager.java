package app.logic.managers;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import app.AppController;
import app.database.DBDriver;
import app.logic.appcomponents.Group;
import app.logic.appcomponents.Task;
import app.logic.appcomponents.User;
import callback.GroupCallBack;
import callback.GroupsCallBack;
import callback.ServerCallBack;
import callback.TaskCallBack;
import callback.TasksCallBack;
import callback.UserCallBack;

/**
 * Created by Raz on 12/20/2016
 */

public class DBManager extends ManagerBase {

    private DBDriver driver = new DBDriver();

    public void Login(final String email, final String password, final UserCallBack callBack) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                ((EnvironmentManager) (Services.GetService(EnvironmentManager.class))).GetLoginURL(), new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {

                        // Now store the user in SQLite
                        JSONObject userObj = jObj.getJSONObject("user");
                        String id = userObj.getString("id");
                        String name = userObj.getString("name");
                        String email = userObj.getString("email");
                        String created_at = userObj.getString("created_at");

                        // Inserting row in users table
                        User user = new User(name, email, created_at, Integer.parseInt(id));

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
        }, error -> callBack.onFailure("Volley ERROR")) {

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
     * Function to store user in MySQL app.database. will post params(name,
     * email, password) to register url
     */
    public void RegisterUser(final String name, final String email, final String password, final UserCallBack callBack) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                ((EnvironmentManager) (Services.GetService(EnvironmentManager.class))).GetRegistrationURL(), new Response.Listener<String>() {

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

                        User user = new User(name, email, created_at, Integer.parseInt(uid));

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

    /**
     * Function to restore user's password
     */
    public void ResetPassword(final String email, final ServerCallBack callBack) {
        // Tag used to cancel the request
        String tag_string_req = "req_reset_pass";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                ((EnvironmentManager) (Services.GetService(EnvironmentManager.class))).GetAPIPassResetURL(), new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // pass successfully reset

                        callBack.onSuccess(new JSONObject().put("error", "false"));

                    } else {

                        // Error occurred in reset password. Get the error
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
                // Posting params to reset url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public void RemoveGroup(final int groupId, final ServerCallBack callBack) {
        removeFromTable("group", groupId, callBack);
    }

    public void RemoveTask(final int taskId, final ServerCallBack callBack) {
        removeFromTable("task", taskId, callBack);
    }

    private void removeFromTable(final String table, final int id, final ServerCallBack callBack) {
        //TODO add this to the API server
    }

    private void ChangePassword(final int userId, final String oldPass, final String newPass) {

    }

    public void UpdateUser(final int userId, final String property, final Object value, final ServerCallBack callBack) {
        updateTableValue("user", userId, property, value, callBack);
    }

    public void UpdateTask(final int taskId, final String property, final Object value, final ServerCallBack callBack) {
        updateTableValue("task", taskId, property, value, callBack);
    }

    public void UpdateGroup(final int groupId, final String property, final Object value, final ServerCallBack callBack) {
        updateTableValue("group", groupId, property, value, callBack);
    }

    private void updateTableValue(final String table, final int id, final String property, final Object value, final ServerCallBack callBack) {
        //TODO add this to the API server
        // Tag used to cancel the request
        String tag_string_req = "update_value";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                ((EnvironmentManager) (Services.GetService(EnvironmentManager.class))).GetAPIUpdateTableValueURL(), new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean error = jsonObject.getBoolean("error");
                    if (!error) {// Task successfully stored in MySQL
                        //all OK, no need to do anything just go back to the called function
                        callBack.onSuccess(jsonObject);
                    } else {

                        // Error occurred while adding a task. Get the error
                        // message
                        String errorMsg = jsonObject.getString("error_msg");
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
                params.put("table", table);
                params.put("property", property);
                params.put("id", id + "");
                params.put("value", value.toString());

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public void AddTask(final String name, final String description, final int creatorId, final int groupId, final String status, final String location, final Date startTime, final Date endTime, final TaskCallBack callBack) {
        // Tag used to cancel the request
        String tag_string_req = "add_task";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                ((EnvironmentManager) (Services.GetService(EnvironmentManager.class))).GetAPIAddTaskURL(), new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean error = jsonObject.getBoolean("error");
                    if (!error) {// Task successfully stored in MySQL
                        String name = jsonObject.getString("name");
                        String id = jsonObject.getString("id");
                        String description = jsonObject.getString("description");
                        int creatorId = jsonObject.getInt("creator_id");
                        int groupId = jsonObject.getInt("group_id");
                        String status = jsonObject.getString("status");
                        String location = jsonObject.getString("location");
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        Date startTime = dateFormat.parse(jsonObject.getString("start_time"));
                        Date endTime = dateFormat.parse(jsonObject.getString("end_time"));
                        callBack.onSuccess(new Task(id, name, groupId, description, status, location, creatorId, startTime, endTime));
                    } else {

                        // Error occurred while adding a task. Get the error
                        // message
                        String errorMsg = jsonObject.getString("error_msg");
                        callBack.onFailure(errorMsg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    callBack.onFailure("JSON ERROR");
                } catch (ParseException e) {
                    e.printStackTrace();
                    callBack.onFailure("PARSE ERROR");
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
                params.put("description", description);
                params.put("creator_id", creatorId + "");
                params.put("group_id", groupId + "");
                params.put("status", status);
                params.put("location", location);
                params.put("start_time", new java.sql.Timestamp(startTime.getTime()).toString());
                params.put("end_time", new java.sql.Timestamp(endTime.getTime()).toString());

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public void GetUser(final int userId, final UserCallBack callBack) {
        // Tag used to cancel the request
        String tag_string_req = "req_get_user";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                ((EnvironmentManager) (Services.GetService(EnvironmentManager.class))).GetAPIGetUserURL(), new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User successfully pulled from MySQL
                        int id = jObj.getInt("uid");
                        JSONObject userObj = jObj.getJSONObject("user");
                        String name = userObj.getString("name");
                        String email = userObj.getString("email");
                        String created_at = userObj.getString("created_at");

                        User user = new User(name, email, created_at, id);
                        callBack.onSuccess(user);

                    } else {

                        // Error occurred in getting user. Get the error
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
                // Posting params to getting user url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", userId + "");

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public void AddGroup(final int creatorId, final String name, final byte[] img, final GroupCallBack callBack) {
        // Tag used to cancel the request
        String tag_string_req = "add_group";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                ((EnvironmentManager) (Services.GetService(EnvironmentManager.class))).GetAPIAddGroupURL(), new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {// Group successfully stored in MySQL
                        String name = jObj.getString("name");
                        String id = jObj.getString("id");
                        byte[] img = jObj.getString("img").getBytes();
                        callBack.onSuccess(new Group(id, name, img));
                    } else {

                        // Error occurred while adding a group. Get the error
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
                // Posting params to adding group url
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("creator_id", creatorId + "");
                params.put("created", new java.sql.Timestamp(new Date().getTime()).toString());
                params.put("img", Arrays.toString(img));

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public void GetGroup(final int groupId, final GroupCallBack callBack) {
        // Tag used to cancel the request
        String tag_string_req = "req_get_group";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                ((EnvironmentManager) (Services.GetService(EnvironmentManager.class))).GetAPIGetGroupURL(), new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User successfully pulled from MySQL
                        String id = jObj.getString("id");
                        String name = jObj.getString("name");
                        String createdStr = jObj.getString("created");
                        byte[] img = jObj.getString("img").getBytes();

                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        Date created = dateFormat.parse(createdStr);

                        Group group = new Group(id, name, img);

                        callBack.onSuccess(group);

                    } else {

                        // Error occurred in getting group. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        callBack.onFailure(errorMsg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    callBack.onFailure("JSON ERROR");
                } catch (ParseException e) {
                    e.printStackTrace();
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
                // Posting params to group url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", groupId + "");

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public void GetUserTasks(final int userId, final TasksCallBack callBack) {
        // Tag used to cancel the request
        String tag_string_req = "req_get_user_tasks";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                ((EnvironmentManager) (Services.GetService(EnvironmentManager.class))).GetAPIGetTasksByUserIdURL(), new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        ArrayList<Task> tasks = new ArrayList<Task>();
                        JSONArray resArr = new JSONArray(jObj.getString("result"));
                        for (int i = 0; i < resArr.length(); i++) {
                            JSONObject obj = resArr.getJSONObject(i);
                            Log.d("debug", obj.toString());
                            String id = obj.getString("id");
                            int groupId = obj.getInt("group_id");
                            String name = obj.getString("name");
                            String description = obj.getString("description");
                            String status = obj.getString("status");
                            int creatorId = obj.getInt("creator_id");
                            String location = obj.getString("location");
                            String startTimeStr = obj.getString("start_time");
                            String endTimeStr = obj.getString("end_time");

                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                            Date startTime = dateFormat.parse(startTimeStr);
                            Date endTime = dateFormat.parse(endTimeStr);

                            Task task = new Task(id, name, groupId, description, status, location, creatorId, startTime, endTime);

                            tasks.add(task);
                        }
                        callBack.onSuccess(tasks);

                    } else {

                        // Error occurred in getting tasks. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        callBack.onFailure(errorMsg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    callBack.onFailure("JSON ERROR");
                } catch (ParseException e) {
                    e.printStackTrace();
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
                // Posting params to tasks url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", userId + "");

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public void GetGroupTasks(final int groupId, final TasksCallBack callBack) {

        // Tag used to cancel the request
        String tag_string_req = "req_get_user_tasks_by_group";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                ((EnvironmentManager) (Services.GetService(EnvironmentManager.class))).GetAPIGetTasksByGroupIdURL(), new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {

                        ArrayList<Task> tasks = new ArrayList<Task>();
                        JSONArray resArr = new JSONArray(jObj.getString("result"));
                        Log.d("debug:GetGroupTasks", resArr.toString());
                        for (int i = 0; i < resArr.length(); i++) {
                            JSONObject obj = resArr.getJSONObject(i);
                            String id = obj.getString("id");
                            int groupId = obj.getInt("group_id");
                            String name = obj.getString("name");
                            String description = obj.getString("description");
                            String status = obj.getString("status");
                            int creatorId = obj.getInt("creator_id");
                            String location = obj.getString("location");
                            String startTimeStr = obj.getString("start_time");
                            String endTimeStr = obj.getString("end_time");

                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                            Date startTime = dateFormat.parse(startTimeStr);
                            Date endTime = dateFormat.parse(endTimeStr);

                            Task task = new Task(id, name, groupId, description, status, location, creatorId, startTime, endTime);

                            tasks.add(task);
                        }
                        callBack.onSuccess(tasks);

                    } else {

                        // Error occurred in getting tasks. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        callBack.onFailure(errorMsg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    callBack.onFailure("JSON ERROR");
                } catch (ParseException e) {
                    e.printStackTrace();
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
                // Posting params to tasks url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", groupId + "");

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public void GetUserGroups(final int userId, final GroupsCallBack callBack) {
        // Tag used to cancel the request
        String tag_string_req = "req_get_user_groups";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                ((EnvironmentManager) (Services.GetService(EnvironmentManager.class))).GetAPIGetUserGroupsURL(), new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        ArrayList<Group> groups = new ArrayList<Group>();
                        JSONArray resArr = new JSONArray(jObj.getString("result"));
                        Log.d("debug", resArr.toString());
                        for (int i = 0; i < resArr.length(); i++) {
                            JSONObject obj = resArr.getJSONObject(i);
                            String id = obj.getString("id");
                            String name = obj.getString("name");
                            String createdStr = obj.getString("created");
                            byte[] img = obj.getString("img").getBytes();

                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                            Date created = dateFormat.parse(createdStr);

                            Group group = new Group(id, name, img);

                            groups.add(group);
                        }
                        callBack.onSuccess(groups);

                    } else {

                        // Error occurred in getting groups. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        callBack.onFailure(errorMsg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    callBack.onFailure("JSON ERROR");
                } catch (ParseException e) {
                    e.printStackTrace();
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
                // Posting params to groups url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", userId + "");

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
