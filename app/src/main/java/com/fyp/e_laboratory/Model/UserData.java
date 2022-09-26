package com.fyp.e_laboratory.Model;

public class UserData {
    String name;
    String email;
    String city;
    String image;
    String uid;
    String phone;


    public UserData(String name, String email, String city, String image, String uid, String phone) {
        this.name = name;
        this.email = email;
        this.city = city;
        this.image = image;
        this.uid = uid;
        this.phone = phone;
    }

    public UserData() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUid() {
        return uid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
