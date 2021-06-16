package com.example.tripapp;

public class HotelAttributes {
    String Standard;
    String Petrol_Pump;
    String Laundry;
    String Name;
    String Parking;
    String Restaurant;
    String Contact_No;
    String Heater;
    String Transportation;
    String UID;


    public HotelAttributes() {
        Standard = "";
        Petrol_Pump = "";
        Laundry ="";
        Name = "";
        Parking = "";
        Restaurant ="";
        Contact_No = "";
        Heater = "";
        Transportation = "";
        UID = "";

    }

    public HotelAttributes(String standard, String petrol_pump, String laundry, String name, String parking, String restaurant, String contact_No, String heater, String transportation, String uid) {
        Standard = standard;
        Petrol_Pump = petrol_pump;
        Laundry = laundry;
        Name = name;
        Parking = parking;
        Restaurant = restaurant;
        Contact_No = contact_No;
        Heater = heater;
        Transportation = transportation;
        UID = uid;

    }

    public String getStandard() {
        return Standard;
    }

    public void setStandard(String standard) {
        Standard = standard;
    }

    public String getPetrol_pump() {
        return Petrol_Pump;
    }

    public void setPetrol_pump(String petrol_pump) {
        Petrol_Pump = petrol_pump;
    }

    public String getLaundry() {
        return Laundry;
    }

    public void setLaundry(String laundry) {
        Laundry = laundry;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getParking() {
        return Parking;
    }

    public void setParking(String parking) {
        Parking = parking;
    }

    public String getRestaurant() {
        return Restaurant;
    }

    public void setRestaurant(String restaurant) {
        Restaurant = restaurant;
    }

    public String getContact_No() {
        return Contact_No;
    }

    public void setContact_No(String contact_No) {
        Contact_No = contact_No;
    }

    public String getHeater() {
        return Heater;
    }

    public void setHeater(String heater) {
        Heater = heater;
    }

    public String getTransportation() {
        return Transportation;
    }

    public void setTransportation(String transportation) {
        Transportation = transportation;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }


}
