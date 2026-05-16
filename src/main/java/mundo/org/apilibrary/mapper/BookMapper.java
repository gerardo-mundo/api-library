package mundo.org.apilibrary.mapper;

import mundo.org.apilibrary.books.BookCreationDTO;
import mundo.org.apilibrary.entities.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Book toEntity(BookCreationDTO dto);

    // BookCreationDTO toDto(Book entity);
}
