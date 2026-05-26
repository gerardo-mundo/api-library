package mundo.org.apilibrary.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import mundo.org.apilibrary.classes.Publication;

@Entity
@DiscriminatorValue(value = "MAGAZINE")
public class Magazine extends Publication {
    private String volume;
}
