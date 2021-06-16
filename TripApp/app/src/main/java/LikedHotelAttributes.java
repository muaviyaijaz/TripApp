package com.example.tripapp;

public class LikedHotelAttributes {
    String Name;
    String ImgId;
    String UID;


    public  LikedHotelAttributes() {
        Name = "";
        ImgId="";
        UID="";
    }

    public LikedHotelAttributes(String name, String imgId, String uid)
    {
        Name = name;
        ImgId = imgId;
        UID=uid;
    }

    public String getHotelName() {
        return Name;
    }

    public void setHotelName(String name) {
        Name = name;
    }

    public String getImgId() {
        return ImgId;
    }

    public void setImgId(String imgId) {
        ImgId = imgId;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }
}
