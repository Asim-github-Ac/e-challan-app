package com.fyp.e_laboratory.AdminAdapters;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fyp.e_laboratory.Admin_panel.SendPdfReportd;
import com.fyp.e_laboratory.Model.ApointmentModel;
import com.fyp.e_laboratory.R;
import com.fyp.e_laboratory.UserPanel.ApointMentBook;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ViewApointAdapters extends RecyclerView.Adapter<ViewApointAdapters.myHoder> {
    Context context;
    List<ApointmentModel> apointmentModelList;

    public ViewApointAdapters(Context context, List<ApointmentModel> apointmentModelList) {
        this.context = context;
        this.apointmentModelList = apointmentModelList;
    }

    @NonNull
    @Override
    public ViewApointAdapters.myHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.apoint_items,parent,false);
        return new myHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewApointAdapters.myHoder holder, int position) {
        ApointmentModel apointmentModel=apointmentModelList.get(position);
        holder.tvname.setText(apointmentModel.getName());
        holder.tvaddress.setText(apointmentModel.getAddress());
        holder.tvtime.setText(apointmentModel.getPhone());
        holder.tvphone.setText(apointmentModel.getTime());
        holder.city.setText(apointmentModel.getCity());
        holder.billamount.setText(apointmentModel.getAmount());
        System.out.println("url is_____________________"+apointmentModel.getUrl());
        Picasso.get().load(apointmentModel.getUrl()).into(holder.cardshowsss);
        holder.btndelivry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AddApointment(apointmentModel.getName(),apointmentModel.getEmail(),apointmentModel.getPhone(),apointmentModel.getAddress(),apointmentModel.getTime(),apointmentModel.getUid(),apointmentModel.getCity(),apointmentModel.getCardnumber(),apointmentModel.getUrl(),apointmentModel.getAmount());
//                Intent myactivity = new Intent(context.getApplicationContext(), SendPdfReportd.class);
//                myactivity.putExtra("id",apointmentModel.getUid());
//                myactivity.putExtra("pname",apointmentModel.getName());
//                myactivity.putExtra("number",apointmentModel.getPhone());
//                myactivity.putExtra("address",apointmentModel.getAddress());
//                myactivity.putExtra("url",apointmentModel.getUrl());
//                myactivity.putExtra("amount",apointmentModel.getAmount());
//                myactivity.addFlags(FLAG_ACTIVITY_NEW_TASK);
//                context.getApplicationContext().startActivity(myactivity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return apointmentModelList.size();
    }

    public class myHoder extends RecyclerView.ViewHolder {
        TextView tvname,tvphone,tvaddress,tvtime,city,billamount;
        ImageView cardshowsss;
        Button btndelivry;

        public myHoder(@NonNull View itemView) {
            super(itemView);
            tvname=itemView.findViewById(R.id.patients_name);
            tvaddress=itemView.findViewById(R.id.patiensAddress);
            tvphone=itemView.findViewById(R.id.patientsphone);
            tvtime=itemView.findViewById(R.id.patientstime);
            city=itemView.findViewById(R.id.city);

            cardshowsss=itemView.findViewById(R.id.cardshowwws);
            billamount=itemView.findViewById(R.id.billamount);
            btndelivry=itemView.findViewById(R.id.medi_delivry_items);


        }
    }
    public void AddApointment(String name, String email, String phone, String addres, String tim, String uid, String city,String bilnumber,String urls,String amount) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("AcceptChallan");
        ApointmentModel apointmentModel = new ApointmentModel(name, email, phone, addres, tim, uid, city,bilnumber,urls,amount);

        databaseReference.child(uid).setValue(apointmentModel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(context, "Added", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
