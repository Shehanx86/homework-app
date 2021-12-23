package com.homework.app.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "homework")
public class Homework {

    @Id
    private String id;

    public Homework(String id, String title, String objectives, Date createdAt, String status, Date deadline, Date lastUpdatedAt) {
        this.id = id;
        this.title = title;
        this.objectives = objectives;
        this.createdAt = createdAt;
        this.status = status;
        this.deadline = deadline;
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public Homework() {
    }

    private String title;
    private String objectives;
    private Date createdAt;
    private String status;
    private Date deadline;
    private Date lastUpdatedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getObjectives() {
        return objectives;
    }

    public void setObjectives(String objectives) {
        this.objectives = objectives;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Date getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(Date lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }
}
