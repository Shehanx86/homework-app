package com.homework.app.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserPayload {
    private String id;
    private String name;
    private String username;
    private String password;
    private String role;
}
