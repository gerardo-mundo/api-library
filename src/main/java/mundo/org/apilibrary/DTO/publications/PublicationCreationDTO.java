package mundo.org.apilibrary.DTO.publications;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import mundo.org.apilibrary.enums.PublicationType;

public record PublicationCreationDTO(
        @NotBlank String title,
        @NotBlank String author,
        String authorTwo,
        String authorThree,
        String authorFour,
        @NotBlank
        @Size(min = 8, max = 10)
        String issn,
        @NotBlank String publisher,
        @NotNull PublicationType type,
        String category,
        String volume
) {
}
