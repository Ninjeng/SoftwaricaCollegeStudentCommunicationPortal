package com.ninjeng.softwaricacollegestudentcommunicationportal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    TextInputLayout etStudenId, etPassword;
    Button btnLogin, btnSignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etStudenId=findViewById(R.id.studentId);
        etPassword=findViewById(R.id.password);
        btnLogin=findViewById(R.id.btnLogin);
        btnSignup=findViewById(R.id.btnCreateAccount);
        btnLogin.setOnClickListener(this);
        btnSignup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
            {
                SaveData();
                break;

            }
            case R.id.btnCreateAccount:
            {
                Intent intent = new Intent(this,SignupActivity.class);
                startActivity(intent);
                break;
            }
        }
    }

    private void SaveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserData",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("StudentId",etStudenId.getEditText().getText().toString());
        editor.putString("Password",etPassword.getEditText().getText().toString());
        editor.commit();
        Toast.makeText(this, "Data has been saved.", Toast.LENGTH_SHORT).show();
    }
}
