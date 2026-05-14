package mundo.org.apilibrary.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue(value = "PAPER")
public class Paper extends Publication {
    private String category;
}
