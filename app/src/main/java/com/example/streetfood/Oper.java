package com.example.streetfood;

import java.util.Date;

public class Oper{
    String username;
    String name;
    Date sessionExpiryDate;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSessionExpiryDate(Date sessionExpiryDate) {
        this.sessionExpiryDate = sessionExpiryDate;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public Date getSessionExpiryDate() {
        return sessionExpiryDate;
    }
}