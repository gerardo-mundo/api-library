package mundo.org.apilibrary.seeder.interfaces;

public interface EntitySeeder {
    void seed() throws Exception;
    int getOrder();
}
