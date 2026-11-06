package mundo.org.apilibrary.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import mundo.org.apilibrary.classes.Publication;
import mundo.org.apilibrary.enums.PublicationType;

@Entity
@Getter
@Setter
@DiscriminatorValue(value = "PAPER")
public class Paper extends Publication {
    private String category;

    @Override
    public PublicationType getType() {
        return PublicationType.PAPER;
    }
}
