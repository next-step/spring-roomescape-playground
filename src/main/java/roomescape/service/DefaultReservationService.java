package roomescape.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.dto.reservation.ReservationRequestDTO.AddReservationRequest;
import roomescape.dto.reservation.ReservationResponseDTO.AddReservationResponse;
import roomescape.dto.reservation.ReservationResponseDTO.QueryReservationResponse;
import roomescape.repository.ReservationRepository;
import roomescape.repository.TimeRepository;

@Profile("default")
@RequiredArgsConstructor
public class DefaultReservationService implements ReservationService {
	private final ReservationRepository reservationRepository;
	private final TimeRepository timeRepository;

	@Override
	public List<QueryReservationResponse> getReservations() {
		return reservationRepository.findAll().stream()
				.map(reservation -> new QueryReservationResponse(
						reservation.id(),
						reservation.name(),
						reservation.date(),
						reservation.time().time_value()))
				.collect(Collectors.toList());
	}

	@Override
	public AddReservationResponse addReservation(
			AddReservationRequest reservationRequest) {
		Time time = timeRepository.findById(reservationRequest.time_id());

		Reservation newReservation = new Reservation(
				null,
				reservationRequest.name(),
				reservationRequest.date(),
				time
		);

		Long savedReservationId = reservationRepository.save(newReservation);

		return new AddReservationResponse(
				savedReservationId,
				newReservation.name(),
				newReservation.date(),
				newReservation.time().time_value()
		);
	}

	@Override
	public void deleteReservation(Long id) {
		reservationRepository.deleteById(id);
	}
}