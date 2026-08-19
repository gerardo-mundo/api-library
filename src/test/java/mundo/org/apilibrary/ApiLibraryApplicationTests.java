package mundo.org.apilibrary;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(properties = "spring.flyway.enabled=true")
@ActiveProfiles("dev")
class ApiLibraryApplicationTests {

    @Test
    void contextLoads() {
    }

}
