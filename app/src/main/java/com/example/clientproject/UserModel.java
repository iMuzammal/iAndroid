package com.example.clientproject;

import com.google.firebase.database.Exclude;

public class UserModel {


    String userName;
    String phone;
    String userLastname;
    String colorCode;

    public UserModel() {
    }

    public UserModel(String userName, String phone, String userLastname, String colorCode) {
        this.userName = userName;
        this.phone = phone;
        this.userLastname = userLastname;
        this.colorCode = colorCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserLastname() {
        return userLastname;
    }

    public void setUserLastname(String userLastname) {
        this.userLastname = userLastname;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }
}