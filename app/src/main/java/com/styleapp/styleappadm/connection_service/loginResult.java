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
    @SerializedName("client")
    @Expose
    private Client client;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

   public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

}
