package com.ninjeng.softwaricacollegestudentcommunicationportal.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ninjeng.softwaricacollegestudentcommunicationportal.Adapter.MessageAdapter;
import com.ninjeng.softwaricacollegestudentcommunicationportal.Fragments.PeopleFragment;
import com.ninjeng.softwaricacollegestudentcommunicationportal.Model.Chat;
import com.ninjeng.softwaricacollegestudentcommunicationportal.Model.User;
import com.ninjeng.softwaricacollegestudentcommunicationportal.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageActivity extends AppCompatActivity {

    CircleImageView profile_image;
    TextView tvusername;
    List<Chat> chats;
    FirebaseUser firebaseUser;
    DatabaseReference refrences;
    MessageAdapter messageAdapter;
    Intent intent;
    RecyclerView recyclerView;
    ImageButton btnSendMessage;
    EditText texmessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        profile_image= findViewById(R.id.profile_image);
        tvusername=findViewById(R.id.username);
        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( getApplicationContext(),MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
        btnSendMessage=findViewById(R.id.btnsend);
        texmessage=findViewById(R.id.sendmessage);
        intent=getIntent();
        final String userid= intent.getStringExtra("userid");

        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message= texmessage.getText().toString();
                if(!message.equals(""))
                {
                    sendMessage(firebaseUser.getUid(),userid,message);
                }
                else
                {
                    Toast.makeText(MessageActivity.this, "There was error sending the message", Toast.LENGTH_SHORT).show();
                }
            }
        });
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        refrences= FirebaseDatabase.getInstance().getReference("Users").child(userid);
        refrences.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user= dataSnapshot.getValue(User.class);
                tvusername.setText(user.getFullname());
                if(user.getProfileImage().equals("default"))
                {
                    profile_image.setImageResource(R.mipmap.ic_launcher);
                }
                else {
                    Glide.with(getApplicationContext()).load(user.getProfileImage()).into(profile_image);
                }
                readMesage(firebaseUser.getUid(),userid,user.getProfileImage());
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void sendMessage(String sender, String reciver, String message)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("msgSender",sender);
        hashMap.put("msgReciver",reciver);
        hashMap.put("message",message);
        reference.child("Chat").push().setValue(hashMap);
        texmessage.setText("");
    }
    private void readMesage(final String myid, final String userid, final String imgUrl)
    {
        chats = new ArrayList<>();
        refrences = FirebaseDatabase.getInstance().getReference("Chat");
        refrences.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chats.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Chat chat = snapshot.getValue(Chat.class);
                    if(chat.getMsgReciver().equals(userid) && chat.getMsgSender().equals(myid)||
                    chat.getMsgReciver().equals(myid) && chat.getMsgSender().equals(userid))
                    {
                        chats.add(chat);
                    }
                    else
                    {

                    }
                    messageAdapter = new MessageAdapter(getApplicationContext(),chats,imgUrl);
                    recyclerView.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void status(String status)
    {
        refrences = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("status",status);

        refrences.updateChildren(hashMap);
    }

    @Override
    protected void onResume() {
        super.onResume();
        status("online");
    }

    @Override
    protected void onPause() {
        super.onPause();
        status("offline");
    }
}
