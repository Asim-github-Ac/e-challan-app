package com.fyp.e_laboratory.AdminAdapters;

import static android.content.Context.DOWNLOAD_SERVICE;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fyp.e_laboratory.Model.ApointmentModel;
import com.fyp.e_laboratory.Model.PdfModel;
import com.fyp.e_laboratory.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ViewPdfAdapter extends RecyclerView.Adapter<ViewPdfAdapter.myHolder> {
    Context context;
    List<ApointmentModel> pdfModelList;
    URL url;
    public ViewPdfAdapter(Context context, List<ApointmentModel> pdfModelList) {
        this.context = context;
        this.pdfModelList = pdfModelList;
    }

    @NonNull
    @Override
    public ViewPdfAdapter.myHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewaccept,parent,false);
        return new ViewPdfAdapter.myHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewPdfAdapter.myHolder holder, int position) {

        ApointmentModel apointmentModel=pdfModelList.get(position);
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
            public void onClick(View v) {
                pdfModelList.remove(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pdfModelList.size();
    }

    public class myHolder extends RecyclerView.ViewHolder {
        TextView tvname,tvphone,tvaddress,tvtime,city,billamount;
        ImageView cardshowsss;
        Button btndelivry;
        public myHolder(@NonNull View itemView) {
            super(itemView);
            tvname=itemView.findViewById(R.id.patients_name1);
            tvaddress=itemView.findViewById(R.id.patiensAddress1);
            tvphone=itemView.findViewById(R.id.patientsphone1);
            tvtime=itemView.findViewById(R.id.patientstime1);
            city=itemView.findViewById(R.id.city1);

            cardshowsss=itemView.findViewById(R.id.cardshowwws1);
            billamount=itemView.findViewById(R.id.billamount1);
            btndelivry=itemView.findViewById(R.id.deleteitem);

        }
    }
    private void downloadPdf(String pdfURL) {

        try {
            url = new URL(pdfURL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            //  requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},READ_STORAGE_PERMISSION_REQUEST_CODE);
        }

        File direct = new File(Environment.getExternalStorageDirectory() + "/Download/AldoFiles");

        if (!direct.exists()) {
            File pdfDirectory = new File("/sdcard/Download/AldoFiles/");
            pdfDirectory.mkdirs();
        }

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url + ""));
        request.setTitle("pdf");
        request.setMimeType("application/pdf");
        request.allowScanningByMediaScanner();
        request.setAllowedOverMetered(true);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"pdf");
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
        downloadManager.enqueue(request);

    }
}
