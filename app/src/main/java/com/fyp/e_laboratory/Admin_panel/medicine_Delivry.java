package com.fyp.e_laboratory.Admin_panel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fyp.e_laboratory.Model.MedicineDelivry;
import com.fyp.e_laboratory.R;
import com.fyp.e_laboratory.SharedPrefrence.PrefManager;
import com.fyp.e_laboratory.UserPanel.ApointMentBook;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class medicine_Delivry extends AppCompatActivity {

    Intent intent;
    String pname,address,uid,number;
    Spinner spinner;
    String meditype;
    Button btn_submit;
    ProgressBar progressBar;
    TextView tvname,tvphone,tvadress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_delivry);
        intent=getIntent();
        pname=intent.getStringExtra("pname");
        uid=intent.getStringExtra("id");
        number=intent.getStringExtra("number");
        address=intent.getStringExtra("address");

        spinner = findViewById(R.id.pspinner);
        btn_submit=findViewById(R.id.ap_submitmedi);
        tvname=findViewById(R.id.panemss);
        tvphone=findViewById(R.id.pphoness);
        tvadress=findViewById(R.id.paddresss);
        progressBar=findViewById(R.id.ap_bbar);
        tvname.setText(pname);
        tvphone.setText(number);
        tvadress.setText(address);

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Panadol");
        arrayList.add("Evion");
        arrayList.add("Agnar");
        arrayList.add("Wintogeno (Balm)");
        arrayList.add("Coldrex");
        arrayList.add("Mexaderm");
        arrayList.add("Lophos");
        arrayList.add("Ponstan");
        arrayList.add("Flagyl");
        arrayList.add("Synthroid");
        arrayList.add("Nexium");
        arrayList.add("Incid-L");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,                         android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 meditype = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "Selected: " + meditype,          Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                UploadData(uid,meditype,pname,number,address);
            }
        });
    }
    public void UploadData(String userid,String meditypes,String name,String phone,String add){
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("ELab_Medi");
        MedicineDelivry medicineDelivry=new MedicineDelivry(userid,name,phone,add,meditypes);
        databaseReference.child(userid).setValue(medicineDelivry).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(medicine_Delivry.this, "Medicine Added", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(medicine_Delivry.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}