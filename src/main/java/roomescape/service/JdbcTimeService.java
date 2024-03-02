package roomescape.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import roomescape.domain.Time;

@Profile("jdbc")
@RequiredArgsConstructor
public class JdbcTimeService implements TimeService {
	private final JdbcTemplate jdbcTemplate;

	@Override
	public List<Time> getAllTimes() {
		String sql = "SELECT id, time FROM time";
		return jdbcTemplate.query(sql, (rs, rowNum) -> new Time(
				rs.getLong("id"),
				rs.getString("time")));
	}

	@Override
	public Time addTime(String time) {
		String sql = "INSERT INTO time (time) VALUES (?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(con -> {
			var ps = con.prepareStatement(sql, new String[]{"id"});
			ps.setString(1, time);
			return ps;
		}, keyHolder);

		return new Time(keyHolder.getKey().longValue(), time);

	}

	@Override
	public void deleteTime(Long id) {
		String sql = "DELETE FROM time WHERE id = ?";
		jdbcTemplate.update(sql, id);
	}
}
