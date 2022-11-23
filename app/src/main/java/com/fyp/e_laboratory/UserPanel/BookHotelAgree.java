package com.fyp.e_laboratory.UserPanel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.fyp.e_laboratory.R;

public class BookHotelAgree extends AppCompatActivity {

    Intent intent;
    String uid,hurl,billurl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_hotel_agree);


        intent=getIntent();
        uid=intent.getStringExtra("uid");
        hurl=intent.getStringExtra("hurl");
        billurl=intent.getStringExtra("geturl");



    }
}