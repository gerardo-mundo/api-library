package mundo.org.apilibrary.seeder.implementations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import mundo.org.apilibrary.entities.Book;
import mundo.org.apilibrary.repository.BookRepository;
import mundo.org.apilibrary.seeder.JsonEntitySeeder;
import org.springframework.stereotype.Component;

@Component
public class BookSeeder extends JsonEntitySeeder<Book> {

    public BookSeeder(BookRepository repo, ObjectMapper mapper) {
        super(repo, mapper, "seeds/books.json", new TypeReference<>() {});
    }

    @Override
    public int getOrder() { return 2; } // after Users if Books reference them
}
