package com.styleapp.styleappadm.connection_service;

import com.styleapp.styleappadm.connection_service.position.PositionPost;
import com.styleapp.styleappadm.connection_service.position.PositionResponse;
import com.styleapp.styleappadm.connection_service.status.StatusPost;
import com.styleapp.styleappadm.connection_service.status.StatusResponse;
import com.styleapp.styleappadm.model.DetailService;

import java.util.ArrayList;

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

    @Headers({ "Content-Type: application/json"})
    @POST("details/worker")
    Call<ArrayList<DetailService>> getWorkerHistory(@Body WorkerDetailPost wdp);

    @Headers({ "Content-Type: application/json"})
    @POST("details/done")
    Call<StatusResponse> doneService(@Body StatusPost statusPost);

    @Headers({ "Content-Type: application/json"})
    @POST("details/canceled")
    Call<StatusResponse> cancelService(@Body StatusPost statusPost);

    @Headers({ "Content-Type: application/json"})
    @POST("details/confirm")
    Call<StatusResponse> confirmarServicio(@Body StatusPost statusPost);

    @Headers({ "Content-Type: application/json"})
    @POST("users/changePosition")
    Call<PositionResponse> cambiarUbicacion(@Body PositionPost positionPost);
}
