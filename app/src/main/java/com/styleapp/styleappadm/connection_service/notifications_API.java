package com.styleapp.styleappadm.connection_service;

import com.styleapp.styleappadm.connection_service.notifications.NotificationPost;
import com.styleapp.styleappadm.connection_service.notifications.NotificationResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import static com.styleapp.styleappadm.VariablesGlobales.keyNoti;

/**
 * Created by Luis on 04/07/2017.
 */

public interface notifications_API {
    @Headers({ "Content-Type: application/json", "Authorization: key="+keyNoti})
    @POST("fcm/send")
    Call<NotificationResponse> enviarNotificacion(@Body NotificationPost notificationPost);
}
