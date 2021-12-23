package com.homework.app.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HomeworkPayload {
    private String id;
    private String title;
    private String objectives;
    private Date createdAt;
    private String status;
    private Date deadline;
    private Date lastUpdatedAt;
}
