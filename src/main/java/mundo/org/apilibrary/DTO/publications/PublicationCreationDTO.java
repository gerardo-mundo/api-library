package mundo.org.apilibrary.DTO.publications;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
        @NotEmpty PublicationType type,
        String category,
        String volume
) {
}
