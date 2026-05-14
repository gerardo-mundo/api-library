package mundo.org.apilibrary.entities;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mundo.org.apilibrary.classes.Document;

@Entity
@Getter
@Setter
@Table(name = "thesis")
@NoArgsConstructor
@AllArgsConstructor
public class Thesis extends Document {
    private String authorTwo;
    private String authorThree;
    @Column(nullable = false)
    private String thesisAdvisor;
    @Column(nullable = false)
    private String university;
    @Column(nullable = false)
    private String bachelorDegree;
}
