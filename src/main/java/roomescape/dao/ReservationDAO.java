package roomescape.dao;

import jakarta.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;
import roomescape.domain.Reservation;

@Component
public class ReservationDAO {

    private static final String URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "";

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            throw new RuntimeException("DB 연결 실패: " + e.getMessage(), e);
        }
    }


    @PostConstruct
    public void createTable() {

        final var query = "CREATE TABLE IF NOT EXISTS RESERVATION (" +
                "ID BIGINT PRIMARY KEY, " +
                "NAME VARCHAR(50), " +
                "DATE VARCHAR(20), " +
                "TIME VARCHAR(20))";

        try (
                final var connection = getConnection();
        ) {
            connection.createStatement().execute(query);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addReservation(final Reservation reservation) {
        final var query = "INSERT INTO RESERVATION VALUES(?, ?, ?, ?)";
        try (
                final var connection = getConnection();
                final var preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setLong(1, reservation.getId());
            preparedStatement.setString(2, reservation.getName());
            preparedStatement.setString(3, reservation.getDate().toString());
            preparedStatement.setString(4, reservation.getTime().toString());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Reservation findReservation(final int id) {
        final var query = "SELECT * FROM RESERVATION WHERE ID = ?";
        try (
                final var connection = getConnection();
                final var preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setLong(1, id);

            final var resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                LocalDate date = LocalDate.parse(resultSet.getString("DATE"), DateTimeFormatter.ISO_DATE);
                LocalTime time = LocalTime.parse(resultSet.getString("TIME"), DateTimeFormatter.ISO_TIME);

                System.out.println(date + " " + time);
                return new Reservation(
                        resultSet.getInt("ID"),
                        resultSet.getString("NAME"),
                        date, time
                );
            }


        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public void deleteReservation(final int id) {
        final var query = "DELETE FROM RESERVATION WHERE ID = ?";
        try (
                final var connection = getConnection();
                final var preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void resetTable() {
        try (var connection = getConnection()) {
            var stmt = connection.createStatement();
            stmt.execute("DROP TABLE IF EXISTS RESERVATION");
            stmt.execute("CREATE TABLE RESERVATION (" +
                    "ID BIGINT PRIMARY KEY, " +
                    "NAME VARCHAR(50), " +
                    "DATE VARCHAR(20), " +
                    "TIME VARCHAR(20))");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
