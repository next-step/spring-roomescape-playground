package roomescape;

import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

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
		parameters.put("time", room.getTime().toString());
		return simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
	}

	public Long saveV3(Room room) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(
					"INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)", new String[]{"id"});

			ps.setString(1, room.getName());
			ps.setString(2, room.getDate().toString());
			ps.setString(3, room.getTime().toString());
			return ps;
		}, keyHolder);

		return keyHolder.getKey().longValue();
	}

	public Room findById(Long id) {
		try {
			Room room = jdbcTemplate.queryForObject("select * from reservation where id = ?",
					(rs, rowNum) -> new Room(rs.getString("name"), rs.getDate("date").toLocalDate(),
							rs.getTime("time").toLocalTime()), id);
			room = new Room(id, room.getName(), room.getDate(), room.getTime());
			return room;
		} catch (EmptyResultDataAccessException e) {
			throw new DomainEmptyFieldException("해당하는 예약이 없습니다.");
		}
	}

	public boolean existsById(Long id) {
		return jdbcTemplate.queryForObject("select exists(select * from reservation where id = ? limit 1)", Boolean.class, id);
	}

	public List<Room> findAll() {
		return jdbcTemplate.query(
				"select id, name, date, time from reservation",
				(resultSet, rowNum) -> {
					Room room = new Room(
							resultSet.getString("name"),
							resultSet.getDate("date").toLocalDate(),
							resultSet.getTime("time").toLocalTime()
					);
					Long id = resultSet.getLong("id");
					room = new Room(id, room.getName(), room.getDate(), room.getTime());
					return room;
				});
	}

	public void deleteById(Long id) {
		if(!existsById(id)) {
			throw new DomainEmptyFieldException("해당하는 예약이 없습니다.");
		}

		jdbcTemplate.update("delete from reservation where id = ?", id);
	}
}
