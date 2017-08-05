package app.logic.appcomponents;

import android.os.Parcel;
import android.os.Parcelable;

import app.activities.interfaces.IHasImage;
import app.activities.interfaces.IHasText;

/**
 * Created by Raz on 12/20/2016.
 * <p>
 * user representation of the db user
 */

public class User implements IHasText, IHasImage, Parcelable {

    private String name;
    private String email;
    private String createdAt;
    private String userId;
    private int score;
    private int level;
    private byte[] img;

    public User(String name, String email, String createdAt, String userId, int score, int level, byte[] img)
    {
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.userId = userId;
        this.score = score;
        this.level = level;
        this.img=img;
    }

    protected User(Parcel in) {
        name = in.readString();
        email = in.readString();
        createdAt = in.readString();
        userId = in.readString();
        score = in.readInt();
        level = in.readInt();
        img = in.createByteArray();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i)
    {
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(createdAt);
        dest.writeString(userId);
        dest.writeInt(score);
        dest.writeInt(level);
        dest.writeByteArray(img);
    }
}
