package com.ninjeng.softwaricacollegestudentcommunicationportal;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserTesting {

    @Test
    public void loginTest(){
        FirebaseAuth auth=FirebaseAuth.getInstance();
        try{
            AuthResult authResult = Tasks.await(auth.signInWithEmailAndPassword("username","password"));
            AuthResult a = authResult;
            Assert.assertNotNull(a);

        }catch (Exception e){
            Exception exception = e;
            Assert.assertNull(e);

        }
    }

}
