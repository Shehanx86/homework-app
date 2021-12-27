package com.homework.app.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "homework")
public class Homework {

    @ApiModelProperty(notes = "Unique id of the homework", example = "1234", required = true)
    @Id
    private String id;
    @ApiModelProperty(notes = "title of the homework", example = "Maths Homework")
    private String title;
    @ApiModelProperty(notes = "Objective of the homework", example = "Getting ready for finals")
    private String objectives;
    @ApiModelProperty(notes = "Date and time of creation of the homework. This will be auto generated", example = "Monday, December 27, 2021")
    private Date createdAt;
    @ApiModelProperty(notes = "Status of the homework", dataType = "String", example = "finished")
    private String status;
    @ApiModelProperty(notes = "deadline of the homework", dataType = "Date", example = "Monday, December 27, 2021")
    private Date deadline;
    @ApiModelProperty(notes = "Date and time of of the homework which it last updated. This will be auto generated", example = "Monday, December 27, 2021")
    private Date lastUpdatedAt;
    @ApiModelProperty(notes = "The username of the teacher who assigned the homework", example = "Shehan")
    private String assignedBy;
    @ApiModelProperty(notes = "The username of the assigned student",example = "Shehan")
    private String assignedTo;

    public Homework() {
    }

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

    public String getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(String assignedBy) {
        this.assignedBy = assignedBy;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }
}
