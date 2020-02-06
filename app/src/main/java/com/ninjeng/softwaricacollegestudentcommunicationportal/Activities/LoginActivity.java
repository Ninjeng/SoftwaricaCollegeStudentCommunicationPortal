package com.ninjeng.softwaricacollegestudentcommunicationportal.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ninjeng.softwaricacollegestudentcommunicationportal.Model.User;
import com.ninjeng.softwaricacollegestudentcommunicationportal.Notification.CreateChannel;
import com.ninjeng.softwaricacollegestudentcommunicationportal.R;
import com.ninjeng.softwaricacollegestudentcommunicationportal.StrictModeClass;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    TextInputLayout etStudenId, etPassword;
    Button btnLogin, btnSignup;
    FirebaseUser firebaseUser;
    TextView tvForgotPassword;
    ProgressBar progressBar;
    NotificationManagerCompat notificationManagerCompat;
    DatabaseReference reference;
    FirebaseAuth auth;
    @Override
    protected void onStart() {
        super.onStart();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if(firebaseUser!=null)
        {
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        notificationManagerCompat = NotificationManagerCompat.from(this);
        etStudenId=findViewById(R.id.email);
        tvForgotPassword=findViewById(R.id.forgotPassword);
        etPassword=findViewById(R.id.password);
        btnLogin=findViewById(R.id.btnLogin);
        btnSignup=findViewById(R.id.btnCreateAccount);
        btnLogin.setOnClickListener(this);
        btnSignup.setOnClickListener(this);
        auth = FirebaseAuth.getInstance();
        progressBar= findViewById(R.id.progressBar);
        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent( getApplicationContext(),ResetPasswordActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
            {
                progressBar.setVisibility(View.VISIBLE);
                String email = etStudenId.getEditText().getText().toString();
                String password= etPassword.getEditText().getText().toString();
                if(TextUtils.isEmpty(email)|| TextUtils.isEmpty(password))
                {
                    progressBar.setVisibility(View.INVISIBLE);
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
                                       progressBar.setVisibility(View.INVISIBLE);
                                       DisplayNotification();

                                   }
                                   else {
                                       progressBar.setVisibility(View.INVISIBLE);
                                       Toast.makeText(LoginActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                                   }
                               }
                           });
//                    if(checkingLogin(email,password)){
//                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(intent);
//                        finish();
//                        progressBar.setVisibility(View.INVISIBLE);
//                        DisplayNotification();
//                    }else {
//                        progressBar.setVisibility(View.INVISIBLE);
//                        Toast.makeText(LoginActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
//                    }
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
    private void DisplayNotification() {
        reference= FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final User user = dataSnapshot.getValue(User.class);
                Notification notification = new NotificationCompat.Builder(getApplicationContext(), CreateChannel.CHANNEL_1)
                        .setSmallIcon(R.drawable.ic_action_person)
                        .setContentTitle("Welcome ").setContentText(user.getFullname())
                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                        .build();
                notificationManagerCompat.notify(1,notification);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//

    }


    public boolean checkingLogin (String username, String password){
        StrictModeClass.StrictMode();
        try{
            AuthResult authResult = Tasks.await(auth.signInWithEmailAndPassword(username,password));
            if(authResult==null)
                return false;
            else
                return true;

        }catch (Exception e){
            return false;
        }

//        auth.signInWithEmailAndPassword(username,password)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if(task.isSuccessful())
//                        {
//                            return;
//                        }
//                        else {
//
//                        }
//                    }
//                });

//        AuthResult authResult = auth.signInWithEmailAndPassword(username,password).getResult();
//        Tasks.await(auth.signInWithEmailAndPassword(username,password));
//        return  true;

    }



}
