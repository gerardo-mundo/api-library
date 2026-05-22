package mundo.org.apilibrary.DTO.books;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BookCreationDTO(
        @NotBlank
        String title,
        @NotBlank
        String author,
        String authorTwo,
        String authorThree,
        @NotBlank
        String publisher,
        @NotBlank
        String collection,
        @Min(1) @Max(30000)
        int acquisition,
        @NotBlank @Size(min = 10, max = 13)
        String isbn,
        boolean available
) {
}
