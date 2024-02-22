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
		List<Reservation> reservations = reservationRepository.findAll();
		return reservations.stream()
				.map(reservation -> new QueryReservationResponse(
						reservation.id(),
						reservation.name(),
						reservation.date(),
						reservation.time()))
				.collect(Collectors.toList());
	}

	public AddReservationResponse addReservation(
			AddReservationRequest reservationRequest) {
		Long newId = reservationRepository.generateId();

		Reservation newReservation = new Reservation(
				newId,
				reservationRequest.name(),
				reservationRequest.date(),
				reservationRequest.time()
		);

		reservationRepository.save(newReservation);

		return new AddReservationResponse(
				newId,
				newReservation.name(),
				newReservation.date(),
				newReservation.time());
	}

	public void deleteReservation(Long id) {
		reservationRepository.deleteById(id);
	}
}