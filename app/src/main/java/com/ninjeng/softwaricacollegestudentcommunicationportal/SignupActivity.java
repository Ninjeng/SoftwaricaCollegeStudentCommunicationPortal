package com.ninjeng.softwaricacollegestudentcommunicationportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ninjeng.softwaricacollegestudentcommunicationportal.Model.User;

public class SignupActivity extends AppCompatActivity {
    TextInputLayout etFullname, etEmail, etPassword, etConfirmPassword, etStudentId;
    Spinner spinnerBatch;
    Button btnSignUp;
    TextView textView;
    private FirebaseAuth myauth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        etFullname = findViewById(R.id.tvfullName);
        spinnerBatch = findViewById(R.id.spinnetBatch);
        textView= findViewById(R.id.tvlogin);
        etEmail = findViewById(R.id.etEmail);
        btnSignUp = findViewById(R.id.btnGetStarted);
        etStudentId = findViewById(R.id.tvstdId);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etconfirmPassword);
        String batch[] = {"21A", "21B", "21C", "22A", "22B", "22C", "22D", "23A", "23B", "24A", "24B", "24C", "25A", "25B", "25C", "26A", "26B"};
        ArrayAdapter batchAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, batch);
        spinnerBatch.setAdapter(batchAdapter);
        myauth = FirebaseAuth.getInstance();
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        final String id= etStudentId.getEditText().getText().toString();
        final String name= etFullname.getEditText().getText().toString();
        final String batch= spinnerBatch.getSelectedItem().toString();
        final String email= etEmail.getEditText().getText().toString();
        final String password= etPassword.getEditText().getText().toString();
        final String profileimage= "default";
        myauth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            User user = new User(id,name,email,batch,profileimage);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user);
                            Toast.makeText(SignupActivity.this, "User created", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(SignupActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}