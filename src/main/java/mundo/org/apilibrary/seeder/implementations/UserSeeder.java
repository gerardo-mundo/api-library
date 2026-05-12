package mundo.org.apilibrary.seeder.implementations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import mundo.org.apilibrary.classes.User;
import mundo.org.apilibrary.repository.UserRepository;
import mundo.org.apilibrary.seeder.JsonEntitySeeder;
import org.springframework.stereotype.Component;

@Component
public class UserSeeder extends JsonEntitySeeder<User> {
    UserSeeder(UserRepository repository, ObjectMapper mapper) {
        super(repository, mapper, "seeds/users.json", new TypeReference<>() {});
    }

    @Override
    public int getOrder() {
        return 4;
    }
}
