package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationDTO;
import roomescape.dto.ResponseReservationDTO;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ReservationRepository {
    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAll() {
        String sql = "SELECT id, name, date, time FROM reservation";

        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> {
                    Reservation reservation = new Reservation(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getString("date"),
                            resultSet.getString("time")
                    );
                    return reservation;
                });
    }

    public ResponseReservationDTO makeReservation(ReservationDTO reservationDTO) {
        String sql = "INSERT INTO reservation (name, date, time) values (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql,
                    new String[]{"id"});
            preparedStatement.setString(1, reservationDTO.name());
            preparedStatement.setString(2, reservationDTO.date());
            preparedStatement.setString(3, reservationDTO.time());
            return preparedStatement;
        }, keyHolder);
        return new ResponseReservationDTO(keyHolder.getKey().longValue(),
                reservationDTO.name(),
                reservationDTO.date(),
                reservationDTO.time()
        );
    }

    private boolean idUserNotExist(Long id){
        String sql = "SELECT * FROM reservation WHERE id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> 0, id).isEmpty();
    }

    public void deleteById(Long id) {
        if(idUserNotExist(id)){
            throw new IllegalArgumentException("아이디가 존재하지 않습니다!");
        }

        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
