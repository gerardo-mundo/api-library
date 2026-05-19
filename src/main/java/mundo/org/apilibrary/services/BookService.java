package mundo.org.apilibrary.services;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

import mundo.org.apilibrary.DTO.books.BookCreationDTO;
import mundo.org.apilibrary.DTO.books.BookDTO;
import mundo.org.apilibrary.entities.Book;
import mundo.org.apilibrary.mapper.BookMapper;
import mundo.org.apilibrary.repository.BookRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookService(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookMapper = bookMapper;
        this.bookRepository = bookRepository;
    }

    public List<BookDTO> findAllBooks() {
        List<Book> books = bookRepository.findAll();
        return this.bookMapper.toListDto(books);
    }

    public BookDTO findById(UUID id) {
        return this.bookMapper.toDto(bookRepository.findById(id).orElse(null));
    }

    public BookDTO findByIsbn(String isbn) {
        return this.bookMapper.toDto(bookRepository.findByIsbn(isbn).orElse(null));
    }

    @Transactional
    public Book createBook(BookCreationDTO dto) {
        if(bookRepository.existsByIsbn(dto.isbn())) {
            throw new EntityExistsException("Book with ISBN " + dto.isbn() + " already exists");
        }

        var newBook = bookMapper.toEntity(dto);
        return bookRepository.save(newBook);
    }

    @Transactional
    public Book updateBook(UUID id, BookCreationDTO dto) {
        var book = bookRepository.findById(id);

        if(book.isEmpty()) {
            throw new EntityNotFoundException("Book with ISBN " + dto.isbn() + " not found");
        }

        var bookUpdated = bookMapper.toEntity(dto);
        return bookRepository.save(bookUpdated);
    }

    @Transactional
    public void deleteBook(UUID id) {
        var book = bookRepository.findById(id);

        if(book.isEmpty()) {
            throw new EntityNotFoundException("Book doesn't exist");
        }

        bookRepository.deleteById(id);
    }
}
