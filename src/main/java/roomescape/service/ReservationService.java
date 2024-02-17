package roomescape.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.dto.ReservationRequestDTO;
import roomescape.dto.ReservationResponseDTO;
import roomescape.dto.ReservationResponseDTO.QueryReservation;
import roomescape.repository.ReservationRepository;

@Service
@RequiredArgsConstructor
public class ReservationService {
	private final ReservationRepository reservationRepository;

	public List<QueryReservation> getReservations() {
		return reservationRepository.findAll();
	}

	public ReservationResponseDTO.AddReservation addReservation(
			ReservationRequestDTO.AddReservation reservationRequest) {
		Long newId = reservationRepository.generateId();
		ReservationResponseDTO.AddReservation newReservation =
				ReservationResponseDTO.AddReservation.builder()
						.id(newId)
						.name(reservationRequest.getName())
						.date(reservationRequest.getDate())
						.time(reservationRequest.getTime())
						.build();
		ReservationResponseDTO.QueryReservation newQueryReservation =
				ReservationResponseDTO.QueryReservation.builder()
						.id(newId)
						.name(reservationRequest.getName())
						.date(reservationRequest.getDate())
						.time(reservationRequest.getTime())
						.build();

		reservationRepository.save(newQueryReservation);
		return newReservation;
	}

	public void deleteReservation(Long id) {
		reservationRepository.deleteById(id);
	}
}