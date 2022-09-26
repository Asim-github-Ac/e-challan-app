package com.fyp.e_laboratory.Admin_panel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.fyp.e_laboratory.Authentication.LoginPage;
import com.fyp.e_laboratory.R;
import com.fyp.e_laboratory.SharedPrefrence.PrefManager;

public class Admin_Home_Page extends AppCompatActivity {

    CardView viewreports,viewapointments,viewmedicines,logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_page);
        viewapointments=findViewById(R.id.view_apointments);
        viewreports=findViewById(R.id.view_reports);
        viewmedicines=findViewById(R.id.medicine_deliviry_Admin);
        logout=findViewById(R.id.logouts_admin);

        viewreports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),ViewAllReports.class);
                intent.putExtra("v","AcceptChallan");
                startActivity(intent);
            }
        });
        viewapointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent=new Intent(getApplicationContext(),ViewApointMents.class);
               intent.putExtra("v","EChalllan");
               startActivity(intent);
            }
        });
        viewmedicines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ViewMedinesAdmin.class));
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrefManager prefManager=new PrefManager(getApplicationContext());
                prefManager.setToken_Email("");
                startActivity(new Intent(getApplicationContext(), LoginPage.class));
                finish();
            }
        });

    }
}