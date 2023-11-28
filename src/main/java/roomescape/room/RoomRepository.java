package roomescape.room;

import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.common.NotFoundReservationException;
import roomescape.time.Time;

@Repository
public class RoomRepository {

	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcInsert simpleJdbcInsert;

	public RoomRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
				.withTableName("reservation")
				.usingGeneratedKeyColumns("id");
	}
	public Long save(Room room) {
		SqlParameterSource parameters = new BeanPropertySqlParameterSource(room);
		return simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
	}

	public Long saveV2(Room room) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("name", room.getName());
		parameters.put("date", room.getDate().toString());
		parameters.put("time_id", room.getTime().getId());
		return simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
	}

	public Room findById(Long id) {
		try {

			return jdbcTemplate.queryForObject("""
					SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.time as time_value
						FROM reservation as r INNER JOIN time as t ON r.time_id = t.id
						WHERE r.id = ?
					""", (rs, rowNum) -> {
				Time time = new Time(rs.getTime("time_value").toLocalTime());
				time.setId(rs.getLong("time_id"));

				return new Room(rs.getLong("reservation_id"), rs.getString("name"), rs.getDate("date").toLocalDate(), time);
			}, id);
		} catch (EmptyResultDataAccessException e) {
			throw new NotFoundReservationException("해당하는 예약이 없습니다.");
		}
	}

	public boolean existsById(Long id) {
		return jdbcTemplate.queryForObject("select exists(select * from reservation where id = ? limit 1)", Boolean.class, id);
	}

	public List<Room> findAll() {
		return jdbcTemplate.query(
        """
        select r.id as rservation_id, r.name, r.date, t.id as time_id, t.time as time_value
            FROM reservation as r INNER JOIN time as t ON r.time_id = t.id
            """, (rs, rowNum) -> {

			Time time = new Time(rs.getTime("time_value").toLocalTime());
			time.setId(rs.getLong("time_id"));

			return new Room(rs.getLong("id"), rs.getString("name"), rs.getDate("date").toLocalDate(),
					time);
		});
	}

	public void deleteById(Long id) {
		if(!existsById(id)) {
			throw new DomainEmptyFieldException("해당하는 예약이 없습니다.");
		}

		jdbcTemplate.update("delete from reservation where id = ?", id);
	}
}
