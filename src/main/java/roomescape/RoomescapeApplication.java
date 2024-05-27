package roomescape;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StreamUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Statement;

@SpringBootApplication
public class RoomescapeApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(RoomescapeApplication.class, args);
    }

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... strings) throws Exception {
        jdbcTemplate.execute("DROP TABLE reservation IF EXISTS");

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("schema.sql");
        if (inputStream == null) {
            throw new RuntimeException("schema.sql not exists");
        }

        String sql = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        String[] sqlStatements = sql.split(";");

        for (String statement : sqlStatements) {
            if (statement.trim().length() > 0) {
                jdbcTemplate.execute(statement.trim());
            }
        }
    }

}
