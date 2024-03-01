package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationDTO;
import roomescape.dto.ReservationResponseDTO;
import roomescape.rowMapper.ReservationRowMapper;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ReservationRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insert;

    public ReservationRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.insert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    public List<Reservation> findAll() {
        String sql = "SELECT id, name, date, time FROM reservation";

        return jdbcTemplate.query(
                sql,
                new ReservationRowMapper());
    }

    public ReservationResponseDTO makeReservation(ReservationDTO reservationDTO) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(reservationDTO);

        Long id = insert.executeAndReturnKey(params).longValue();

        return new ReservationResponseDTO(id,
                reservationDTO.name(),
                reservationDTO.date(),
                reservationDTO.time()
        );
    }

    private boolean idUserNotExist(Long id) {
        String sql = "SELECT * FROM reservation WHERE id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> 0, id).isEmpty();
    }

    public void deleteById(Long id) {
        if (idUserNotExist(id)) {
            throw new IllegalArgumentException("아이디가 존재하지 않습니다!");
        }

        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
