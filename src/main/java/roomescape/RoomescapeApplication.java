package roomescape;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class RoomescapeApplication implements CommandLineRunner {
    public static void main(String[] args)
    {
        SpringApplication.run(RoomescapeApplication.class, args);
    }

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... strings) throws Exception
    {
        jdbcTemplate.execute("DROP TABLE reservation IF EXISTS"); // DB 초기화
        jdbcTemplate.execute("CREATE TABLE reservation (id BIGINT AUTO_INCREMENT, name VARCHAR(255), date VARCHAR(255), time VARCHAR(255))");
    }
}
