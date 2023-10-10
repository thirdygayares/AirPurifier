package com.airpurifier.airpurifier.Model;

public class AccountListModel {

    String username;
    boolean status;
    Boolean blocked;

    public AccountListModel(String username, boolean status, boolean blocked) {
        this.username = username;
        this.status = status;
        this.blocked = blocked;
    }

    public String getUsername() {
        return username;
    }

    public Boolean getBlocked() {
        return blocked;
    }

    public boolean isStatus() {
        return status;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

}
