package roomescape.service;

import java.util.List;

import org.springframework.stereotype.Service;

import roomescape.domain.Reservation;
import roomescape.dto.request.ReservationSaveRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.exception.NotFoundReservationException;
import roomescape.repository.ReservationRepository;

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
		Reservation reservation = new Reservation(request.name(), request.date(), request.time());
		reservationRepository.save(reservation);
		return ReservationResponse.from(reservation);
	}

	public void deleteReservation(Long id) {
		if (reservationRepository.findById(id) == null) {
			throw new NotFoundReservationException("존재하지 않는 예약정보 입니다.");
		}
		reservationRepository.delete(id);
	}
}
