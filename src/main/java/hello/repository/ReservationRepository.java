package hello.repository;

import hello.controller.dto.CreateReservationDto;
import hello.domain.Reservation;
import hello.repository.dto.ReservationDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Objects;

@Repository
public class ReservationRepository {

    private final JdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;

    public ReservationRepository(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    private final RowMapper<Reservation> reservationRowMapper = (rs, rn) -> new Reservation(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getDate("date").toLocalDate(),
            rs.getTime("time").toLocalTime()
    );

    public List<ReservationDto> findAllReservations() {
        String sql = "select id, name, date, time from reservation";
        List<Reservation> reservations = template.query(sql, reservationRowMapper);

        return reservations.stream()
                .map(this::convertToDto)
                .toList();
    }

    public ReservationDto findById(Long id) {
        String sql = "select id, name, date, time from reservation where id = ?";

        Reservation reservation = template.queryForObject(sql, reservationRowMapper, id);
        return convertToDto(reservation);

    }

    public Long save(CreateReservationDto dto) {

        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("name", dto.getName())
                .addValue("date", dto.getDate())
                .addValue("time", dto.getTime());

        Number key = jdbcInsert.executeAndReturnKey(param);

        return Objects.requireNonNull(key).longValue();
    }

    public void delete(Long id) {
        String sql = "delete from reservation where id = ?";
        template.update(sql, id);
    }

    private ReservationDto convertToDto(Reservation reservation) {
        return new ReservationDto(reservation.getId(),
                reservation.getName(), reservation.getDate(), reservation.getTime());
    }
}
