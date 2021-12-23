package com.homework.app.payload;

import java.util.Date;

public class HomeworkPayload {
    private String pId;
    private String pTitle;
    private String pObjectives;
    private Date pCreatedAt;
    private String pStatus;
    private Date pDeadline;
    private Date pLastUpdatedAt;

    public HomeworkPayload() {
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getpTitle() {
        return pTitle;
    }

    public void setpTitle(String pTitle) {
        this.pTitle = pTitle;
    }

    public String getpObjectives() {
        return pObjectives;
    }

    public void setpObjectives(String pObjectives) {
        this.pObjectives = pObjectives;
    }

    public Date getpCreatedAt() {
        return pCreatedAt;
    }

    public void setpCreatedAt(Date pCreatedAt) {
        this.pCreatedAt = pCreatedAt;
    }

    public String getpStatus() {
        return pStatus;
    }

    public void setpStatus(String pStatus) {
        this.pStatus = pStatus;
    }

    public Date getpDeadline() {
        return pDeadline;
    }

    public void setpDeadline(Date pDeadline) {
        this.pDeadline = pDeadline;
    }

    public Date getpLastUpdatedAt() {
        return pLastUpdatedAt;
    }

    public void setpLastUpdatedAt(Date pLastUpdatedAt) {
        this.pLastUpdatedAt = pLastUpdatedAt;
    }
}
