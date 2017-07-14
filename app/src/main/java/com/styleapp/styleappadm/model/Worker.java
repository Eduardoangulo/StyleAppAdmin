
package com.styleapp.styleappadm.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Worker {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("types")
    @Expose
    private List<Type> types;
    @SerializedName("addresses")
    @Expose
    private List<Address> addresses = null;
    @SerializedName("user")
    @Expose
    private User user;

    private String logedUsername;
    private String logedPassword;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }

    public String getLogedUsername() {
        return logedUsername;
    }

    public void setLogedUsername(String logedUsername) {
        this.logedUsername = logedUsername;
    }

    public String getLogedPassword() {
        return logedPassword;
    }

    public void setLogedPassword(String logedPassword) {
        this.logedPassword = logedPassword;
    }
}
