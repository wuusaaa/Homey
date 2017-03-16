package app.logic.managers;

import java.util.HashMap;

/**
 * Created by Raz on 12/20/2016.
 */

public class Services {
    private Services() {
    }

    private static boolean initiated = false;

    private static final HashMap<Class, ManagerBase> services = new HashMap<>();

    public static void AddService(ManagerBase manager) {
        services.put(manager.getClass(), manager);
    }

    public static ManagerBase GetService(Class type) {
        if (!initiated) {
            initialManagers();
            initiated = true;
        }

        return services.get(type);
    }

    private static void initialManagers() {
        new AppManager();
        new SessionManager();
        new GroupManager();
        new EnvironmentManager();
        new TaskManager();
        new DBManager();
    }

}
