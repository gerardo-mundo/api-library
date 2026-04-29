package mundo.org.apilibrary.classes;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public abstract class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;
    private boolean available = false;

    @CreationTimestamp(source = SourceType.VM)
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp(source = SourceType.VM)
    private LocalDateTime updatedAt;
}
