package mundo.org.apilibrary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ApiLibraryApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiLibraryApplication.class, args);
    }

}
