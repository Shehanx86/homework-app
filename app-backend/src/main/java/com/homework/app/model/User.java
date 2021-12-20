package com.homework.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {

    @Id
    private String id;
    private String name;
    private String username;
    private String password;
    private String userType;
    private List<String> roles;

}
