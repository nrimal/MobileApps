package edu.osu.myapplication;

public class User {
    String userName, email, password;
    int pereferencePk, storePreferencePk;
    
    public User(String userName, String email, String password){
        this.userName = userName;
        this.email = email;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
