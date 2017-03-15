package app.logic.managers;

/**
 * Created by barakm on 12/03/2017.
 */

public abstract class ManagerBase {
    public ManagerBase() {
        registerService();
    }

    protected void registerService() {
        Services.GetInstance().AddService(this.getClass(), this);
    }

    public static Class GetClass() {
        return ManagerBase.class;
    }
}
