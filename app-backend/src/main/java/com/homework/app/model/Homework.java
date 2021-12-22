package com.homework.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "homework")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Homework {

    @Id
    private String id;
    private String title;
    private String objectives;
    private Date createdAt;
    private String status;
    private Date deadline;
    private Date lastUpdatedAt;

}
