package app.logic.appcomponents;

import java.util.Date;

import app.logic.IItem;

/**
 * Created by Raz on 12/20/2016.
 */

public class Task implements IItem {

    private String name;
    private String description;
    private String status;
    private String location;
    private int creatorId;
    private Date startTime;
    private Date endTime;

    public Task(String name, String description, String status, String location, int creatorId, Date startTime, Date endTime) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.location = location;
        this.creatorId = creatorId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String GetName() {
        return name;
    }

    public void SetName(String name) {
        this.name = name;
    }

    public String GetDescription() {
        return description;
    }

    public void SetDescription(String description) {
        this.description = description;
    }

    public String GetStatus() {
        return status;
    }

    public void SetStatus(String status) {
        this.status = status;
    }

    public String GetLocation() {
        return location;
    }

    public void SetLocation(String location) {
        this.location = location;
    }

    public int GetCreatorId() {
        return creatorId;
    }

    public void SetCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public Date GetStartTime() {
        return startTime;
    }

    public void SetStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date GetEndTime() {
        return endTime;
    }

    public void SetEndTime(Date endTime) {
        this.endTime = endTime;
    }


//    private TaskDate m_TaskDate;
//    private String m_TaskName;
//    private Boolean m_IsDone;
//    private String m_Asignee;
//
//    public Task(TaskDate i_TaskDate, String i_TaskName, String i_Asignee) {
//        m_TaskDate = i_TaskDate;
//        m_TaskName = i_TaskName;
//        m_IsDone = false;
//        m_Asignee = i_Asignee;
//    }
//
//    public void setTaskAsComplete() {
//        m_IsDone = true;
//    }
//
//    public void setTaskAsInComplete() {
//        m_IsDone = false;
//    }
//
//    public Boolean isComplete() {
//        return m_IsDone;
//    }


}
