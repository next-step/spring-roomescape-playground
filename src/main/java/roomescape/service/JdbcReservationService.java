package roomescape.service;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import roomescape.dto.ReservationRequestDTO.AddReservationRequest;
import roomescape.dto.ReservationResponseDTO.AddReservationResponse;
import roomescape.dto.ReservationResponseDTO.QueryReservationResponse;

@Service
@RequiredArgsConstructor
public class JdbcReservationService {
	private final JdbcTemplate jdbcTemplate;

	public List<QueryReservationResponse> getReservations() {
		String sql = "SELECT id, name, date, time FROM reservation";
		return jdbcTemplate.query(sql, (rs, rowNum) -> new QueryReservationResponse(
				rs.getLong("id"),
				rs.getString("name"),
				rs.getString("date"),
				rs.getString("time")));
	}

	public AddReservationResponse addReservation(AddReservationRequest reservationRequest) {
		String sql = "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(con -> {
			var ps = con.prepareStatement(sql, new String[]{"id"});
			ps.setString(1, reservationRequest.name());
			ps.setString(2, reservationRequest.date());
			ps.setString(3, reservationRequest.time());
			return ps;
		}, keyHolder);

		Long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
		return new AddReservationResponse(
				id,
				reservationRequest.name(),
				reservationRequest.date(),
				reservationRequest.time());
	}

	public void deleteReservation(Long id) {
		String sql = "DELETE FROM reservation WHERE id = ?";
		jdbcTemplate.update(sql, id);
	}
}
