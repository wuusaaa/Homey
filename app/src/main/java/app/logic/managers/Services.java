package app.logic.managers;

import db.DBManager;

/**
 * Created by Raz on 12/20/2016.
 */

public class Services {
    public static DBManager GetService() {
        return new DBManager();
    }
}
