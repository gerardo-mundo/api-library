package mundo.org.apilibrary.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import mundo.org.apilibrary.classes.Publication;

@Entity
@Getter
@Setter
@DiscriminatorValue(value = "PAPER")
public class Paper extends Publication {
    private String category;
}
