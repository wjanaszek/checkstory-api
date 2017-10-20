package org.wjanaszek.checkstory.utils;

import org.wjanaszek.checkstory.persistance.model.User;

public class AuthenticationResponse {

    private User user;
    private String token;

    public AuthenticationResponse() {}

    public AuthenticationResponse(User user, String token) {
        this.user = user;
        this.token = token;
    }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
