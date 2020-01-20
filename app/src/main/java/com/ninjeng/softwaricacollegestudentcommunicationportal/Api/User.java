package com.ninjeng.softwaricacollegestudentcommunicationportal.Api;

import com.ninjeng.softwaricacollegestudentcommunicationportal.Response.SignupResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface User {
    @POST("user/signup")
    Call<SignupResponse> registerUser(@Body User user);
}
