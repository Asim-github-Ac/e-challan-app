package com.fyp.e_laboratory.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.fyp.e_laboratory.Admin_panel.Admin_Home_Page;
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
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LoginPage extends AppCompatActivity {
    private EditText mLoginEmail;
    private EditText mLoginPassword;
    ProgressDialog loadingBar;
    private Button mLogin_btn,mSign_up,forgetpassword;
    private Context mContext;

    private FirebaseAuth mAuth;
    List<UserData> userData=new ArrayList<>();
    AlertDialog dialog_verifying,profile_dialog;
    private ProgressDialog mRegProgress;
    private DatabaseReference mUserDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        mAuth = FirebaseAuth.getInstance();
        mUserDatabase = FirebaseDatabase.getInstance().getReference("HotelUsers");
        mLogin_btn=(Button)findViewById(R.id.lg_login);
        mSign_up=(Button)findViewById(R.id.lg_signup);

        forgetpassword=findViewById(R.id.forgetpass);
        mLoginEmail=(EditText)findViewById(R.id.lg_email);
        mRegProgress = new ProgressDialog(LoginPage.this);
        mLoginPassword=(EditText)findViewById(R.id.lg_pass);

        mContext = this;
        forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRecoverPasswordDialog();
            }
        });
        mSign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginPage.this,SignUpPage.class));
            }
        });

        mLogin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email=mLoginEmail.getText().toString().trim();
                String password=mLoginPassword.getText().toString().trim();


