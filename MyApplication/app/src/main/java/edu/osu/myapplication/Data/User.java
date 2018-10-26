package edu.osu.myapplication.Data;


public class User {
    String userName, email, userId;
    int pereferencePk, storePreferencePk;
    
    public User(String userName, String email){
        this.userName = userName;
        this.email = email;
    }
    public User(String userName, String email, String userId){
        this.userName = userName;
        this.email = email;
        this.userId = userId;
    }
    public User(){}

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
