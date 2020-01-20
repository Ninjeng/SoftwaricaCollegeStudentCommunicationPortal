package com.ninjeng.softwaricacollegestudentcommunicationportal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class SignupActivity extends AppCompatActivity {
    TextInputLayout etFullname, etEmail, etPassword, etConfirmPassword, etStudentId;
    Spinner spinnerBatch;
    Button btnSignUp;
    TextView textView;

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
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}