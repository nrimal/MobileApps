package edu.osu.myapplication.Data;


import java.util.LinkedList;
import java.util.List;

public class User {
    String userName, email, userId;
    String preferencePk;
    List<String> storePreferencePk;
    
    public User(String userName, String email){
        this.userName = userName;
        this.email = email;
    }
    public User(String userName, String email, String userId, String designPreference, String storePreference){
        this.userName = userName;
        this.email = email;
        this.userId = userId;
        this.preferencePk = designPreference;
        this.storePreferencePk = new LinkedList<>();
        this.storePreferencePk.add(storePreference);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {return userId; }
}
