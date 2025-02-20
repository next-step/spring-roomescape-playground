package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;

public class DatabaseProductionTest {
    // 왜 성공하는지 이해가 안갑니다.. 운영 디비의 url인데 서버를 띄우지 않았지만 성공..
    @Test
    void testGetProductionConnection() {
        String url = "jdbc:h2:mem:database";
        String username = "sa";
        String password = "";
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            assertThat(connection).isNotNull();
            assertThat(connection.getCatalog()).isEqualTo("DATABASE");
            System.out.println("connection = " + connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
