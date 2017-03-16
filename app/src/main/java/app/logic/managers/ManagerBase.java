package app.logic.managers;

/**
 * Created by barakm on 12/03/2017.
 */

public abstract class ManagerBase {

    protected ManagerBase() {
        registerService();
    }

    protected void registerService() {
        Services.AddService(this);
    }
}
