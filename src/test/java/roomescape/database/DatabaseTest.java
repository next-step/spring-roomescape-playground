package roomescape.database;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class DatabaseTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void 데이터베이스에_연결할_수_있다() throws SQLException {
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            assertThat(connection.isValid(1)).isTrue();
        }
    }

    @Test
    void 데이터베이스_이름을_확인한다() throws SQLException {
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            assertThat(connection.getCatalog()).isEqualTo("DATABASE");
        }
    }

    @Test
    void reservations_테이블이_생성되어_있다() throws SQLException {
        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             ResultSet tables = connection.getMetaData()
                     .getTables(null, null, "RESERVATIONS", new String[]{"TABLE"})) {
            assertThat(tables.next()).isTrue();
        }
    }
}
