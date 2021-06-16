package com.example.tripapp;

public class LikedAreasAttributes {

    String Name;
    String ImgId;
    String UID;


    public LikedAreasAttributes() {
        Name = "";
        ImgId="";
        UID="";
    }

    public LikedAreasAttributes(String name, String imgId, String uid)
    {
        Name = name;
        ImgId = imgId;
        UID=uid;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
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
