package com.example.macstudent.rfapp;

/**
 * Created by macstudent on 2018-01-18.
 */
import retrofit2.Call;
import retrofit2.http.GET;

public interface RequestInterface {
    @GET("api/userinfo")
    Call<JSONResponse> getJSON();
}
