package com.fyp.e_laboratory.UserPanel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.fyp.e_laboratory.AdminAdapters.ManageHotelAdpt;
import com.fyp.e_laboratory.Model.ApointmentModel;
import com.fyp.e_laboratory.R;
import com.fyp.e_laboratory.SharedPrefrence.PrefManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewBookingHistory extends AppCompatActivity {
    RecyclerView recyclerView;
    private DatabaseReference mDatabase;
    List<ApointmentModel> pdfModelList=new ArrayList<>();
    PrefManager prefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_reports);
        recyclerView=findViewById(R.id.myreports);
        prefManager=new PrefManager(getApplicationContext());
        String uid=prefManager.getUserID();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("AcceptChallan");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    ApointmentModel pdfModel=snapshot1.getValue(ApointmentModel.class);
                    if (pdfModel.getUid().equals(uid)) {
                        pdfModelList.add(pdfModel);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        recyclerView.setAdapter(new ManageHotelAdpt(getApplicationContext(), pdfModelList));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(ViewBookingHistory.this, "error "+ error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
