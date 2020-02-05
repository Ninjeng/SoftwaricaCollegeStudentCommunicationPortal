package com.ninjeng.softwaricacollegestudentcommunicationportal.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.ninjeng.softwaricacollegestudentcommunicationportal.Adapter.MessageAdapter;
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
    private String userid;
    MessageAdapter messageAdapter;
    Intent intent;
    RecyclerView recyclerView;
    ImageButton btnSendMessage,btnSendImage;
    EditText texmessage;
    private  String checker="",myuri;
    private StorageTask storageTask;
    private Uri fileUri;
    boolean notify = false;
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
        btnSendImage=findViewById(R.id.btnImage);
        texmessage=findViewById(R.id.sendmessage);
        intent=getIntent();
        userid= intent.getStringExtra("userid");
        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notify=true;
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
        btnSendImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence option[] = new CharSequence[]
                        {
                                "Images",
                                "PDF files",
                                "Ms word files"
                        };
                AlertDialog.Builder builder = new AlertDialog.Builder(MessageActivity.this);
                builder.setTitle("Select a file");
                builder.setItems(option, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which ==0)
                        {
                            checker ="image";
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            intent.setType("image/*");
                            startActivityForResult(intent.createChooser(intent,"Select image"),438);
                        }
                        if(which ==1)
                        {
                            checker ="image";
                        }
                        if(which ==2)
                        {
                            checker ="image";
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if( resultCode == 438 && data != null ) {
            fileUri = data.getData();
            if(!checker.equals("image"))
            {

            }
            else if(checker.equals("image"))
            {
                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("ImageFiless");
            }
            else {
                Toast.makeText(this, "Select a image", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void sendMessage(final String sender, final String reciver, String message)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("msgSender",sender);
        hashMap.put("msgReciver",reciver);
        hashMap.put("message",message);
        reference.child("Chat").push().setValue(hashMap);
        texmessage.setText("");
        final DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference("Chatlist")
                .child(reciver);
        chatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists())
                {
                    chatRef.child("id").setValue(sender);
                    chatRef.child("reciverid").setValue(reciver);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final String msg = message;
        DatabaseReference notific = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        notific.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if(notify)
                {
                }

                notify=false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
