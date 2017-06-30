package com.styleapp.styleappadm.connection_service;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Luis on 14/06/2017.
 */

public interface styleapp_API {

    @Headers({ "Content-Type: application/json"})
    @POST("workers/login")
    Call<loginResult> login(@Body loginPost log);

}
