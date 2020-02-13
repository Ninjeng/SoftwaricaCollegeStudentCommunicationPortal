package com.ninjeng.softwaricacollegestudentcommunicationportal;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class RestingPasswordTesting {

    @Test
    public void ResetPasswordTestin(){
        FirebaseAuth auth = Mockito.mock(FirebaseAuth.class);
        final Task<Void> mockedAuth = Mockito.mock(Task.class);
        when(auth.sendPasswordResetEmail("gyawat@gmail.com"))
        .thenReturn(mockedAuth);
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Task<Void> authResult = invocation.getArgument(0,Task.class);
                assertEquals(true,authResult.isSuccessful());
                return null;
            }
        });

    }

}
