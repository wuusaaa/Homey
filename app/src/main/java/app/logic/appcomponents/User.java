package app.logic.appcomponents;

import app.activities.interfaces.IHasImage;
import app.activities.interfaces.IHasText;

/**
 * Created by Raz on 12/20/2016.
 * <p>
 * user representation of the db user
 */

public class User implements IHasText, IHasImage {

    private String name;
    private String email;
    private String createdAt;
    private String userId;
    private int score;
    private int level;

    public User(String name, String email, String createdAt, String userId, int score, int level) {
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.userId = userId;
        this.score = score;
        this.level = level;
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

    public String GetUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    @Override
    public String GetName() {
        return name;
    }

    @Override
    public String GetDescription() {
        return name;
    }

    @Override
    public byte[] GetImage() {
        return new byte[0];
    }
}
