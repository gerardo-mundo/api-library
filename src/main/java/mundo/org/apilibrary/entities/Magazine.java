package mundo.org.apilibrary.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import mundo.org.apilibrary.classes.Publication;

@Entity
@Getter
@Setter
@DiscriminatorValue(value = "MAGAZINE")
public class Magazine extends Publication {
    private String volume;
}
