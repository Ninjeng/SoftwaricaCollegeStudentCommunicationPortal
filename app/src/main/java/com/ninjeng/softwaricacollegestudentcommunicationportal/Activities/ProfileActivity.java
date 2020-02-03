package com.ninjeng.softwaricacollegestudentcommunicationportal.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.ninjeng.softwaricacollegestudentcommunicationportal.Model.User;
import com.ninjeng.softwaricacollegestudentcommunicationportal.R;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    CircleImageView circleImageView;
    TextView username, email;
    Button btnSignOut;
    Intent intent;
    DatabaseReference reference;
    FirebaseUser firebaseUser;
     String fuId="";
     ProgressBar progressBar;
    StorageReference storageReference;
    private Uri imageURl;
    private StorageTask uploadsTask;

    public ProfileActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        intent=getIntent();
        final String userid= intent.getStringExtra("userid");
        fuId=userid;
        storageReference = FirebaseStorage.getInstance().getReference("Uploads");
        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( getApplicationContext(),MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
        circleImageView = findViewById(R.id.pflImage);
        username = findViewById(R.id.pflusername);
        email = findViewById(R.id.pflemail);
        btnSignOut= findViewById(R.id.btnSignOut);
        progressBar = findViewById(R.id.progressBar);
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
            }
        });

        reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                username.setText(user.getFullname());
                email.setText(user.getEmail());
                if(user.getProfileImage().equals("default"))
                {
                    circleImageView.setImageResource(R.mipmap.ic_launcher);
                }
                else
                {
                    Glide.with(getApplicationContext()).load(user.getProfileImage()).into(circleImageView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImage();
            }
        });
    }

    private void openImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }
    private String getFileExtension(Uri uri)
    {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if( resultCode == RESULT_OK && data != null )
        {
            imageURl =data.getData();
            uploadImage();
        }


    }

    private void uploadImage()
    {
        progressBar.setVisibility(View.VISIBLE);
        if(imageURl !=null)
        {
             final  StorageReference fileReference = storageReference.child(System.currentTimeMillis()
             +"."+getFileExtension(imageURl));
             uploadsTask =fileReference.putFile(imageURl);
             uploadsTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot,Task<Uri>> (){
             @Override
             public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                 if(!task.isSuccessful())
                 {
                     throw task.getException();
                 }
                 return fileReference.getDownloadUrl();
             }
             }).addOnCompleteListener(new OnCompleteListener<Uri>() {
             @Override
             public void onComplete(@NonNull Task<Uri> task) {
                if(task.isSuccessful())
                {
                    Uri downloadUri = task.getResult();
                    String mUri = downloadUri.toString();

                    reference = FirebaseDatabase.getInstance().getReference("Users").child(fuId);
                    HashMap<String,Object> map = new HashMap<>();
                    map.put("profileImage",mUri);
                    reference.updateChildren(map);
                    progressBar.setVisibility(View.INVISIBLE);
                }
                else 
                {
                    Toast.makeText(ProfileActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }
             }
             }).addOnFailureListener(new OnFailureListener() {
             @Override
             public void onFailure(@NonNull Exception e) {
                 Toast.makeText(ProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
             }
         });
        }
        else {
         Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
         progressBar.setVisibility(View.INVISIBLE);
        }
    }
}

