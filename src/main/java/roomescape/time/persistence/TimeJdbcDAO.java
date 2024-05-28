package roomescape.time.persistence;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import roomescape.time.domain.Time;

@Repository
public class TimeJdbcDAO implements TimeDAO {

	private final JdbcTemplate jdbcTemplate;
	private final RowMapper<Time> TimeRowMapper = (resultSet, rowNum) ->
		new Time(
			resultSet.getLong("id"),
			resultSet.getString("time")
		);

	public TimeJdbcDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Time> findAll() {
		String sql = "select id, time from time";
		return List.copyOf(jdbcTemplate.query(sql, TimeRowMapper));
	}

	@Override
	public Time save(Time time) {
		String sql = "insert into time (time) values (?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id"});
			ps.setString(1, time.getTime());
			return ps;
		}, keyHolder);
		return new Time(keyHolder.getKey().longValue(), time.getTime());
	}

	@Override
	public Time findById(Long id) {
		String sql = "select id, time from time where id = ?";
		try {
			return jdbcTemplate.queryForObject(sql, TimeRowMapper, id);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public void delete(Long id) {
		String sql = "delete from time where id = ?";
		jdbcTemplate.update(sql, id);
	}
}
