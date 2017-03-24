package app.logic.appcomponents;

import java.util.Calendar;

import app.logic.IItem;

/**
 * Created by Raz on 2/22/2017.
 */

public class Group implements IItem {

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

    public String getId() {
        return id;
    }

    public String GetName() {
        return name;
    }

    public long getCreated() {
        return created;
    }

    public byte[] getImg() {
        return img;
    }
}
