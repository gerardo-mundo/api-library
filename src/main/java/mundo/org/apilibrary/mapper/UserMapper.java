package mundo.org.apilibrary.mapper;

import mundo.org.apilibrary.DTO.users.UserCreationDTO;
import mundo.org.apilibrary.DTO.users.UserDTO;
import mundo.org.apilibrary.DTO.users.UserUpdateDTO;
import mundo.org.apilibrary.classes.User;

import mundo.org.apilibrary.entities.Employee;
import mundo.org.apilibrary.entities.Student;
import mundo.org.apilibrary.enums.Role;
import org.mapstruct.*;

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
    @Mapping(target = "password", ignore = true)
    void updateEntity(UserUpdateDTO dto, @MappingTarget User user);

    default UserDTO toDto(User user) {
        if (user == null) return null;
        String employeeKey = null;
        String enrollmentId = null;

        if (user instanceof Student student) {
            enrollmentId = student.getEnrollmentId();
        } else if (user instanceof Employee employee) {
            employeeKey = employee.getEmployeeKey();
        }
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.isActive(),
                user.getLastLogin(),
                enrollmentId,
                employeeKey
        );
    }

    List<UserDTO> toListDto(List<User> users);

    @ObjectFactory
    default User createUserEntity(UserCreationDTO dto) {
        if (dto.role() == Role.ROLE_STUDENT) {
            return new Student();
        } else {
            return new Employee();
        }
    }

    @AfterMapping
    default void mapSubclassFields(UserCreationDTO dto, @MappingTarget User user) {
        if (user instanceof Employee employee) {
            employee.setEmployeeKey(dto.employeeKey());
        } else if (user instanceof Student student) {
            student.setEnrollmentId(dto.enrollmentId());
        }
    }
}
