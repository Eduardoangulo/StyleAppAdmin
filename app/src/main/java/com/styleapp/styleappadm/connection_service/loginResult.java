package com.styleapp.styleappadm.connection_service;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.styleapp.styleappadm.model.Client;
//import com.styleappteam.styleapp.model.Client;

/**
 * Created by Luis on 29/06/2017.
 */

public class loginResult {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("worker")
    @Expose
    private Client worker;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

   public Client getClient() {
        return worker;
    }

    public void setClient(Client worker) {
        this.worker = worker;
    }

}
