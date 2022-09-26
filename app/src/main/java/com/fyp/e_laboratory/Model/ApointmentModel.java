package com.fyp.e_laboratory.Model;

public class ApointmentModel {

    String name,email,phone,address,time,uid,city,cardnumber,url,amount;

    public ApointmentModel(String name, String email, String phone, String address, String time, String uid, String city, String cardnumber, String url, String amount) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.time = time;
        this.uid = uid;
        this.city = city;
        this.cardnumber = cardnumber;
        this.url = url;
        this.amount = amount;
    }

    public ApointmentModel() {
    }

    public String getCardnumber() {
        return cardnumber;
    }

    public void setCardnumber(String cardnumber) {
        this.cardnumber = cardnumber;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
