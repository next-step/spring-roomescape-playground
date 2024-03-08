package roomescape.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import roomescape.dto.time.TimeResponseDTO.AddTimeResponse;
import roomescape.dto.time.TimeResponseDTO.QueryTimeResponse;

@Profile("jdbc")
@RequiredArgsConstructor
public class JdbcTimeService implements TimeService {
	private final JdbcTemplate jdbcTemplate;

	@Override
	public List<QueryTimeResponse> getAllTimes() {
		String sql = "SELECT t.id, t.time_value FROM time t";
		return jdbcTemplate.query(sql, (rs, rowNum) -> new QueryTimeResponse(
				rs.getLong("id"),
				rs.getString("time_value")
		));
	}

	@Override
	public AddTimeResponse addTime(String time) {
		String sql = "INSERT INTO TIME (TIME_VALUE) VALUES (?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(con -> {
			var ps = con.prepareStatement(sql, new String[]{"id"});
			ps.setString(1, time);
			return ps;
		}, keyHolder);

		return new AddTimeResponse(
				keyHolder.getKey().longValue(),
				time
		);
	}

	@Override
	public void deleteTime(Long id) {
		String sql = "DELETE FROM time WHERE id = ?";
		jdbcTemplate.update(sql, id);
	}
}
