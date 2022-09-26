package com.fyp.e_laboratory.Model;

public class PdfModel {
    String uid,pdfurl,number,pname;

    public PdfModel(String uid, String pdfurl, String number, String pname) {
        this.uid = uid;
        this.pdfurl = pdfurl;
        this.number = number;
        this.pname = pname;
    }

    public PdfModel() {
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPdfurl() {
        return pdfurl;
    }

    public void setPdfurl(String pdfurl) {
        this.pdfurl = pdfurl;
    }
}
