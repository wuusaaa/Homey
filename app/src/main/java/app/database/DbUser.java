package app.database;

/**
 * Created by Raz on 2/22/2017.
 */

public class DbUser {

    private int id;
    private byte[] guid;
    private String name;

    public DbUser(int id, byte[] guid, String name) {

        this.id = id;
        this.guid = guid;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public byte[] getGuid() {
        return guid;
    }

    public String getName() {
        return name;
    }
}
