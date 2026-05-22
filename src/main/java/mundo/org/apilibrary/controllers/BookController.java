package mundo.org.apilibrary.controllers;

import jakarta.validation.Valid;
import mundo.org.apilibrary.DTO.books.BookCreationDTO;
import mundo.org.apilibrary.DTO.books.BookDTO;
import mundo.org.apilibrary.entities.Book;
import mundo.org.apilibrary.mapper.BookMapper;
import mundo.org.apilibrary.payload.ApiResponse;
import mundo.org.apilibrary.services.BookService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;
    private final BookMapper bookMapper;

    public BookController(BookService bookService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BookDTO>>> listBooks() {
        List<BookDTO> booksDTO = this.bookService.findAllBooks();

        if (booksDTO.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        return ResponseEntity.ok(ApiResponse.success(booksDTO, "List of books retrieved!"));
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<BookDTO>> findBookById(@PathVariable UUID id) {
        BookDTO bookDTO = bookService.findById(id);

        if (bookDTO == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.ok(ApiResponse.success(bookDTO, "Book retrieved!"));
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<ApiResponse<BookDTO>> findBookByIsbn(@PathVariable String isbn) {
        BookDTO bookDTO = bookService.findByIsbn(isbn);

        if (bookDTO == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.ok(ApiResponse.success(bookDTO, "Book retrieved!"));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<BookDTO>> createBook(@Valid @RequestBody BookCreationDTO bookCreationDTO) {
        Book bookEntity = bookService.createBook(bookCreationDTO);
        BookDTO bookDTO = bookMapper.toDto(bookEntity);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(bookDTO, "Book created!"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<BookDTO>> updateBook(@PathVariable UUID id, @Valid @RequestBody BookCreationDTO bookCreationDTO) {
        Book bookEntity = bookService.updateBook(id, bookCreationDTO);

        /* TODO: manage the returned value without mapping to BookDTO inside the controller layer: check out
            bookService.updateBook **/
        BookDTO bookDTO = bookMapper.toDto(bookEntity);
        return ResponseEntity.ok(ApiResponse.success(bookDTO, "Book updated!"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> deleteBookById(@PathVariable UUID id) {
        BookDTO bookDTO = bookService.findById(id);

        if (bookDTO == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        bookService.deleteBook(id);
        return ResponseEntity.ok().build();
    }
}