//                LayoutInflater inflater = getLayoutInflater();
//                View alertLayout= inflater.inflate(R.layout.profile_create_dialog,null);
//                AlertDialog.Builder show = new AlertDialog.Builder(mContext);
//                show.setView(alertLayout);
//                show.setCancelable(false);
//                dialog_verifying = show.create();
//                dialog_verifying.show();



                if (email.isEmpty() || password.isEmpty()){
                    Toast.makeText(mContext, "You can't leave fields empty", Toast.LENGTH_SHORT).show();
                }
                else {

                    mRegProgress.setTitle("Logging in");
                    mRegProgress.setMessage("Please wait while we create your account !");
                    mRegProgress.setCanceledOnTouchOutside(false);
                    mRegProgress.show();
                    if (email.equals("admin@gmail.com") && password.equals("admin")){
                        PrefManager prefManager=new PrefManager(getApplicationContext());
                        prefManager.setToken_Email("Admin");
                        Intent mainIntent = new Intent(LoginPage.this, Admin_Home_Page.class);
                        startActivity(mainIntent);
                        finish();
                    }else {
                        loginUser(email,password);
                    }
                }
            }
        });
    }
    private void showRecoverPasswordDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Recover Password");
        LinearLayout linearLayout=new LinearLayout(this);
        final EditText emailet= new EditText(this);

        // write the email using which you registered
        emailet.setText("Email");
        emailet.setMinEms(16);
        emailet.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        linearLayout.addView(emailet);
        linearLayout.setPadding(10,10,10,10);
        builder.setView(linearLayout);

        // Click on Recover and a email will be sent to your registered email id
        builder.setPositiveButton("Recover", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String email=emailet.getText().toString().trim();
                beginRecovery(email);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
    private void loginUser(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    //Log.e("rg", "onComplete: Failed=" + Objects.requireNonNull(task.getException()).getMessage());

                    String current_user_id = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                    FirebaseUser currentUser = mAuth.getCurrentUser();
                    System.out.println("user id__________________"+current_user_id);
                    mUserDatabase.child(current_user_id).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {


                                UserData userData = snapshot.getValue(UserData.class);
                                System.out.println("data ____________________"+userData.getSelect_type());
                            if (userData.getSelect_type().equals("Service Provider")){
                                PrefManager prefManager=new PrefManager(getApplicationContext());
                                prefManager.setToken_Email("Service Provider");
                                prefManager.setUserID(current_user_id);
                                Intent mainIntent = new Intent(LoginPage.this, Admin_Home_Page.class);
                                mainIntent.putExtra("id", current_user_id);
                                startActivity(mainIntent);
                            }else if (userData.getSelect_type().equals("User")){
                                PrefManager prefManager=new PrefManager(getApplicationContext());
                                prefManager.setToken_Email("User");
                                prefManager.setUserID(current_user_id);
                                Intent mainIntent = new Intent(LoginPage.this, MainActivity.class);
                                mainIntent.putExtra("id", current_user_id);
                                startActivity(mainIntent);
                                //   Toast.makeText(LoginPage.this, "Something went Wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(LoginPage.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
//                    currentUser.getIdToken(true).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<GetTokenResult> task) {
//                            if (task.isSuccessful()) {
//                                mUserDatabase.child(current_user_id).child("device_token").setValue(Objects.requireNonNull(task.getResult()).getToken()).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void aVoid) {
//
//
////                                        mRegProgress.dismiss();
////                                            PrefManager prefManager=new PrefManager(getApplicationContext());
////                                            prefManager.setToken_Email("User");
////                                            prefManager.setUserID(current_user_id);
////                                            Intent mainIntent = new Intent(LoginPage.this, MainActivity.class);
////                                            mainIntent.putExtra("id", current_user_id);
////                                            startActivity(mainIntent);
////                                            finish();
//                                        mUserDatabase.child(current_user_id).addValueEventListener(new ValueEventListener() {
//                                            @Override
//                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                                                    UserData userData=snapshot.getValue(UserData.class);
//                                                    if (userData.getUid().equals("Service Provider")){
//                                                        PrefManager prefManager=new PrefManager(getApplicationContext());
//                                                        prefManager.setToken_Email("Service Provider");
//                                                        prefManager.setUserID(current_user_id);
//                                                        Intent mainIntent = new Intent(LoginPage.this, Admin_Home_Page.class);
//                                                        mainIntent.putExtra("id", current_user_id);
//                                                        startActivity(mainIntent);
//                                                    }else if (userData.getUid().equals("User")){
//                                                        PrefManager prefManager=new PrefManager(getApplicationContext());
//                                                        prefManager.setToken_Email("User");
//                                                        prefManager.setUserID(current_user_id);
//                                                        Intent mainIntent = new Intent(LoginPage.this, MainActivity.class);
//                                                        mainIntent.putExtra("id", current_user_id);
//                                                        startActivity(mainIntent);
//                                                     //   Toast.makeText(LoginPage.this, "Something went Wrong", Toast.LENGTH_SHORT).show();
//                                                    }
//                                            }
//                                            @Override
//                                            public void onCancelled(@NonNull DatabaseError error) {
//                                                Toast.makeText(LoginPage.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
//                                            }
//                                        });
//                                    }
//                                });
//                            } else {
//                                mRegProgress.dismiss();
//                                Log.e("TAG", "exception=" + Objects.requireNonNull(task.getException()).toString());
//                                Toast.makeText(LoginPage.this, "Error - " +task.getException().toString(), Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });

                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mRegProgress.dismiss();
                Toast.makeText(mContext, "error"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void beginRecovery(String email) {
        loadingBar=new ProgressDialog(this);
        loadingBar.setMessage("Sending Email....");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        // calling sendPasswordResetEmail
        // open your email and write the new
        // password and then you can login
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                loadingBar.dismiss();
                if(task.isSuccessful())
                {
                    Toast.makeText(LoginPage.this,"Done sent",Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(LoginPage.this,"Error Occured",Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loadingBar.dismiss();
                Toast.makeText(LoginPage.this,"Error Failed"+e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser != null){
//
//        }else {
//            startActivity(new Intent(getApplicationContext(),MainActivity.class));
//            finish();
//        }
        PrefManager prefManager=new PrefManager(getApplicationContext());
        if (prefManager.getToken_Email().equals("User")){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }else if (prefManager.getToken_Email().equals("Service Provider"))  {
            startActivity(new Intent(getApplicationContext(),Admin_Home_Page.class));
            finish();
        }
    }
}