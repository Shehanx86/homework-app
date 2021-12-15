package Model;

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
    String id;
    String title;
    String objectives;
    Date divenDate;
    Date deadline;
}
