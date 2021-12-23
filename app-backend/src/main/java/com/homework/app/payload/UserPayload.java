package com.homework.app.payload;

public class UserPayload {
    private String pId;
    private String pName;
    private String pUsername;
    private String pPassword;
    private String pRole;

    public UserPayload() {
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpUsername() {
        return pUsername;
    }

    public void setpUsername(String pUsername) {
        this.pUsername = pUsername;
    }

    public String getpPassword() {
        return pPassword;
    }

    public void setpPassword(String pPassword) {
        this.pPassword = pPassword;
    }

    public String getpRole() {
        return pRole;
    }

    public void setpRole(String pRole) {
        this.pRole = pRole;
    }
}
