package com.styleapp.styleappadm.connection_service.notifications;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Luis on 04/07/2017.
 */


public class NotificationPost {

    @SerializedName("to")
    @Expose
    private String to;
    @SerializedName("notification")
    @Expose
    private Notificacion notification;
    @SerializedName("data")
    @Expose
    private Datos data;

    public NotificationPost(String to, Notificacion notification, Datos data) {
        this.to = to;
        this.notification = notification;
        this.data = data;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Notificacion getNotification() {
        return notification;
    }

    public void setNotification(Notificacion notification) {
        this.notification = notification;
    }

    public Datos getData() {
        return data;
    }

    public void setData(Datos data) {
        this.data = data;
    }

}