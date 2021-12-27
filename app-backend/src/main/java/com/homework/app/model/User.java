package com.homework.app.model;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
public class User {

    @Id
    @ApiModelProperty(notes = "The unique id of the user",example = "1234", required = true)
    private String id;
    @ApiModelProperty(notes = "The name of the user",example = "Shehan")
    private String name;
    @ApiModelProperty(notes = "The username of the user",example = "Shehan_123")
    private String username;
    @ApiModelProperty(notes = "The password of the user",example = "abc123@")
    private String password;
    @ApiModelProperty(notes = "The role of the user", value = "teacher/student")
    private String role;

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
