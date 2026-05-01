package mundo.org.apilibrary.seeder.implementations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import mundo.org.apilibrary.entities.Thesis;
import mundo.org.apilibrary.repository.ThesisRepository;
import mundo.org.apilibrary.seeder.JsonEntitySeeder;
import org.springframework.stereotype.Component;

@Component
public class ThesisSeeder extends JsonEntitySeeder<Thesis> {
    ThesisSeeder(ThesisRepository repository, ObjectMapper mapper) {
        super(repository, mapper, "seeds/thesis.json", new TypeReference<>() {});
    }

    @Override
    public int getOrder() {
        return 3;
    }
}
