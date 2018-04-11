package com.example.alpha.reader_materialdesign.Domain;

/**
 * Created by Alpha on 2018/2/9.
 */

public class LoginMessage {
    private String status;
    private String login;
    private int userId;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
