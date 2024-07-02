package roomescape;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class RoomescapeApplication implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    public RoomescapeApplication(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static void main(String[] args) {
        SpringApplication.run(RoomescapeApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        jdbcTemplate.execute("DROP TABLE IF EXISTS reservation");
        jdbcTemplate.execute("CREATE TABLE reservation (id BIGINT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255), date VARCHAR(255), time VARCHAR(255))");
    }
}
