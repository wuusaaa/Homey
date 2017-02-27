package lib;

/**
 * Created by Raz on 12/20/2016.
 *
 * user representation of the db user
 */

public class User {

    private String name;
    private String email;
    private String createdAt;
    private String uid;

    public User(String _name, String _email, String _createdAt, String _uid) {
        name = _name;
        email = _email;
        createdAt = _createdAt;
        uid = _uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
