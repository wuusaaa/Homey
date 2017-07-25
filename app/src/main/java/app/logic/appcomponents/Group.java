package app.logic.appcomponents;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

import app.activities.interfaces.IHasImage;
import app.activities.interfaces.IHasText;

/**
 * Created by Raz on 2/22/2017
 */

public class Group implements IHasText, IHasImage,Parcelable {

    private final String id;
    private final String name;
    private long created;
    private byte[] img;

    public Group(String id, String name, byte[] img) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.created = Calendar.getInstance().getTime().getTime();
    }

    protected Group(Parcel in) {
        name = in.readString();
        id = in.readString();
        img = in.createByteArray();
        created = in.readLong();
    }

    public static final Creator<Group> CREATOR = new Creator<Group>() {
        @Override
        public Group createFromParcel(Parcel in) {
            return new Group(in);
        }

        @Override
        public Group[] newArray(int size) {
            return new Group[size];
        }
    };

    public String GetId() {
        return id;
    }

    public String GetName() {
        return name;
    }

    public long GetCreated() {
        return created;
    }

    public String GetDescription(){ return "aaa"; }

    public byte[] GetImage() {
        return img;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(id);
        dest.writeByteArray(img);
        dest.writeLong(created);
    }
}
