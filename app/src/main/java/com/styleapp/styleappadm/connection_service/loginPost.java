package com.styleapp.styleappadm.connection_service;

/**
 * Created by Luis on 29/06/2017.
 */

public class loginPost {
    private String email;
    private String password;

    public loginPost(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
