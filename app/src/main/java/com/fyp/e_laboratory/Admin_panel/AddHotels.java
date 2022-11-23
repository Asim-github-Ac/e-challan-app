package com.fyp.e_laboratory.Admin_panel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.fyp.e_laboratory.Model.ApointmentModel;
import com.fyp.e_laboratory.R;
import com.fyp.e_laboratory.SharedPrefrence.PrefManager;
import com.fyp.e_laboratory.UserPanel.ApointMentBook;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AddHotels extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {
    TimePicker timepicker;
    Button changetime, submit;
    String uid;
    String time;
    Button btngenrate;
    ImageView imggenrate;
    String url,hotelurl;
    FirebaseStorage storage;
    StorageReference storageReference;
    Uri filepath;
    private Uri filePath;
    String city;

    private final int PICK_IMAGE_REQUEST = 22;
    ImageView selectimg;
    EditText emails, names, phones, addresss, billamount, cardnumber;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hotels);
        names = findViewById(R.id.ap_name);
        emails = findViewById(R.id.ap_email);
        addresss = findViewById(R.id.ap_address);
        billamount = findViewById(R.id.billamount);
        btngenrate = findViewById(R.id.genrateqr);
        phones = findViewById(R.id.ap_phone);
        progressBar = findViewById(R.id.ap_pbbar);
        submit = findViewById(R.id.ap_submit);
        cardnumber = findViewById(R.id.cardnumber);
        imggenrate = findViewById(R.id.showqr);
        selectimg=findViewById(R.id.selectpic);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        PrefManager prefManager = new PrefManager(getApplicationContext());
        uid = prefManager.getUserID();
        Spinner spinner = (Spinner) findViewById(R.id.spinner);


        selectimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SelectImage();
            }
        });
        btngenrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(names.getText().toString().isEmpty() || emails.getText().toString().isEmpty() || addresss.getText().toString().isEmpty() || billamount.getText().toString().isEmpty() || phones.getText().toString().isEmpty() || cardnumber.getText().toString().isEmpty()){
                    Toast.makeText(AddHotels.this, "Please Fill All Fields", Toast.LENGTH_SHORT).show();
                }else{
                    Generateqrcode(cardnumber);
                }
            }
        });
        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        timepicker = (TimePicker) findViewById(R.id.timePicker1);
        //Uncomment the below line of code for 24 hour view
        timepicker.setIs24HourView(true);
        changetime = (Button) findViewById(R.id.button1);
        System.out.println("time is " + time);
        time = getCurrentTime();
        changetime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time = getCurrentTime();
                System.out.println("time is " + time);
            }
        });
        List<String> categories = new ArrayList<String>();
        categories.add("Faisalabad");
        categories.add("Lahore");
        categories.add("Islamabad");
        categories.add("Karachi");
        categories.add("Peshawar");
        categories.add("Multan");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (emails.getText().toString().isEmpty() && phones.getText().toString().isEmpty() && addresss.getText().toString().isEmpty() && names.getText().toString().isEmpty()) {
                    emails.setError("Complete Requirements");
                    names.setError("Complete Requirements");
                    addresss.setError("Complete Requirements");
                    phones.setError("Complete Requirements");
                    Toast.makeText(AddHotels.this, "Something is Empty", Toast.LENGTH_SHORT).show();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    AddApointment(names.getText().toString(), emails.getText().toString(), phones.getText().toString(), addresss.getText().toString(), time, uid, city,cardnumber.getText().toString(),url,billamount.getText().toString(),hotelurl);
                }
            }
        });

    }
    private void SelectImage()
    {

        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }

    public String getCurrentTime() {
        String currentTime = "Current Time: " + timepicker.getCurrentHour() + ":" + timepicker.getCurrentMinute();
        return currentTime;
    }

    public void AddApointment(String name, String email, String phone, String addres, String tim, String uid, String city,String bilnumber,String urls,String amount,String hurl) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("HotelPost");
        ApointmentModel apointmentModel = new ApointmentModel(name, email, phone, addres, tim, uid, city,bilnumber,urls,amount,hurl);

        databaseReference.child(uid).setValue(apointmentModel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(AddHotels.this, "Service Added", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(AddHotels.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        city = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + city, Toast.LENGTH_LONG).show();
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    public void Generateqrcode(EditText etText) {
        String myText = etText.getText().toString().trim();


        MultiFormatWriter mWriter = new MultiFormatWriter();
        try {

            BitMatrix mMatrix = mWriter.encode(myText, BarcodeFormat.QR_CODE, 400, 400);
            BarcodeEncoder mEncoder = new BarcodeEncoder();
            Bitmap mBitmap = mEncoder.createBitmap(mMatrix);
            filepath = getImageUri(this, mBitmap);
            imggenrate.setImageBitmap(mBitmap);
            uploadImage();
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(etText.getApplicationWindowToken(), 0);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    public Uri getImageUri(Context inContext, Bitmap imageCode) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        imageCode.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), imageCode, "Title", null);
        return Uri.parse(path);
    }

    // UploadImage method
    private void uploadImage() {
        if (filepath != null) {


            // Defining the child of storageReference
            StorageReference ref
                    = storageReference
                    .child(
                            "images/"
                                    + UUID.randomUUID().toString());

            // adding listeners on upload
            // or failure of image
            ref.putFile(filepath)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot) {
                                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {

                                            url= uri.toString();
                                            System.out.println("uri_____________"+uri.toString());
                                        }
                                    });
                                    Toast
                                            .makeText(AddHotels.this,
                                                    "Image Uploaded!!",
                                                    Toast.LENGTH_SHORT)
                                            .show();
                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {


                            Toast.makeText(AddHotels.this,
                                            "Failed " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    });

        }
    }
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data)
    {

        super.onActivityResult(requestCode,
                resultCode,
                data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePath);
                selectimg.setImageBitmap(bitmap);
                uploadImage2();
            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }
    private void uploadImage2()
    {
        if (filePath != null) {

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            StorageReference ref
                    = storageReference
                    .child(
                            "images/"
                                    + UUID.randomUUID().toString());

            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot) {
                                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {

                                            hotelurl= uri.toString();
                                            System.out.println("uri_____________"+uri.toString());
                                        }
                                    });

                                    // Image uploaded successfully
                                    // Dismiss dialog
                                    progressDialog.dismiss();
                                    Toast
                                            .makeText(AddHotels.this,
                                                    "Image Uploaded!!",
                                                    Toast.LENGTH_SHORT)
                                            .show();
                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast
                                    .makeText(AddHotels.this,
                                            "Failed " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {

                                // Progress Listener for loading
                                // percentage on the dialog box
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage(
                                            "Uploaded "
                                                    + (int)progress + "%");
                                }
                            });
        }
    }
}