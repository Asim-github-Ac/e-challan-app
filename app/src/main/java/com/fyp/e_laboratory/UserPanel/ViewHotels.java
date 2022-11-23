package com.fyp.e_laboratory.UserPanel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.fyp.e_laboratory.AdminAdapters.ViewHotelAdapter;
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

public class ViewHotels extends AppCompatActivity {
    List<ApointmentModel> medicineDelivryList=new ArrayList<>();
    PrefManager prefManager;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_hotels);
        recyclerView=findViewById(R.id.viewhotels);
        prefManager=new PrefManager(getApplicationContext());
        String uid=prefManager.getUserID();
        getData(uid);
    }
    public void getData(String uid){
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("HotelPost");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    ApointmentModel pdfModel=snapshot1.getValue(ApointmentModel.class);
//                    if (pdfModel.getUid().equals(uid)) {
                        medicineDelivryList.add(pdfModel);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        recyclerView.setAdapter(new ViewHotelAdapter(getApplicationContext(), medicineDelivryList));
               //     }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(ViewHotels.this, "error "+ error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}