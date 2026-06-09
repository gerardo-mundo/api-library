package mundo.org.apilibrary.mapper;

import mundo.org.apilibrary.DTO.users.UserCreationDTO;
import mundo.org.apilibrary.DTO.users.UserDTO;
import mundo.org.apilibrary.classes.User;

import mundo.org.apilibrary.entities.Employee;
import mundo.org.apilibrary.entities.Student;
import mundo.org.apilibrary.enums.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ObjectFactory;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "lastLogin", ignore = true)
    User toEntity(UserCreationDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "lastLogin", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntity(UserCreationDTO dto, @MappingTarget User user);

    UserDTO toDto(User user);

    List<UserDTO> toListDto(List<User> users);

    @ObjectFactory
    default User createUserEntity(UserCreationDTO dto) {
        if (dto.role() == Role.ROLE_STUDENT) {
            return new Student();
        } else {
            return new Employee();
        }
    }
}
