package mundo.org.apilibrary.classes;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mundo.org.apilibrary.entities.Employee;
import mundo.org.apilibrary.entities.Student;
import mundo.org.apilibrary.enums.Role;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.SourceType;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@Setter @Getter
@NoArgsConstructor
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "role",
        visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Student.class, name = "ROLE_STUDENT"),
        @JsonSubTypes.Type(value = Employee.class, names = {"ROLE_ADMIN", "ROLE_LIBRARIAN", "ROLE_PROFESSOR", "ROLE_ADMINISTRATIVE"})
})
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotNull
    private String name;
    @Email
    @NotNull
    @Column(unique = true)
    private String email;
    @NotNull
    private String password;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;
    private boolean isActive = true;
    @CreationTimestamp(source = SourceType.VM)
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @CurrentTimestamp(source = SourceType.VM)
    private LocalDateTime lastLogin;
}
