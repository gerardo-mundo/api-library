package mundo.org.apilibrary.services;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

import mundo.org.apilibrary.DTO.books.BookCreationDTO;
import mundo.org.apilibrary.DTO.books.BookDTO;
import mundo.org.apilibrary.entities.Book;
import mundo.org.apilibrary.mapper.BookMapper;
import mundo.org.apilibrary.repository.BookRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookMapper bookMapper;
    @InjectMocks
    private BookService bookService;


    private Book book;
    private UUID bookId;
    private BookDTO bookDTO;
    private BookCreationDTO bookCreationDTO;
    private String isbn;
    private Integer acquisition;

    @BeforeEach
    void setup() {
        book = new Book();
        bookId = UUID.randomUUID();
        isbn = "1234567890123";
        acquisition = 25666;

        bookCreationDTO = new BookCreationDTO(
                null,
                null,
                null,
                null,
                null,
                null,
                acquisition,
                isbn,
                true
        );

        bookDTO = new BookDTO(
                bookId,
                null,
                null,
                null,
                null,
                acquisition,
                isbn,
                true,
                null,
                null
        );
    }

    @Test
    void createBook_shouldReturnAnException_whenIsbnExists() {
        when(bookRepository.existsByIsbn(isbn)).thenReturn(true);

        EntityExistsException exception = assertThrows(EntityExistsException.class,
                () -> bookService.createBook(bookCreationDTO));

        assertEquals("Book with ISBN " + isbn + " already exists", exception.getMessage());
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void createBook_shouldReturnBookDto_whenSuccessful() {
        when(bookRepository.existsByIsbn(isbn)).thenReturn(false);
        when(bookMapper.toEntity(bookCreationDTO)).thenReturn(book);
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book result = bookService.createBook(bookCreationDTO);

        assertNotNull(result);
        assertEquals(book, result);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void updateBook_shouldReturnBookDto_whenSuccessful() {
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookRepository.existsByAcquisitionAndIdNot(acquisition, bookId)).thenReturn(false);
        when(bookRepository.existsByIsbnAndIdNot(isbn, bookId)).thenReturn(false);
        doNothing().when(bookMapper).updateEntityFromDto(any(BookCreationDTO.class), any(Book.class));
        when(bookRepository.saveAndFlush(any(Book.class))).thenReturn(book);
        when(bookMapper.toDto(any(Book.class))).thenReturn(bookDTO);

        BookDTO result = bookService.updateBook(bookId, bookCreationDTO);

        assertNotNull(result);
        assertEquals(bookDTO, result);
        verify(bookRepository, times(1)).saveAndFlush(any(Book.class));
    }

    @Test
    void findAllBooks_shouldReturnListOfBooks_whenSuccessful() {
        when(bookRepository.findAll()).thenReturn(List.of(book));
        when(bookMapper.toListDto(List.of(book))).thenReturn(List.of(bookDTO));

        List<BookDTO> expectedResult = bookService.findAllBooks();

        assertNotNull(expectedResult);
        assertEquals(List.of(bookDTO), expectedResult);
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void deleteBook_shouldThrownException_whenBookIsNotFound() {
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> bookService.deleteBook(bookId));

        assertEquals("Book doesn't exist", exception.getMessage());
        verify(bookRepository, times(1)).findById(bookId);
        verify(bookRepository, never()).delete(any(Book.class));
    }

    @Test
    void deleteBook_shouldBeDeleted_whenBookExists() {
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        bookService.deleteBook(bookId);

        verify(bookRepository, times(1)).deleteById(bookId);
    }

}
