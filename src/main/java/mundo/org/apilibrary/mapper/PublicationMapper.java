package mundo.org.apilibrary.mapper;

import mundo.org.apilibrary.DTO.publications.PublicationCreationDTO;
import mundo.org.apilibrary.DTO.publications.PublicationDTO;
import mundo.org.apilibrary.classes.Publication;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

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

}
