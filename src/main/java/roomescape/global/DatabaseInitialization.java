package roomescape.global;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitialization {
    private final JdbcTemplate jdbcTemplate;

    public DatabaseInitialization(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void initializeTables() {
        initializeReservationTable();
    }

    private void initializeReservationTable() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS reservations CASCADE");

        jdbcTemplate.execute("""
                CREATE TABLE reservations (
                	id INT AUTO_INCREMENT PRIMARY KEY,
                	name VARCHAR(32),
                	time TIMESTAMP)
                """);

        jdbcTemplate.execute("CREATE INDEX by_time ON reservations (time)");
    }
}
