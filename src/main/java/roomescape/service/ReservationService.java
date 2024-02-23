package roomescape.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequestDTO.AddReservationRequest;
import roomescape.dto.ReservationResponseDTO.AddReservationResponse;
import roomescape.dto.ReservationResponseDTO.QueryReservationResponse;
import roomescape.repository.ReservationRepository;

@Service
@RequiredArgsConstructor
public class ReservationService {
	private final ReservationRepository reservationRepository;

	public List<QueryReservationResponse> getReservations() {
		return reservationRepository.findAll().stream()
				.map(reservation -> new QueryReservationResponse(
						reservation.id(),
						reservation.name(),
						reservation.date(),
						reservation.time()))
				.collect(Collectors.toList());
	}

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

	public void deleteReservation(Long id) {
		reservationRepository.deleteById(id);
	}
}