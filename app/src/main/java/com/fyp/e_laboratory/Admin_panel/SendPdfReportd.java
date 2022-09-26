package com.fyp.e_laboratory.Admin_panel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.fyp.e_laboratory.Model.PdfModel;
import com.fyp.e_laboratory.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class SendPdfReportd extends AppCompatActivity {

    CardView uploadpdf;
    Uri imageuri = null;
    ProgressDialog dialog;
    String myurl,uid,number,pname,mediaddress;
    Button btnready;
    Intent intent;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_pdf_reportd);

        intent=getIntent();
        uid=intent.getStringExtra("id");
        pname=intent.getStringExtra("pname");
        number=intent.getStringExtra("number");
        mediaddress=intent.getStringExtra("address");

        uploadpdf=findViewById(R.id.uploadpdf);
        btnready=findViewById(R.id.uploadpdfload);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("ELabPdf");

        btnready.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadPdfFiles(uid,myurl);
            }
        });

        uploadpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

                // We will be redirected to choose pdf
                galleryIntent.setType("application/pdf");
                startActivityForResult(galleryIntent, 1);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            // Here we are initialising the progress dialog box
            dialog = new ProgressDialog(this);
            dialog.setMessage("Uploading");

            // this will show message uploading
            // while pdf is uploading
            dialog.show();
            imageuri = data.getData();
            final String timestamp = "" + System.currentTimeMillis();
            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
            final String messagePushID = timestamp;
            Toast.makeText(SendPdfReportd.this, imageuri.toString(), Toast.LENGTH_SHORT).show();

            // Here we are uploading the pdf in firebase storage with the name of current time
            final StorageReference filepath = storageReference.child(messagePushID + "." + "pdf");
            Toast.makeText(SendPdfReportd.this, filepath.getName(), Toast.LENGTH_SHORT).show();
            filepath.putFile(imageuri).continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return filepath.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        // After uploading is done it progress
                        // dialog box will be dismissed
                        dialog.dismiss();
                        Uri uri = task.getResult();

                        myurl = uri.toString();
                        Toast.makeText(SendPdfReportd.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                        btnready.setVisibility(View.VISIBLE);
                    } else {
                        dialog.dismiss();
                        Toast.makeText(SendPdfReportd.this, "UploadedFailed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    public void UploadPdfFiles(String userid,String myurls){
        PdfModel pdfModel=new PdfModel(userid,myurls,number,pname);
        mDatabase.child(userid).setValue(pdfModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(SendPdfReportd.this, "Pdf Uploaded", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getApplicationContext(),medicine_Delivry.class);
                    intent.putExtra("id",userid);
                    intent.putExtra("pname",pname);
                    intent.putExtra("number",number);
                    intent.putExtra("address",mediaddress);
                    startActivity(intent);

                   /// startActivity(getApplication(),medicine_Delivry.class);
                }
            }
        }).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(SendPdfReportd.this, "Something Wrong : "+task.getException(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}