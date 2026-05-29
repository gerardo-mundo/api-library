package mundo.org.apilibrary.seeder.implementations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import mundo.org.apilibrary.classes.Publication;
import mundo.org.apilibrary.repository.PublicationRepository;
import mundo.org.apilibrary.seeder.JsonEntitySeeder;
import org.springframework.stereotype.Component;

@Component
public class PublicationSeeder extends JsonEntitySeeder<Publication> {
    PublicationSeeder(PublicationRepository repository,  ObjectMapper mapper) {
        super(repository, mapper, "seeds/publications.json", new TypeReference<>() {});
    }

    @Override
    public int getOrder() { return 1; }
}
