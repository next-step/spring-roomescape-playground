package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;

public class DatabaseProductionTest {

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
