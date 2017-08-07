package app.logic.appcomponents;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

import app.activities.interfaces.IHasText;
import app.enums.TaskStatus;

/**
 * Created by Raz on 12/20/2016
 */

public class Task implements IHasText, Parcelable {
    private String taskId;
    private String name;
    private int groupId;
    private String description;
    private String status;
    private String location;
    private int creatorId;
    private Date startTime;
    private Date endTime;
    private int score = 0;
    private byte[] img;

    public Task(String taskId, String name, int groupId, String description, String status, String location, int creatorId, Date startTime, Date endTime, int score, byte[] img) {
        this.taskId = taskId;
        this.name = name;
        this.groupId = groupId;
        this.description = description;
        this.status = status;
        this.location = location;
        this.creatorId = creatorId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.score = score;
        this.img = img;
    }

    protected Task(Parcel in) {
        taskId = in.readString();
        name = in.readString();
        groupId = in.readInt();
        description = in.readString();
        status = in.readString();
        location = in.readString();
        creatorId = in.readInt();
        startTime = new Date(in.readLong());
        endTime = new Date(in.readLong());
        score = in.readInt();
        img = in.createByteArray();
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    public String getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status.value();
    }

    public String GetName() {
        return name;
    }

    public String GetTaskId() {
        return taskId;
    }

    public int GetGroupId() {
        return groupId;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(taskId);
        dest.writeString(name);
        dest.writeInt(groupId);
        dest.writeString(description);
        dest.writeString(status);
        dest.writeString(location);
        dest.writeInt(creatorId);
        dest.writeLong(startTime.getTime());
        dest.writeLong(endTime.getTime());
        dest.writeInt(score);
        dest.writeByteArray(img);
    }

    public byte[] GetImg() {
        return img;
    }

    public void SetImg(byte[] img) {
        this.img = img;
    }
}
