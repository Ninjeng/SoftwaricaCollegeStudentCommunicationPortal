package com.ninjeng.softwaricacollegestudentcommunicationportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    TextInputLayout etStudenId, etPassword;
    Button btnLogin, btnSignup;
    FirebaseUser firebaseUser;
    FirebaseAuth auth;
//    @Override
//    protected void onStart() {
//        super.onStart();
//        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//
//        if(firebaseUser!=null)
//        {
//            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//            startActivity(intent);
//            finish();
//        }
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etStudenId=findViewById(R.id.email);
        etPassword=findViewById(R.id.password);
        btnLogin=findViewById(R.id.btnLogin);
        btnSignup=findViewById(R.id.btnCreateAccount);
        btnLogin.setOnClickListener(this);
        btnSignup.setOnClickListener(this);
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
            {
                String email = etStudenId.getEditText().getText().toString();
                String password= etPassword.getEditText().getText().toString();
                if(TextUtils.isEmpty(email)|| TextUtils.isEmpty(password))
                {
                    Toast.makeText(this, "Enter Values", Toast.LENGTH_SHORT).show();
                }
                else
                {
                   auth.signInWithEmailAndPassword(email,password)
                           .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                               @Override
                               public void onComplete(@NonNull Task<AuthResult> task) {
                                   if(task.isSuccessful())
                                   {
                                       Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                       intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                       startActivity(intent);
                                       finish();
                                   }
                                   else {
                                       Toast.makeText(LoginActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                                   }
                               }
                           });
                }
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


}
