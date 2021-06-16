package com.example.tripapp;

public class User {
    String name;
    String phonenum;
    String email;
    String pass;
    String UID;


    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public User(String name, String phonenum, String email, String pass, String UID) {
        this.name = name;
        this.phonenum = phonenum;
        this.email = email;
        this.pass = pass;
        this.UID = UID;
    }

    public User() {
        name = "hello";
        phonenum="123";
        email="m@m.com";
        pass="12345678";
        UID = "uiwdef";
    }

    public String getName() {
        return name;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public String getEmail() {
        return email;
    }

    public String getPass() {
        return pass;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
