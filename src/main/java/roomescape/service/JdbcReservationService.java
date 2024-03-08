package roomescape.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import roomescape.dto.reservation.ReservationRequestDTO.AddReservationRequest;
import roomescape.dto.reservation.ReservationResponseDTO.AddReservationResponse;
import roomescape.dto.reservation.ReservationResponseDTO.QueryReservationResponse;

@Profile("jdbc")
@RequiredArgsConstructor
public class JdbcReservationService implements ReservationService {
	private final JdbcTemplate jdbcTemplate;

	@Override
	public List<QueryReservationResponse> getReservations() {
		String sql = "SELECT r.id as reservation_id, r.name, r.date, t.id, t.time_value FROM reservation as r inner join time as t on r.time_id = t.id;";

		return jdbcTemplate.query(sql, (rs, rowNum) -> new QueryReservationResponse(
				rs.getLong("id"),
				rs.getString("name"),
				rs.getString("date"),
				rs.getString("time_value")
		));
	}

	@Override
	public AddReservationResponse addReservation(AddReservationRequest reservationRequest) {

		String findTimeIdSql = "SELECT id FROM time WHERE time_value = ?";
		Long timeId = jdbcTemplate.queryForObject(findTimeIdSql, new Object[]{reservationRequest.time_value()},
				(rs, rowNum) -> rs.getLong("id"));

		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(con -> {
			var ps = con.prepareStatement(findTimeIdSql, new String[]{"id"});
			ps.setString(1, reservationRequest.name());
			ps.setString(2, reservationRequest.date());
			ps.setLong(3, timeId);
			return ps;
		}, keyHolder);

		Long id = keyHolder.getKey().longValue();
		return new AddReservationResponse(
				id,
				reservationRequest.name(),
				reservationRequest.date(),
				reservationRequest.time_value()
		);
	}

	@Override
	public void deleteReservation(Long id) {
		String sql = "DELETE FROM reservation WHERE id = ?";
		jdbcTemplate.update(sql, id);
	}
}
