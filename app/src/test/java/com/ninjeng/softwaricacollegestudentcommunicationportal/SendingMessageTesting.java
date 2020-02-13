package com.ninjeng.softwaricacollegestudentcommunicationportal;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class SendingMessageTesting {


    @Test
    public void sendMessage(){
        FirebaseDatabase database = Mockito.mock(FirebaseDatabase.class);
        final DataSnapshot snapshot = Mockito.mock(DataSnapshot.class);

        when(database.getInstance().getReference("test").child("message").setValue("This is testing"));
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                DataSnapshot authResult = invocation.getArgument(0,DataSnapshot.class);
                assertEquals(false,authResult.exists());
                return null;
            }
        });
    }
}
