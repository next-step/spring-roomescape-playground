package roomescape.room;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.common.DomainEmptyFieldException;
import roomescape.common.NotFoundReservationException;
import roomescape.time.Time;

@Repository
public class ReservationDAO {

	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert simpleJdbcInsert;

	public ReservationDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
				.withTableName("reservation")
				.usingGeneratedKeyColumns("id");
	}

	public Long save(Reservation reservation) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("name", reservation.getName());
		parameters.put("date", reservation.getDate().toString());
		parameters.put("time_id", reservation.getTime().getId());
		return simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
	}

	public Reservation findById(Long id) {
		try {
			return jdbcTemplate.queryForObject("""
					SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.time as time_value
						FROM reservation as r INNER JOIN time as t ON r.time_id = t.id
						WHERE r.id = ?
					""", (rs, rowNum) -> {
				Time time = new Time(rs.getLong("time_id"), rs.getTime("time_value").toLocalTime());

				return new Reservation(id, rs.getString("name"), rs.getDate("date").toLocalDate(), time);
			}, id);
		} catch (EmptyResultDataAccessException e) {
			throw new NotFoundReservationException("해당하는 예약이 없습니다.");
		}
	}

	public boolean existsById(Long id) {
		return jdbcTemplate.queryForObject("select exists(select * from reservation where id = ? limit 1)",
				Boolean.class, id);
	}

	public List<Reservation> findAll() {
		return jdbcTemplate.query(
				"""
						select r.id as rservation_id, r.name, r.date, t.id as time_id, t.time as time_value
						    FROM reservation as r INNER JOIN time as t ON r.time_id = t.id
						    """, (rs, rowNum) -> {

					Time time = new Time(rs.getLong("time_id"), rs.getTime("time_value").toLocalTime());

					return new Reservation(rs.getLong("id"), rs.getString("name"), rs.getDate("date").toLocalDate(),
							time);
				});
	}

	public void deleteById(Long id) {
		if (!existsById(id)) {
			throw new DomainEmptyFieldException("해당하는 예약이 없습니다.");
		}

		jdbcTemplate.update("delete from reservation where id = ?", id);
	}
}
