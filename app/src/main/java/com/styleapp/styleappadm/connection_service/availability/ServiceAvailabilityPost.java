package com.styleapp.styleappadm.connection_service.availability;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Luis on 17/07/2017.
 */

public class ServiceAvailabilityPost {
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("type_id")
    @Expose
    private Integer type_id;
    @SerializedName("status")
    @Expose
    private Integer status;

    public ServiceAvailabilityPost(Integer userId, Integer type_id, Integer status) {
        this.userId = userId;
        this.type_id = type_id;
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getType_id() {
        return type_id;
    }

    public void setType_id(Integer type_id) {
        this.type_id = type_id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
