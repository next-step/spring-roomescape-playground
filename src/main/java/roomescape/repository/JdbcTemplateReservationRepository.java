package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.global.exception.BadRequestException;

import java.util.List;

import static roomescape.global.exception.ExceptionMessage.RESERVATION_NOT_EXISTS;

@Repository
public class JdbcTemplateReservationRepository implements ReservationRepository {

    private final SimpleJdbcInsert jdbcInsert;

    private static final RowMapper<Reservation> RESERVATION_ROW_MAPPER = (rs, rowNum) ->
            new Reservation(
                    rs.getLong("id"),
                    rs.getString("customer_name"),
                    rs.getDate("date").toLocalDate(),
                    rs.getTime("time").toLocalTime()
            );

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("RESERVATION")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Reservation save(final Reservation reservation) {
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(reservation);

        long id = jdbcInsert.executeAndReturnKey(parameters).longValue();
        return new Reservation(
                id,
                reservation.getCustomerName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }

    @Override
    public List<Reservation> findAll() {
        return jdbcTemplate.query("SELECT * FROM RESERVATION", RESERVATION_ROW_MAPPER);
    }

    @Override
    public Reservation findById(final long reservationId) {
        Reservation reservation;
        try {
            reservation = jdbcTemplate.queryForObject("SELECT * FROM RESERVATION WHERE id = ?", RESERVATION_ROW_MAPPER, reservationId);
        } catch (RuntimeException runtimeException) {
            throw new BadRequestException(RESERVATION_NOT_EXISTS.getMessage());
        }
        return reservation;
    }

    @Override
    public void deleteById(final long reservationId) {
        jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", reservationId);
    }
}
