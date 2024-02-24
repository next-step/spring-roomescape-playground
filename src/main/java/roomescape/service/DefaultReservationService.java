package roomescape.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequestDTO.AddReservationRequest;
import roomescape.dto.ReservationResponseDTO.AddReservationResponse;
import roomescape.dto.ReservationResponseDTO.QueryReservationResponse;
import roomescape.repository.ReservationRepository;

@Profile("default")
@RequiredArgsConstructor
public class DefaultReservationService implements ReservationService {
	private final ReservationRepository reservationRepository;

	@Override
	public List<QueryReservationResponse> getReservations() {
		return reservationRepository.findAll().stream()
				.map(reservation -> new QueryReservationResponse(
						reservation.id(),
						reservation.name(),
						reservation.date(),
						reservation.time()))
				.collect(Collectors.toList());
	}

	@Override
	public AddReservationResponse addReservation(
			AddReservationRequest reservationRequest) {

		Reservation newReservation = new Reservation(
				null,
				reservationRequest.name(),
				reservationRequest.date(),
				reservationRequest.time()
		);

		Long savedReservationId = reservationRepository.save(newReservation);

		return new AddReservationResponse(
				savedReservationId,
				newReservation.name(),
				newReservation.date(),
				newReservation.time());
	}

	@Override
	public void deleteReservation(Long id) {
		reservationRepository.deleteById(id);
	}
}