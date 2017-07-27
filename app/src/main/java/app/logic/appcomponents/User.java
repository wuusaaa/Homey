package app.logic.appcomponents;

/**
 * Created by Raz on 12/20/2016.
 *
 * user representation of the db user
 */

public class User {

    private String name;
    private String email;
    private String createdAt;
    private int uid;
    private int score;
    private int level;

    public User(String _name, String _email, String _createdAt, int _uid, int score, int lvl) {
        name = _name;
        email = _email;
        createdAt = _createdAt;
        uid = _uid;
        this.score = score;
        level=lvl;
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

    public int GetUserId() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }


    public int GetScore() {
        return score;
    }

    public void SetScore(int score) {
        this.score = score;
    }

    public int GetLevel() {
        return level;
    }

    public void SetLevel(int level) {
        this.level = level;
    }
}
