package mundo.org.apilibrary.DTO.publications;

import mundo.org.apilibrary.enums.PublicationType;

import java.time.LocalDateTime;
import java.util.UUID;

public record PublicationDTO(
        UUID id,
        String title,
        String author,
        String authorTwo,
        String authorThree,
        String authorFour,
        String issn,
        String publisher,
        PublicationType type,
        String category,
        String volume,
        LocalDateTime updatedAt
) {
}
