package mundo.org.apilibrary.classes;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import mundo.org.apilibrary.entities.Magazine;
import mundo.org.apilibrary.entities.Paper;

@Entity
@Table(name = "publications")
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", length = 12)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include =  JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Magazine.class, name = "MAGAZINE"),
        @JsonSubTypes.Type(value = Paper.class, name = "PAPER"),
})
public abstract class Publication extends Document {
    private String authorTwo;
    private String authorThree;
    private String authorFour;
    @Column(length = 10,  nullable = false)
    private String issn;
    @Column(nullable = false)
    private String publisher;
}

