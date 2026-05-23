package mundo.org.apilibrary.DTO.books;

import java.util.UUID;

public record BookDTO(
        UUID id,
        String title,
        String author,
        String publisher,
        String collection,
        int acquisition,
        String isbn,
        boolean available,
        String authorTwo,
        String authorThree
) {
}
