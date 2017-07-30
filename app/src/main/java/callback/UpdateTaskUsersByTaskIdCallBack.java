package callback;

/**
 * Created by barakm on 30/07/2017
 */

public interface UpdateTaskUsersByTaskIdCallBack {

    void onSuccess();

    void onFailure(String errorMessage);
}
