package mundo.org.apilibrary.mapper;

import mundo.org.apilibrary.DTO.publications.PublicationCreationDTO;
import mundo.org.apilibrary.DTO.publications.PublicationDTO;
import mundo.org.apilibrary.classes.Publication;

import mundo.org.apilibrary.entities.Magazine;
import mundo.org.apilibrary.entities.Paper;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PublicationMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Publication toEntity(PublicationCreationDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDto(PublicationCreationDTO dto, @MappingTarget Publication entity);

    PublicationDTO toDTO(Publication publication);

    List<PublicationDTO> toDTOList(List<Publication> publicationList);

    @ObjectFactory
    default Publication createPublication(PublicationCreationDTO dto) {
        return switch (dto.type()) {
            case PAPER -> new Paper();
            case MAGAZINE -> new Magazine();
            default -> throw new IllegalArgumentException("invalid type: " + dto.type());
        };
    }

    @AfterMapping
    default void fillSubclassFields(PublicationCreationDTO dto, @MappingTarget Publication entity) {
        if (entity instanceof Paper paper) {
            paper.setCategory(dto.category());
        } else if (entity instanceof Magazine magazine) {
            magazine.setVolume(dto.volume());
        }
    }
}
