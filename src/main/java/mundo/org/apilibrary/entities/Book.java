package mundo.org.apilibrary.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    @Column(nullable = false)
    private String publisher;
    @Column(nullable = false)
    private String collection;
    @Column(nullable = false, length = 13)
    private String isbn;
    @Column(nullable = false, unique = true)
    private int acquisition;
}
