package mundo.org.apilibrary.books;

import java.time.LocalDateTime;
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
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String authorTwo,
        String authorThree
) {}
