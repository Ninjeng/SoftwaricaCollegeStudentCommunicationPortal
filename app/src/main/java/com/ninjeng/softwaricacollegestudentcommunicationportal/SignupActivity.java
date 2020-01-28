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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.ninjeng.softwaricacollegestudentcommunicationportal.Model.User;

public class SignupActivity extends AppCompatActivity {
    TextInputLayout etFullname, etEmail, etPassword, etConfirmPassword, etStudentId;
    Spinner spinnerBatch;
    Button btnSignUp;
    TextView textView;
    private FirebaseAuth myauth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        etFullname = findViewById(R.id.tvfullName);
        spinnerBatch = findViewById(R.id.spinnetBatch);
        textView= findViewById(R.id.tvlogin);
        etEmail = findViewById(R.id.etEmail);
        btnSignUp = findViewById(R.id.btnGetStarted);
        etStudentId = findViewById(R.id.tvStudentId);
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

    @Override
    protected void onStart() {
        super.onStart();
        if(myauth.getCurrentUser() !=null)
        {


        }
    }

    private void registerUser() {
        final String id= etStudentId.getEditText().toString();
        final String fullname= etFullname.getEditText().toString();
        final String batch= spinnerBatch.getSelectedItem().toString();
        final String email= etEmail.getEditText().toString();
        final String password= etPassword.getEditText().toString();

        progressBar.setVisibility(View.VISIBLE);
        myauth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            User user = new User(id,fullname,batch,email);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progressBar.setVisibility(View.GONE);
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(SignupActivity.this, "User created", Toast.LENGTH_SHORT).show();
                                    }
                                    else 
                                    {
                                        Toast.makeText(SignupActivity.this, "Failed to create user", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                        else
                        {
                            Toast.makeText(SignupActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}