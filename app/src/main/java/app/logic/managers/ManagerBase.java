package app.logic.managers;

/**
 * Created by barakm on 12/03/2017
 */

abstract class ManagerBase {

    ManagerBase() {
        registerService();
    }

    private void registerService() {
        Services.AddService(this);
    }
}
