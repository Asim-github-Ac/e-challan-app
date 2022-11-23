package com.fyp.e_laboratory.Fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fyp.e_laboratory.Googlemap.MapConsole;
import com.fyp.e_laboratory.R;
import com.fyp.e_laboratory.UserPanel.ApointMentBook;
import com.fyp.e_laboratory.UserPanel.Instruction;
import com.fyp.e_laboratory.UserPanel.ViewHotels;
import com.fyp.e_laboratory.UserPanel.ViewMedicine;
import com.fyp.e_laboratory.UserPanel.ViewMyReports;


public class HomeFragments extends Fragment {



    CardView paid,aid,ambulance,apoinment,helpinstruction;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_home_fragments, container, false);

        paid=v.findViewById(R.id.paid);
        aid=v.findViewById(R.id.aid);
        ambulance=v.findViewById(R.id.ambulance);
        apoinment=v.findViewById(R.id.apoinment);
        helpinstruction=v.findViewById(R.id.helpinstructions);

        ambulance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ViewHotels.class));
            }
        });
        aid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MapConsole.class));

            }
        });

        apoinment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ViewMyReports.class));
            }
        });
        paid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ViewMedicine.class));
            }
        });
        helpinstruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), Instruction.class));
            }
        });
        return v;
    }
}