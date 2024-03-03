package roomescape.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.dto.ReservationDTO;
import roomescape.dto.ReservationResponseDTO;
import roomescape.rowMapper.ReservationRowMapper;
import roomescape.rowMapper.TimeRowMapper;

import javax.sql.DataSource;
import java.util.List;
import java.util.NoSuchElementException;


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
        String sql = """
                SELECT
                r.id as reservation_id,
                        r.name,
                        r.date,
                        t.id as time_id,
                t.time as time_value
                FROM reservation as r inner join time as t on r.time_id = t.id
                """;

        return jdbcTemplate.query(sql,
                new ReservationRowMapper());
    }

    public ReservationResponseDTO makeReservation(ReservationDTO reservationDTO) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(reservationDTO);

        Long id = insert.executeAndReturnKey(params).longValue();

        Time time = jdbcTemplate.queryForObject("Select * From time where id = ?", new TimeRowMapper());

        return new ReservationResponseDTO(id,
                reservationDTO.name(),
                reservationDTO.date(),
                time
        );
    }

    private void idUserNotExist(Long id) {
        String sql = """
                 SELECT
                r.id as reservation_id
                , r.name
                , r.date
                , t.id as time_id
                , t.time as time_value
                FROM reservation as r
                inner join time as t
                on r.time_id = ?
                """;
        try{
            Reservation reservation =
                    jdbcTemplate.queryForObject(sql,
                            new ReservationRowMapper(),
                            id);
        }catch (DataAccessException e){
            throw new NoSuchElementException();
        }
    }

    public void deleteById(Long id) {
        idUserNotExist(id);

        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
