package roomescape;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class RoomescapeApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(RoomescapeApplication.class, args);
    }

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("RUN");
        jdbcTemplate.execute("DROP TABLE reservation IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE reservation(id SERIAL, name VARCHAR(255), date VARCHAR(255), time VARCHAR(255))");
    }
}