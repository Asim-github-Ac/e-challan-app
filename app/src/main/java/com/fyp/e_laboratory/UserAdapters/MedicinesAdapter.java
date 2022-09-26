package com.fyp.e_laboratory.UserAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fyp.e_laboratory.AdminAdapters.ViewPdfAdapter;
import com.fyp.e_laboratory.Model.MedicineDelivry;
import com.fyp.e_laboratory.R;

import org.w3c.dom.Text;

import java.util.List;

public class MedicinesAdapter extends RecyclerView.Adapter<MedicinesAdapter.myHolder> {
    Context context;
    List<MedicineDelivry> medicineDelivryList;

    public MedicinesAdapter(Context context, List<MedicineDelivry> medicineDelivryList) {
        this.context = context;
        this.medicineDelivryList = medicineDelivryList;
    }

    @NonNull
    @Override
    public MedicinesAdapter.myHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.medicines,parent,false);
        return new MedicinesAdapter.myHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicinesAdapter.myHolder holder, int position) {

        MedicineDelivry medicineDelivry=medicineDelivryList.get(position);

        holder.tvname.setText(medicineDelivry.getName());
        holder.tvmedi.setText(medicineDelivry.getMedicine());
        holder.tvaddress.setText(medicineDelivry.getAddress());
        holder.tvphone.setText(medicineDelivry.getPhone());

    }

    @Override
    public int getItemCount() {
        return medicineDelivryList.size();
    }

    public class myHolder extends RecyclerView.ViewHolder {
        TextView tvname,tvmedi,tvphone,tvaddress;
        public myHolder(@NonNull View itemView) {
            super(itemView);
            tvname=itemView.findViewById(R.id.pitemname);
            tvmedi=itemView.findViewById(R.id.pitemmediname);
            tvphone=itemView.findViewById(R.id.pitemphone);
            tvaddress=itemView.findViewById(R.id.pitemaddres);
        }
    }
}
