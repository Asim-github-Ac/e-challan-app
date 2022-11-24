package com.fyp.e_laboratory.UserPanel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.fyp.e_laboratory.R;
import com.squareup.picasso.Picasso;

public class BookHotelAgree extends AppCompatActivity implements View.OnClickListener {

    Intent intent;
    String uid,hurl,billurl;
    ImageView imageView;
    EditText edname,edadd,eddes,edphone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_hotel_agree);
        imageView=findViewById(R.id.urlagree);
        edname=findViewById(R.id.weddbook_name);
        edadd=findViewById(R.id.weddbook_address);
        eddes=findViewById(R.id.weddingbook_description);
        edphone=findViewById(R.id.weddingbook_phone);

        intent=getIntent();
        uid=intent.getStringExtra("uid");
        hurl=intent.getStringExtra("hurl");
        billurl=intent.getStringExtra("geturl");
        Picasso.get().load(hurl).into(imageView);






    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
                    }
    }
}