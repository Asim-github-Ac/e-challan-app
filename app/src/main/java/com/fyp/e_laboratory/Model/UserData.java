package com.fyp.e_laboratory.Model;

public class UserData {
    String name;
    String email;
    String city;
    String image;
    String uid;
    String phone;
    String select_type;


    public UserData(String name, String email, String city, String image, String uid, String phone, String select_type) {
        this.name = name;
        this.email = email;
        this.city = city;
        this.image = image;
        this.uid = uid;
        this.phone = phone;
        this.select_type = select_type;
    }

    public UserData() {
    }

    public String getSelect_type() {
        return select_type;
    }

    public void setSelect_type(String select_type) {
        this.select_type = select_type;
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
