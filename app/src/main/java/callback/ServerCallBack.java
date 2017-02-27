package callback;

import org.json.JSONObject;

/**
 * Created by razze on 26/02/2017.
 */

public interface ServerCallBack {
    void onSuccess(JSONObject result);

    void onFailure(String result);
}
