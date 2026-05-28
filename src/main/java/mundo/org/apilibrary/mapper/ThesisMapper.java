package mundo.org.apilibrary.mapper;

import mundo.org.apilibrary.DTO.thesis.ThesisCreationDTO;
import mundo.org.apilibrary.DTO.thesis.ThesisDTO;
import mundo.org.apilibrary.entities.Thesis;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ThesisMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created_at", ignore = true)
    @Mapping(target = "updated_at", ignore = true)
    Thesis toEntity(ThesisCreationDTO thesisDTO);

    ThesisDTO toDto(Thesis thesis);

    List<ThesisDTO> toListDto(List<Thesis> thesis);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created_at", ignore = true)
    @Mapping(target = "updated_at", ignore = true)
    void updateEntityFromDto(ThesisDTO dto, @MappingTarget Thesis entity);
}
