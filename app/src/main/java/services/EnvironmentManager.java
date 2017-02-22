package services;

/**
 * Created by Raz on 2/22/2017.
 * A singelton to set all the environment variables
 */

public class EnvironmentManager {

    private EnvironmentManager() {}

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

    /**
     * getter for the LoginURL
     * @return String
     */
    public String GetLoginURL(){
        return LoginURL;
    }

    /**
     * getter for the registrationURL
     * @returnString
     */
    public String GetRegistrationURL(){
        return registrationURL;
    }
}
