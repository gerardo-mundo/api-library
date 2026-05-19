package mundo.org.apilibrary.controllers;

import mundo.org.apilibrary.DTO.books.BookCreationDTO;
import mundo.org.apilibrary.DTO.books.BookDTO;
import mundo.org.apilibrary.mapper.BookMapper;
import mundo.org.apilibrary.payload.ApiResponse;
import mundo.org.apilibrary.services.BookService;

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
        var books = this.bookService.findAllBooks();

        if (books.isEmpty()) {
            return ResponseEntity.ok(ApiResponse.success(null, "No books were found"));
        }

        var booksDTO = this.bookMapper.toListDto(books);
        var response = ApiResponse.success(booksDTO, "List of books retrieved!");
        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<BookDTO>> findBookById(@PathVariable UUID id) {
        var book = bookService.findById(id);

        var bookDto = bookMapper.toDto(book);

        var response = ApiResponse.success(bookDto, "Book retrieved!");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<BookDTO>> createBook(BookCreationDTO bookCreationDTO) {
        var book = this.bookService.createBook(bookCreationDTO);

        if(book == null) {
            return ResponseEntity.ok(ApiResponse.failure("Book could not be created!"));
        }

        var bookDTO = bookMapper.toDto(book);
        return ResponseEntity.ok(ApiResponse.success(bookDTO, "Book created!"));
    }
}
