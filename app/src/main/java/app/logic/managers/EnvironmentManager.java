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

    private final String APIAddUserToTask = "https://www.crossword-clues.com/homey/add-user-to-task.php";

    private final String APIAddUserToGroup = "https://www.crossword-clues.com/homey/add-user-to-group.php";

    private final String APIGetTaskUsersURL = "https://www.crossword-clues.com/homey/get-task-users.php";

    private final String APIGetGroupUsersURL = "https://www.crossword-clues.com/homey/get-group-users-by-group-id.php";

    private final String APIGetUserURL = "https://www.crossword-clues.com/homey/get-user.php";

    private final String APIGetCreatorFromTaskIdURL = "https://www.crossword-clues.com/homey/get-creator-from-task-id.php";

    private final String APIGetGroupsThatUserIsAdminURL = "https://www.crossword-clues.com/homey/get-groups-that-user-is-admin.php";

    private final String APIGetGroupURL = "https://www.crossword-clues.com/homey/get-group.php";

    private final String APIGetGroupAdminsURL = "https://www.crossword-clues.com/homey/get-group-admins.php";

    private final String APIGetGroupByTaskIdURL = "https://www.crossword-clues.com/homey/get-group-from-task-id.php";

    private final String APIGetTasksByGroupIdURL = "https://www.crossword-clues.com/homey/get-tasks-by-group-id.php";

    private final String APIGetTasksByUserIdURL = "https://www.crossword-clues.com/homey/get-tasks-by-user-id.php";

    private final String APIUpdateTableValueURL = "https://www.crossword-clues.com/homey/update-table-value.php";

    private final String APIMakeUserAdminURL = "https://www.crossword-clues.com/homey/make-user-admin.php";

    private final String APILeaveTaskURL = "https://www.crossword-clues.com/homey/leave-task.php";

    private final String APIRemoveTaskURL = "https://www.crossword-clues.com/homey/remove-task.php";

    private final String APILeaveGroupURL = "https://www.crossword-clues.com/homey/leave-group.php";

    private final String APIRemoveFromTableURL = "https://www.crossword-clues.com/homey/remove-from-table.php";

    private final String serverSecurityToken = "&t5h5th%TH&&gr4gjkbddr%THsdfd21";

    //default is Home Page - first screen
    private String screenName = "Home Page";


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
     * getter for the APIPassResetURL
     *
     * @return String
     */
    public String GetAPIPassResetURL() {
        return APIPassResetURL;
    }

    /**
     * getter for the APIAddTaskURL
     *
     * @return String
     */
    public String GetAPIAddTaskURL() {
        return APIAddTaskURL;
    }

    /**
     * getter for the APIAddGroupURL
     *
     * @return String
     */
    public String GetAPIAddGroupURL() {
        return APIAddGroupURL;
    }

    /**
     * getter for the APIGetUserURL
     *
     * @return String
     */
    public String GetAPIGetUserURL() {
        return APIGetUserURL;
    }

    /**
     * getter for the APIGetGroupURL
     *
     * @return String
     */
    public String GetAPIGetGroupURL() {
        return APIGetGroupURL;
    }

    /**
     * getter for the APIGetUserGroupsURL
     *
     * @return String
     */
    public String GetAPIGetUserGroupsURL() {
        return APIGetUserGroupsURL;
    }

    /**
     * getter for the APIGetTasksByGroupIdURL
     *
     * @return String
     */
    public String GetAPIGetTasksByGroupIdURL() {
        return APIGetTasksByGroupIdURL;
    }

    /**
     * getter for the APIGetTasksByUserIdURL
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

    /**
     * getter for the APIUpdateTableValueURL
     *
     * @return String
     */
    public String GetAPIUpdateTableValueURL() {
        return APIUpdateTableValueURL;
    }

    /**
     * getter for the APIRemoveFromTableURL
     *
     * @return String
     */
    public String GetAPIRemoveFromTableURL() {
        return APIRemoveFromTableURL;
    }

    /**
     * getter for the APIGetGroupByTaskIdURL
     *
     * @return String
     */
    public String GetAPIGetGroupByTaskIdURL() {
        return APIGetGroupByTaskIdURL;
    }

    /**
     * getter for the APIGetCreatorFromTaskIdURL
     *
     * @return String
     */
    public String GetAPIGetCreatorFromTaskIdURL() {
        return APIGetCreatorFromTaskIdURL;
    }

    /**
     * getter for the APIGetGroupAdminsURL
     *
     * @return String
     */
    public String GetAPIGetGroupAdminsURL() {
        return APIGetGroupAdminsURL;
    }

    /**
     * getter for the APIGetTaskUsersURL
     *
     * @return String
     */
    public String GetAPIGetTaskUsersURL() {
        return APIGetTaskUsersURL;
    }

    /**
     * getter for the APILeaveTaskURL
     *
     * @return String
     */
    public String GetAPILeaveTaskURL() {
        return APILeaveTaskURL;
    }

    /**
     * getter for the APILeaveGroupURL
     *
     * @return String
     */
    public String GetAPILeaveGroupURL() {
        return APILeaveGroupURL;
    }

    /**
     * getter for the APIGetGroupUsersURL
     *
     * @return String
     */
    public String GetAPIGetGroupUsersURL() {
        return APIGetGroupUsersURL;
    }

    /**
     * getter for the APIGetGroupsThatUserIsAdminURL
     *
     * @return String
     */
    public String GetAPIGetGroupsThatUserIsAdminURL() {
        return APIGetGroupsThatUserIsAdminURL;
    }

    /**
     * getter for the APIAddUserToTask
     *
     * @return
     */
    public String GetAPIAddUserToTask() {
        return APIAddUserToTask;
    }

    /**
     * getter for the APIAddUserToGroup
     *
     * @return
     */
    public String GetAPIAddUserToGroup() {
        return APIAddUserToGroup;
    }


    public String GetAPIMakeUserAdminURL() {
        return APIMakeUserAdminURL;
    }

    public String GetAPIRemoveTaskURL() {
        return APIRemoveTaskURL;
    }
}
