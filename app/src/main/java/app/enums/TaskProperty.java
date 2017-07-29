package app.enums;

/**
 * Created by barakm on 29/07/2017
 */

public enum TaskProperty {
    TASK_ID("taskId"),

    NAME("name"),

    GROUP_ID("groupId"),

    DESCRIPTION("description"),

    STATUS("status"),

    LOCATION("location"),

    CREATOR_ID("creatorId"),

    START_TIME("startTime"),

    END_TIME("endTime");

    TaskProperty(String property) {
        this.property = property;
    }

    private String property;

    public String value() {
        return property;
    }
}
