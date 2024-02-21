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
        jdbcTemplate.execute("DROP TABLE reservation IF EXISTS"); // query문 실행으로 reservation 테이블 존재 시 삭제 후 추가
        jdbcTemplate.execute("CREATE TABLE reservation (id BIGINT AUTO_INCREMENT, name VARCHAR(255), date VARCHAR(255), time VARCHAR(255))");
    }
}
