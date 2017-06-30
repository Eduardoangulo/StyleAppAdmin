package com.styleapp.styleappadm.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by eduardo on 6/29/17.
 */

public class Type {
    @SerializedName("type_id")
    @Expose
    private Integer type_id;

    @SerializedName("type_name")
    @Expose
    private String type_name;

    @SerializedName("services")
    @Expose
    private List<Service> services = null;

    public Integer getType_id() {
        return type_id;
    }

    public void setType_id(Integer type_id) {
        this.type_id = type_id;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }
}
