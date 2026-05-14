package mundo.org.apilibrary.books;

import java.time.LocalDateTime;
import java.util.UUID;

public record BookCreationDTO(
                              String title,
                              String author,
                              String authorTwo,
                              String authorThree,
                              String publisher,
                              String collection,
                              int acquisition,
                              String isbn,
                              boolean available
                              ) { }
