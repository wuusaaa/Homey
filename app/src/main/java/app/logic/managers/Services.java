package app.logic.managers;

import java.util.HashMap;

/**
 * Created by Raz on 12/20/2016.
 */

public class Services {
    private Services() {
    }

    private final HashMap<Class, ManagerBase> services = new HashMap<>();

    private static class ServiceHolder {
        public static final Services INSTANCE = new Services();

        private ServiceHolder() {
        }
    }

    public static Services GetInstance() {
        return ServiceHolder.INSTANCE;
    }

    public void AddService(Class type, ManagerBase manager) {
        services.put(type, manager);
    }

    public ManagerBase GetService(Class type) {
        return services.get(type);
    }
}
