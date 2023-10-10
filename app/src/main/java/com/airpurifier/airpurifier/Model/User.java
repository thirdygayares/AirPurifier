package com.airpurifier.airpurifier.Model;

public class User {
    private int id;
    private boolean isAdmin;

    // Constructor, getters, setters...

    public User(int id, boolean isAdmin) {
        this.id = id;
        this.isAdmin = isAdmin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}

