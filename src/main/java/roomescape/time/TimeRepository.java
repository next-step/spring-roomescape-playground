package roomescape.time;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.common.NotFoundTimeException;

@Repository
public class TimeRepository {

	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert simpleJdbcInsert;

	public TimeRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("time").usingGeneratedKeyColumns("id");
	}

	public Time save(Time time) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("time", time.getTime().toString());

		long savedId = simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
		time.setId(savedId);

		return time;
	}

	public List<Time> findAll() {
		return jdbcTemplate.query("select * from time", (rs, rowNum) -> {
			Time time = new Time(rs.getTime("time").toLocalTime());
			time.setId(rs.getLong("id"));

			return time;
		});
	}

	public void deleteById(Long id) {
		findById(id);
		jdbcTemplate.update("delete from time where id = ?", id);
	}

	private Time findById(Long id) {
		try {
			return jdbcTemplate.queryForObject("select * from time where id = ?", (rs, rowNum) -> {
				Time time = new Time(rs.getTime("time").toLocalTime());
				time.setId(rs.getLong("id"));

				return time;
			}, id);
		} catch (EmptyResultDataAccessException e) {
			throw new NotFoundTimeException("해당하는 시간이 없습니다.");
		}

	}
}
