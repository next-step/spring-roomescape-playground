package roomescape;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class RoomescapeApplication implements CommandLineRunner {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(RoomescapeApplication.class, args);
    }

    public void run(String... strings) throws Exception {
        jdbcTemplate.execute("DROP TABLE Times IF EXISTS");
        jdbcTemplate.execute("DROP TABLE Reservations IF EXISTS");
        jdbcTemplate.execute(
                "CREATE TABLE Reservations(id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255) NOT NULL, date DATE NOT NULL)");
        jdbcTemplate.execute(
                "CREATE TABLE Times(time_id INT FOREIGN KEY REFERENCES Reservations(id), time TIME NOT NULL)");
    }
}
