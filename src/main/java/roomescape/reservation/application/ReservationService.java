package roomescape.reservation.application;

import java.util.List;

import org.springframework.stereotype.Service;

import roomescape.reservation.domain.Reservation;
import roomescape.reservation.persistence.ReservationRepository;
import roomescape.reservation.presentation.dto.request.ReservationSaveRequest;
import roomescape.reservation.presentation.dto.response.ReservationResponse;
import roomescape.reservation.presentation.exception.NotFoundReservationException;

@Service
public class ReservationService {

	private final ReservationRepository reservationRepository;

	public ReservationService(ReservationRepository reservationRepository) {
		this.reservationRepository = reservationRepository;
	}

	public List<ReservationResponse> getReservations() {
		return reservationRepository.findAll().stream().map(ReservationResponse::from).toList();
	}

	public ReservationResponse saveReservation(ReservationSaveRequest request) {
		Reservation reservation = reservationRepository.save(
			new Reservation(request.name(), request.date(), request.time()));
		return ReservationResponse.from(reservation);
	}

	public void deleteReservation(Long id) {
		if (reservationRepository.findById(id) == null) {
			throw new NotFoundReservationException("존재하지 않는 예약정보 입니다.");
		}
		reservationRepository.delete(id);
	}
}
