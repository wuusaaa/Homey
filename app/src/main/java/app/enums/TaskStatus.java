package app.enums;

/**
 * Created by barakm on 28/07/2017
 */

public enum TaskStatus {

    DONE("done"),
    UNDONE("undone"),
    IN_PROGRESS("inProgress");

    TaskStatus(String status) {
    }

    private String status;

    public String value() {
        return status;
    }
}
