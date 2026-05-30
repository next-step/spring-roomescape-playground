package roomescape.reservation.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import roomescape.exception.customexception.ReservationConflictException;
import roomescape.exception.customexception.ReservationNotFoundException;
import roomescape.exception.customexception.ReservationPastException;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.dto.ReservationResponse;
import roomescape.reservation.repository.ReservationRepository;
import roomescape.time.domain.Time;
import roomescape.time.repository.TimeRepository;
import roomescape.time.service.TimeService;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;

    public ReservationService(ReservationRepository reservationRepository, TimeRepository timeRepository) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
    }

    public ReservationResponse createReservation(ReservationRequest reservationRequest) {
        Reservation reservation = toReservation(reservationRequest);
        try {
            Reservation createdReservation = reservationRepository.saveReservation(reservation);
            return toReservationResponse(createdReservation);
        } catch (DataIntegrityViolationException e) {
            throw new ReservationConflictException();
        }
    }

    public List<ReservationResponse> readAllReservations() {
        List<Reservation> reservations = reservationRepository.findAllReservations();
        return reservations.stream()
                .map(ReservationService::toReservationResponse)
                .toList();
    }

    public void deleteReservation(Long id) {
        int affectedRows = reservationRepository.deleteReservationById(id);
        if (affectedRows == 0) {
            throw new ReservationNotFoundException();
        }
    }

    public Reservation toReservation(ReservationRequest reservationRequest) {
        Time time = timeRepository.findTimeById(reservationRequest.time());
        validateNotPast(reservationRequest.date(), time.getTime());
        return new Reservation(reservationRequest.name(), reservationRequest.date(), time);
    }

    private void validateNotPast(LocalDate date, LocalTime time) {
        LocalDateTime dateTime = LocalDateTime.of(date, time);
        if (dateTime.isBefore(LocalDateTime.now())) {
            throw new ReservationPastException();
        }
    }

    public static ReservationResponse toReservationResponse(Reservation reservation) {
        return new ReservationResponse(reservation.getId(), reservation.getName(), reservation.getDate().toString(),
                TimeService.toTimeResponse(reservation.getTime()));
    }
}
