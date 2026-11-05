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
@DiscriminatorValue(value = "MAGAZINE")
public class Magazine extends Publication {
    private String volume;

    @Override
    public PublicationType getType() {
        return PublicationType.MAGAZINE;
    }
}
