package app.logic.managers;

/**
 * Created by Raz on 2/22/2017.
 * A singleton to set all the environment variables
 */

public class EnvironmentManager {

    private EnvironmentManager() {}

    public String GetAPIPassResetURL() {
        return APIPassResetURL;
    }

    public String GetAPIAddTaskURL() {
        return APIAddTaskURL;
    }

    public String GetAPIAddGroupURL() {
        return APIAddGroupURL;
    }

    // Instance holder
    private static class EnvironmentManagerHolder {
        public static final EnvironmentManager INSTANCE = new EnvironmentManager();
        private EnvironmentManagerHolder() {}
    }

    /**
     * provide the instance of this class
     * @return EnvironmentManager
     */
    public static EnvironmentManager GetInstance() {
        return EnvironmentManagerHolder.INSTANCE;
    }

    // Server user login url
    private static final String LoginURL = "https://www.crossword-clues.com/homey/login.php";
    // Server user register url
    private static final String registrationURL = "https://www.crossword-clues.com/homey/register.php";

    private final String APIServerURL = "https://www.crossword-clues.com/homey/sql.php";

    private final String APIPassResetURL = "https://www.crossword-clues.com/homey/forgot.php";

    private final String APIAddTaskURL = "https://www.crossword-clues.com/homey/add-app.task.php";

    private final String APIAddGroupURL = "https://www.crossword-clues.com/homey/add-group.php";

    private final String serverSecurityToken = "&t5h5th%TH&&gr4gjkbddr%THsdfd21";


    /**
     * getter for the LoginURL
     * @return String
     */
    public String GetLoginURL(){
        return LoginURL;
    }

    /**
     * getter for the registrationURL
     * @return String
     */
    public String GetRegistrationURL(){
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
}
