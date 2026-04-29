package mundo.org.apilibrary.seeder;

import lombok.RequiredArgsConstructor;
import mundo.org.apilibrary.seeder.interfaces.EntitySeeder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Component
@Transactional
@RequiredArgsConstructor
public class SeederRunner implements CommandLineRunner {
    private final List<EntitySeeder> seeders;


    @Override
    public void run(String... args) throws Exception {
        seeders.stream().sorted(Comparator.comparing(EntitySeeder::getOrder))
                .forEach(seeder -> {
                   try {
                       seeder.seed();
                   } catch (Exception e) {
                       throw new RuntimeException("Error while seeding: " + seeder.getClass().getSimpleName(), e);
                   }
                });
    }
}
