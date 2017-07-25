package app.logic.managers;

import java.lang.reflect.Constructor;
import java.util.HashMap;

/**
 * Created by Raz on 12/20/2016
 */

public class Services {
    private Services() {
    }

    private static final HashMap<Class, ManagerBase> services = new HashMap<>();

    public static void AddService(ManagerBase manager) {
        services.put(manager.getClass(), manager);
    }

    public static ManagerBase GetService(Class type) {
        //checks if the type is a subclass of ManagerBase and if the map doesnt contain the key.
        if ((ManagerBase.class.isAssignableFrom(type)) && !services.containsKey(type)) {
            Constructor ctor = type.getDeclaredConstructors()[0];
            try {
                ctor.newInstance();
            } catch (Exception e) { //TODO: Handle the exception?
                e.printStackTrace();
            }
        }

        return services.get(type);
    }
}
