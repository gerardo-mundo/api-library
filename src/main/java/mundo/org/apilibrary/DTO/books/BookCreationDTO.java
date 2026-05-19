package mundo.org.apilibrary.DTO.books;

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
) {
}
