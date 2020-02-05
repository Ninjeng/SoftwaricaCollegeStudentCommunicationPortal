package com.ninjeng.softwaricacollegestudentcommunicationportal.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.ninjeng.softwaricacollegestudentcommunicationportal.Adapter.ChatAdapter;
import com.ninjeng.softwaricacollegestudentcommunicationportal.Model.ChatList;
import com.ninjeng.softwaricacollegestudentcommunicationportal.Model.User;
import com.ninjeng.softwaricacollegestudentcommunicationportal.Notifications.Token;
import com.ninjeng.softwaricacollegestudentcommunicationportal.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {
    private RecyclerView recyclerView;
    private ChatAdapter chatAdapter;
    private List<User> mUsers;
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    List<ChatList> userList;

    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_chat, container, false);

        recyclerView = view.findViewById(R.id.friendRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        userList = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Chatlist").child(firebaseUser.getUid() );
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();
                for(DataSnapshot snapshot :dataSnapshot.getChildren()) {
                    ChatList chatList = snapshot.getValue(ChatList.class);
                    userList.add(chatList);

                }
                ReaChat();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        UpdateToken(FirebaseInstanceId.getInstance().getToken());

        return view;
    }

    private void ReaChat() {
        mUsers = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    User user = dataSnapshot1.getValue(User.class);
                    for(ChatList chatList: userList) {
                        if(user.getId().equals(chatList.getId()) ) {

                            mUsers.add(user);
                        }
                    }
                }
                chatAdapter = new ChatAdapter(getContext(),mUsers ,true);
                recyclerView.setAdapter(chatAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void UpdateToken(String token)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tokens");
        Token token1 = new Token(token);
        reference.child(firebaseUser.getUid()).setValue(token1);
    }


}

