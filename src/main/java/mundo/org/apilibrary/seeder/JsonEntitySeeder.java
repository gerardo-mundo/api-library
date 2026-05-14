package mundo.org.apilibrary.seeder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import mundo.org.apilibrary.seeder.interfaces.EntitySeeder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@RequiredArgsConstructor
public abstract class JsonEntitySeeder<T> implements EntitySeeder {

    private static final int BATCH_SIZE = 500;
    private final JpaRepository<T, ?> repository;
    private final ObjectMapper objectMapper;
    private final String jsonFilePath;
    private final TypeReference<List<T>> typeReference;

    @Override
    public void seed() throws Exception {
        if (repository.count() > 0) {
            System.out.println("Skipping " + jsonFilePath + " — already seeded.");
            return;
        }

        ClassPathResource resource = new ClassPathResource(jsonFilePath);
        List<T> entities = objectMapper.readValue(resource.getInputStream(), typeReference);

        for (int i = 0; i < entities.size(); i += BATCH_SIZE) {
            List<T> batch = entities.subList(i, Math.min(i + BATCH_SIZE, entities.size()));
            repository.saveAll(batch);
        }

        System.out.printf("Seeded %d records from %s%n", entities.size(), jsonFilePath);
    }
}
