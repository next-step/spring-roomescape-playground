package roomescape.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import roomescape.domain.Time;
import roomescape.dto.reservation.ReservationRequestDTO.AddReservationRequest;
import roomescape.dto.reservation.ReservationResponseDTO.AddReservationResponse;
import roomescape.dto.reservation.ReservationResponseDTO.QueryReservationResponse;
import roomescape.repository.TimeRepository;

@Profile("jdbc")
@RequiredArgsConstructor
public class JdbcReservationService implements ReservationService {
	private final JdbcTemplate jdbcTemplate;
	private final TimeRepository timeRepository;

	@Override
	public List<QueryReservationResponse> getReservations() {
		String sql = "SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.time as time_value "
				+ "FROM reservation as r inner join time as t "
				+ "on r.time_id = t.id";

		return jdbcTemplate.query(sql, (rs, rowNum) -> new QueryReservationResponse(
				rs.getLong("id"),
				rs.getString("name"),
				rs.getString("date"),
				rs.getString("time_value")
		));
	}

	@Override
	public AddReservationResponse addReservation(AddReservationRequest reservationRequest) {
		String sql = "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)";
		Time time = timeRepository.findById(reservationRequest.time_id());

		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(con -> {
			var ps = con.prepareStatement(sql, new String[]{"id"});
			ps.setString(1, reservationRequest.name());
			ps.setString(2, reservationRequest.date());
			ps.setString(3, time.time_value());
			return ps;
		}, keyHolder);

		Long id = keyHolder.getKey().longValue();
		return new AddReservationResponse(
				id,
				reservationRequest.name(),
				reservationRequest.date(),
				time.time_value()
		);
	}

	@Override
	public void deleteReservation(Long id) {
		String sql = "DELETE FROM reservation WHERE id = ?";
		jdbcTemplate.update(sql, id);
	}
}
