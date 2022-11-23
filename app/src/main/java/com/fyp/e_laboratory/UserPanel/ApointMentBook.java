package com.fyp.e_laboratory.UserPanel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ApointMentBook extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TimePicker timepicker;
    Button changetime, submit;
    String uid;
    String time;
    Button btngenrate;
    ImageView imggenrate;
    String url;
    FirebaseStorage storage;
    StorageReference storageReference;
    Uri filepath;
    String city;
    EditText emails, names, phones, addresss, billamount, cardnumber;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apoint_ment_book);
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
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        PrefManager prefManager = new PrefManager(getApplicationContext());
        uid = prefManager.getUserID();
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        btngenrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(names.getText().toString().isEmpty() || emails.getText().toString().isEmpty() || addresss.getText().toString().isEmpty() || billamount.getText().toString().isEmpty() || phones.getText().toString().isEmpty() || cardnumber.getText().toString().isEmpty()){
                    Toast.makeText(ApointMentBook.this, "Please Fill All Fields", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(ApointMentBook.this, "Something is Empty", Toast.LENGTH_SHORT).show();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    AddApointment(names.getText().toString(), emails.getText().toString(), phones.getText().toString(), addresss.getText().toString(), time, uid, city,cardnumber.getText().toString(),url,billamount.getText().toString());
                }
            }
        });
    }

    public String getCurrentTime() {
        String currentTime = "Current Time: " + timepicker.getCurrentHour() + ":" + timepicker.getCurrentMinute();
        return currentTime;
    }

    public void AddApointment(String name, String email, String phone, String addres, String tim, String uid, String city,String bilnumber,String urls,String amount) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("HotelPost");
       // ApointmentModel apointmentModel = new ApointmentModel(name, email, phone, addres, tim, uid, city,bilnumber,urls,amount);

//        databaseReference.child(uid).setValue(apointmentModel).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void unused) {
//                progressBar.setVisibility(View.INVISIBLE);
//                Toast.makeText(ApointMentBook.this, "ApointMent Added", Toast.LENGTH_SHORT).show();
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//
//                progressBar.setVisibility(View.INVISIBLE);
//                Toast.makeText(ApointMentBook.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
//            }
//        });
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
                                            .makeText(ApointMentBook.this,
                                                    "Image Uploaded!!",
                                                    Toast.LENGTH_SHORT)
                                            .show();
                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {


                            Toast.makeText(ApointMentBook.this,
                                            "Failed " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    });

        }
    }
}