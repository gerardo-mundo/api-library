package mundo.org.apilibrary.entities;

import jakarta.persistence.*;
import lombok.*;
import mundo.org.apilibrary.classes.Document;


@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book extends Document {
    private String authorTwo;
    private String authorThree;
}
