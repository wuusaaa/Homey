package app.logic.lib;

import java.util.Date;

/**
 * Created by Raz on 2/22/2017.
 */

public class Group {

    private int id;
    private String name;
    private Date created;
    private byte[] img;

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

    public Date GetCreated() {
        return created;
    }

    public void SetCreated(Date created) {
        this.created = created;
    }

    public byte[] GetImg() {
        return img;
    }

    public void SetImg(byte[] img) {
        this.img = img;
    }
}
