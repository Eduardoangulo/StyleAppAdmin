package com.styleapp.styleappadm.connection_service;

/**
 * Created by Luis on 29/06/2017.
 */

public class loginPost {
    private String email;
    private String password;
    private String _token;

    public loginPost(String email, String password, String token) {
        this.email = email;
        this.password = password;
        this._token=token;
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

    public String getToken() {
        return _token;
    }

    public void setToken(String token) {
        this._token = token;
    }
}
