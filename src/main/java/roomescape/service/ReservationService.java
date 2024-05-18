package roomescape.service;

import java.util.List;

import org.springframework.stereotype.Service;

import roomescape.domain.Reservation;
import roomescape.dto.request.ReservationSaveRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.exception.NotFoundReservationException;
import roomescape.repository.Repository;

@Service
public class ReservationService {

	private final Repository repository;

	public ReservationService(Repository repository) {
		this.repository = repository;
	}

	public ReservationResponse saveReservation(ReservationSaveRequest request) {
		Reservation reservation = new Reservation(request.name(), request.date(), request.time());
		repository.save(reservation);
		return ReservationResponse.from(reservation);
	}

	public List<ReservationResponse> getReservations() {
		return repository.findAll().stream().map(ReservationResponse::from).toList();
	}

	public void deleteReservation(Long id) {
		if (repository.findById(id) == null) {
			throw new NotFoundReservationException("존재하지 않는 예약정보 입니다.");
		}
		repository.delete(id);
	}
}
