package mundo.org.apilibrary.services;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

import mundo.org.apilibrary.DTO.books.BookCreationDTO;
import mundo.org.apilibrary.DTO.books.BookDTO;
import mundo.org.apilibrary.entities.Book;
import mundo.org.apilibrary.interfaces.SpecificationFilter;
import mundo.org.apilibrary.mapper.BookMapper;
import mundo.org.apilibrary.repository.BookRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Map;
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
    @Mock
    SpecificationFilter<Book> specificationFilter;
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
                "Spring Boot in Action",
                "Author",
                "Publisher",
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

    @Test
    void findAllBooks_WithFilters_ShouldReturnPaginatedDTOs() {
        Map<String, String> filters = Map.of("title", "Spring Boot");
        Pageable pageable = PageRequest.of(0, 10);
        List<String> expectedAllowedTerms = List.of("title", "author", "publisher");

        book.setId(UUID.randomUUID());
        book.setTitle("Spring Boot in Action");
        Page<Book> bookPage = new PageImpl<>(List.of(book));

        @SuppressWarnings("unchecked")
        Specification<Book> mockSpec = mock(Specification.class);

        when(specificationFilter.buildSpecification(eq(filters), eq(expectedAllowedTerms))).thenReturn(mockSpec);
        when(bookRepository.findAll(eq(mockSpec), eq(pageable))).thenReturn(bookPage);
        when(bookMapper.toDto(any(Book.class))).thenReturn(bookDTO);

        Page<BookDTO> result = bookService.findAllPageableBooks(filters, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("Spring Boot in Action", result.getContent().get(0).title());

        verify(specificationFilter, times(1)).buildSpecification(filters, expectedAllowedTerms);
        verify(bookRepository, times(1)).findAll(mockSpec, pageable);
    }

}
