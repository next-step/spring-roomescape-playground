package roomescape.reservation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.common.exception.NoSuchReservationTimeException;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.dto.ReservationResponse;
import roomescape.common.exception.NoSuchElementToDeleteException;
import roomescape.reservation.model.Reservation;
import roomescape.reservation.repository.ReservationRepository;
import roomescape.time.model.Time;
import roomescape.time.repository.TimeRepository;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, TimeRepository timeRepository) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
    }

    public List<ReservationResponse> getReservations() {
        return reservationRepository.findAllReservations().stream().map(ReservationResponse::from).toList();
    }

    public ReservationResponse addReservation(ReservationRequest request) {
        Reservation reservation = request.toEntity();

        Time time = timeRepository.findTimeByValue(request.time)
                .orElseThrow(() -> new NoSuchReservationTimeException("There is no time with value " + request.time));
        reservation.setTime(time);

        Long id = reservationRepository.insertWithKeyHolder(reservation);
        reservation.setId(id);

        return ReservationResponse.from(reservation);
    }

    public void deleteReservation(Long id) {
        Reservation reservation = reservationRepository.findReservationById(id)
                .orElseThrow(() -> new NoSuchElementToDeleteException("There is no reservation with id " + id));

        reservationRepository.delete(reservation);
    }
}