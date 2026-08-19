package roomescape;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
class DataSourceTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void 데이터베이스_연결_테스트() throws SQLException {
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            assertThat(connection).isNotNull();
        }
    }

    @Test
    void 데이터베이스_이름_테스트() throws SQLException {
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            assertThat(connection.getCatalog()).isEqualTo("DATABASE");
        }
    }

    @Test
    void 테이블_이름_테스트() throws SQLException {
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            assertThat(connection.getMetaData().getTables(null, null, "RESERVATION", null).next()).isTrue();
        }
    }
}
