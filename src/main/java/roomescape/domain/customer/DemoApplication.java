package roomescape.domain.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    /**
     * 스프링에서 제공하는 객체.
     * <p>
     * SQL 관계형 데이터베이스 및 JDBC와 쉽게 작업이 가능함.
     * <p>
     * 리소스 획득, 연결 관리, 예외 처리와 같은 작업은 고려하지 않고 쿼리와 응답 처리만 고민할 수 있게 됨.
     */
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        jdbcTemplate.execute("DROP TABLE customers IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE customers(id SERIAL, first_name VARCHAR(255), last_name VARCHAR(255))");
    }
}
