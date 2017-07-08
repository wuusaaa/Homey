package app.logic.managers;

/**
 * Created by Raz on 2/22/2017.
 * A singleton to set all the environment variables
 */

public class EnvironmentManager extends ManagerBase {

    public EnvironmentManager() {
        super();
    }

    // Server user login url
    private static final String LoginURL = "https://www.crossword-clues.com/homey/login.php";
    // Server user register url
    private static final String registrationURL = "https://www.crossword-clues.com/homey/register.php";

    private final String APIServerURL = "https://www.crossword-clues.com/homey/sql.php";

    private final String APIPassResetURL = "https://www.crossword-clues.com/homey/forgot.php";

    private final String APIAddTaskURL = "https://www.crossword-clues.com/homey/add-task.php";

    private final String APIAddGroupURL = "https://www.crossword-clues.com/homey/add-group.php";

    private final String APIGetUserGroupsURL = "https://www.crossword-clues.com/homey/get-user-groups.php";

    private final String APIGetUserURL = "https://www.crossword-clues.com/homey/get-user.php";

    private final String APIGetGroupURL = "https://www.crossword-clues.com/homey/get-group.php";

    private final String APIGetTasksByGroupIdURL = "https://www.crossword-clues.com/homey/get-tasks-by-group-id.php";

    private final String APIGetTasksByUserIdURL = "https://www.crossword-clues.com/homey/get-tasks-by-user-id.php";

    private final String serverSecurityToken = "&t5h5th%TH&&gr4gjkbddr%THsdfd21";

    private String screenName;


    /**
     * getter for the LoginURL
     *
     * @return String
     */
    public String GetLoginURL() {
        return LoginURL;
    }


    /**
     * getter for the registrationURL
     *
     * @return String
     */
    public String GetRegistrationURL() {
        return registrationURL;
    }

    /**
     * getter for the APIServerURL
     *
     * @return String
     */
    public String GetAPIServerURL() {
        return APIServerURL;
    }

    /**
     * getter for the serverSecurityToken
     *
     * @return String
     */
    public String GetServerSecurityToken() {
        return serverSecurityToken;
    }

    /**
     * getter for the GetAPIPassResetURL
     *
     * @return String
     */
    public String GetAPIPassResetURL() {
        return APIPassResetURL;
    }

    /**
     * getter for the GetAPIAddTaskURL
     *
     * @return String
     */
    public String GetAPIAddTaskURL() {
        return APIAddTaskURL;
    }

    /**
     * getter for the GetAPIAddGroupURL
     *
     * @return String
     */
    public String GetAPIAddGroupURL() {
        return APIAddGroupURL;
    }

    /**
     * getter for the GetAPIGetUserURL
     *
     * @return String
     */
    public String GetAPIGetUserURL() {
        return APIGetUserURL;
    }

    /**
     * getter for the GetAPIGetGroupURL
     *
     * @return String
     */
    public String GetAPIGetGroupURL() {
        return APIGetGroupURL;
    }

    /**
     * getter for the GetAPIGetUserGroupsURL
     *
     * @return String
     */
    public String GetAPIGetUserGroupsURL() {
        return APIGetUserGroupsURL;
    }

    /**
     * getter for the GetAPIGetTasksByGroupIdURL
     *
     * @return String
     */
    public String GetAPIGetTasksByGroupIdURL() {
        return APIGetTasksByGroupIdURL;
    }

    /**
     * getter for the GetAPIGetTasksByUserIdURL
     *
     * @return String
     */
    public String GetAPIGetTasksByUserIdURL() {
        return APIGetTasksByUserIdURL;
    }

    /**
     * getter for the screenName
     *
     * @return String
     */
    public String GetScreenName() {
        return screenName;
    }

    /**
     * setter for the screenName
     *
     * @return String
     */
    public void SetScreenName(String screenName) {
        this.screenName = screenName;
    }
}
