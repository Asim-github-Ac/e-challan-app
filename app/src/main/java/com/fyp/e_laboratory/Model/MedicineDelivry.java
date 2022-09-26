package com.fyp.e_laboratory.Model;

public class MedicineDelivry {
    String userid,name,phone,address,medicine;

    public MedicineDelivry(String userid, String name, String phone, String address, String medicine) {
        this.userid = userid;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.medicine = medicine;
    }

    public MedicineDelivry() {
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }
}
