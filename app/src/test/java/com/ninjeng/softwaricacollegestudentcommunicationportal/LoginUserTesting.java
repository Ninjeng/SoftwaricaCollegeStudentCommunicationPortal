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

public class LoginUserTesting {
    @Test
    public void logingUserTesting(){
        FirebaseAuth auth = Mockito.mock(FirebaseAuth.class);
        final Task<AuthResult> mockedAuth = Mockito.mock(Task.class);
        when(auth.signInWithEmailAndPassword("ninzenlama21@gmail.com","password"))
                .thenReturn(mockedAuth);
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Task<AuthResult> authResult = invocation.getArgument(0,Task.class);
                assertEquals(true,authResult.isSuccessful());
                return null;
            }
        });

    }
}
