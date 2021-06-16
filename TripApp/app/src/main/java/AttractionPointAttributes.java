package com.example.tripapp;

public class AttractionPointAttributes {
    String About;
    String Category;
    String Family_Friendly;
    String Name;
    String Network;
    String Parking;
    String Tuck_Shop;

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    String UID;

    public AttractionPointAttributes() {
        About = "dfdfd";
        Category = " dfdf";
        Family_Friendly = "dfdf ";
        Name = " rebr";
        Network = "vvwv ";
        Parking = "wrv ";
        Tuck_Shop = "wv ";
        UID=" wvw";
    }

    public AttractionPointAttributes(String about, String category, String family_Friendly, String name, String network, String parking, String tuck_Shop,String UID) {
        About = about;
        Category = category;
        Family_Friendly = family_Friendly;
        Name = name;
        Network = network;
        Parking = parking;
        Tuck_Shop = tuck_Shop;
        UID=UID;
    }

    public String getAbout() {
        return About;
    }

    public void setAbout(String about) {
        About = about;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getFamily_Friendly() {
        return Family_Friendly;
    }

    public void setFamily_Friendly(String family_Friendly) {
        Family_Friendly = family_Friendly;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNetwork() {
        return Network;
    }

    public void setNetwork(String network) {
        Network = network;
    }

    public String getParking() {
        return Parking;
    }

    public void setParking(String parking) {
        Parking = parking;
    }

    public String getTuck_Shop() {
        return Tuck_Shop;
    }

    public void setTuck_Shop(String tuck_Shop) {
        Tuck_Shop = tuck_Shop;
    }
}
