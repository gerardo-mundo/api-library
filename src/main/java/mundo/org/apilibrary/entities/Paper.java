package mundo.org.apilibrary.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import mundo.org.apilibrary.classes.Publication;

@Entity
@DiscriminatorValue(value = "PAPER")
public class Paper extends Publication {
    private String category;
}
