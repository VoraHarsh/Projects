package com.harshvora.bookkarbooks.Modal;

/**
 * Created by Harsh on 1/30/2018.
 */

public class User {
    private String emailId;
    private String Password;
    private String MobileNo;
    private String Username;



    public User(){

    }

    public User(String emailId, String password, String mobileNo) {
        this.emailId = emailId;
        Password = password;
        MobileNo = mobileNo;
       // Username = username;
    }

    public User(String emailId, String password, String mobileNo, String username) {
        this.emailId = emailId;
        Password = password;
        MobileNo = mobileNo;
        Username = username;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
