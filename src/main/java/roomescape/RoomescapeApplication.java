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
        jdbcTemplate.execute("DROP TABLE Reservations IF EXISTS");
        jdbcTemplate.execute("DROP TABLE Times IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE Times(time_id INT AUTO_INCREMENT PRIMARY KEY, time TIME NOT NULL UNIQUE)");
        String[] ReservationTimes = {"08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00",
                "17:00",
                "18:00", "19:00", "20:00", "21:00"};
        for (String t : ReservationTimes) {
            jdbcTemplate.execute("INSERT INTO Times(time) VALUES ('" + t + "')");
        }
        jdbcTemplate.execute(
                "CREATE TABLE Reservations(id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255) NOT NULL, date DATE NOT NULL, time_id INT NOT NULL, FOREIGN KEY (time_id) REFERENCES Times(time_id) ON DELETE CASCADE)");
    }
}
