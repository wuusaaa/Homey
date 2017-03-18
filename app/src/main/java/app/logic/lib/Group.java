package app.logic.lib;

import java.util.Calendar;

/**
 * Created by Raz on 2/22/2017.
 */

public class Group {

    private int id;
    private String name;
    private long created;
    private byte[] img;

    public Group(String name) {
        this.name = name;
        this.created = Calendar.getInstance().getTime().getTime();
    }

    public int GetId() {
        return id;
    }

    public void SetId(int id) {
        this.id = id;
    }

    public String GetName() {
        return name;
    }

    public void SetName(String name) {
        this.name = name;
    }

    public long GetCreated() {
        return created;
    }

    public void SetCreated(long created) {
        this.created = created;
    }

    public byte[] GetImg() {
        return img;
    }

    public void SetImg(byte[] img) {
        this.img = img;
    }
}
