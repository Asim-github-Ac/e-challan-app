package com.fyp.e_laboratory.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.fyp.e_laboratory.MainActivity;
import com.fyp.e_laboratory.Model.UserData;
import com.fyp.e_laboratory.R;
import com.fyp.e_laboratory.SharedPrefrence.PrefManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpPage extends AppCompatActivity  implements AdapterView.OnItemSelectedListener{
    private EditText mDisplayName, mEmail, mPassword, mCity, mPhone;
    private Button mCreateBtn;
    ProgressDialog progressDialog;
    private ProgressDialog mRegProgress;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    String appaccount;
    String[] account_type={"Select","Service Provider","User"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        Spinner spin = (Spinner) findViewById(R.id.simpleSpinner);
        spin.setOnItemSelectedListener(this);
        mRegProgress = new ProgressDialog(this);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("HotelUsers");
        mAuth = FirebaseAuth.getInstance();

        mRegProgress = new ProgressDialog(this);
        mDisplayName = (EditText) findViewById(R.id.reg_name);
        mEmail = (EditText) findViewById(R.id.reg_email);
        mPassword = (EditText) findViewById(R.id.reg_pass);
        mCity = (EditText) findViewById(R.id.reg_city);
        mPhone = findViewById(R.id.reg_phone);
        mCreateBtn = (Button) findViewById(R.id.register);

        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String display_name=mDisplayName.getText().toString();
                final String  email=mEmail.getText().toString();
                final String password=mPassword.getText().toString();
                final String city=mCity.getText().toString();
                String phone=mPhone.getText().toString().trim();
                if(!TextUtils.isEmpty(display_name) || !TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)) {
                    mRegProgress.setTitle("Registering User");
                    mRegProgress.setMessage("Please wait while we create your account !");
                    mRegProgress.setCanceledOnTouchOutside(false);

                    if (appaccount.equals("Select")){

                        Toast.makeText(SignUpPage.this, "Please Select Account Type", Toast.LENGTH_SHORT).show();
                    }else {
                        mRegProgress.show();
                        register_user(display_name, email, password, city,phone,appaccount);
                    }

                }else {
                    Toast.makeText(SignUpPage.this, "Try Again", Toast.LENGTH_SHORT).show();

                }
            }
        });
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,account_type);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);
    }


    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {
        appaccount= account_type[position];
        Toast.makeText(getApplicationContext(), account_type[position], Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
// TODO Auto-generated method stub

    }
    private void register_user(final String display_name, final String email, String password, final String city,String phone,String type) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    // dialog_verifying.dismiss();
                    FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                    assert current_user != null;
                    String uid = current_user.getUid();
                    UserData userData = new UserData(display_name,email,city,"default",uid,phone,type);
                    FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
                    mDatabase.child(uid).setValue(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                    firebaseFirestore.collection("HotelUsers").add(userData).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {

                                            Intent mainIntent = new Intent(SignUpPage.this, LoginPage.class);
                                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(mainIntent);
                                            finish();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(SignUpPage.this, "error"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                    });

                } else {
                    String task_result = task.getException().getMessage().toString();
                    mRegProgress.hide();
                    Toast.makeText(SignUpPage.this, task_result, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}