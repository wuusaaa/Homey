package app.enums;

/**
 * Created by barakm on 28/07/2017
 */

public enum TaskStatus {

    COMPLETED("completed"),
    INCOMPLETE("incomplete"),
    IN_PROGRESS("inProgress");

    TaskStatus(String status) {
        this.status = status;
    }

    private String status;

    public String value() {
        return status;
    }
}
