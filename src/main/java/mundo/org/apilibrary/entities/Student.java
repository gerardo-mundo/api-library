package mundo.org.apilibrary.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import mundo.org.apilibrary.classes.User;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@OnDelete(action = OnDeleteAction.CASCADE)
@Getter @Setter
@NoArgsConstructor
public class Student extends User {
    @NotNull
    @Column(unique = true)
    private String enrollmentId;
}
