package com.fyp.e_laboratory.Fragments;

import static android.app.Activity.RESULT_OK;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.graphics.PathUtils;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fyp.e_laboratory.Authentication.LoginPage;
import com.fyp.e_laboratory.PathUtilvideo;
import com.fyp.e_laboratory.R;
import com.fyp.e_laboratory.SharedPrefrence.PrefManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URISyntaxException;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import io.grpc.Compressor;


public class SettingsFragments extends Fragment {
    private CircleImageView profile;
    TextView username;
    RelativeLayout logout;
    private static final int GALLERY = 1;
    private ProgressDialog mRegProgress;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_settings_fragments, container, false);
        profile = (CircleImageView) v.findViewById(R.id.p_profile);
        username = (TextView) v.findViewById(R.id.p_name);
        logout = (RelativeLayout) v.findViewById(R.id.logout);

        mRegProgress = new ProgressDialog(getContext());

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRegProgress.setTitle("Logging Out");
                mRegProgress.setMessage("Please wait while we create your account !");
                mRegProgress.setCanceledOnTouchOutside(false);
                mRegProgress.show();

                Handler handler =new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent =new Intent(getContext(), LoginPage.class);
                        startActivity(intent);
                        getActivity().finish();
                        PrefManager prefManager=new PrefManager(getContext());
                        prefManager.setToken_Email("");
                    }
                },2000);

            }
        });


        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);//
                startActivityForResult(Intent.createChooser(intent, "Select File"), GALLERY);

            }
        });
        getUserData();
        return v;

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY && resultCode == RESULT_OK) {
            if (data.getData() != null) {
                final Uri imageUri = data.getData();
                uploadImage(imageUri);
            }
        }
    }
    public void getUserData(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("ELabUsers").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child("name").getValue().toString();
                username.setText(name);
//                String image = snapshot.child("image").getValue().toString();
//
//                if (!image.equalsIgnoreCase("default")){
//
//                    Picasso.get()
//                            .load(image)
//
//                            .centerCrop()
//                            .placeholder(R.drawable.ic_baseline_chat_24)
//                            .into(profile);
//                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void uploadImage(Uri uri){
        StorageReference mImageStorage = FirebaseStorage.getInstance().getReference();


        String current_uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final StorageReference filepath = mImageStorage.child("profile_images").child(current_uid + ".jpg");









    }
}