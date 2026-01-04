package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.dto.ReservationRequest;
import roomescape.exception.ErrorMessage;
import roomescape.exception.InvalidDataException;
import roomescape.exception.InvalidReservationException;
import roomescape.exception.NotFoundDataException;
import roomescape.repository.ReservationRepository;
import roomescape.repository.TimeRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;

    public ReservationService(ReservationRepository reservationRepository, TimeRepository timeRepository) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public Reservation save(ReservationRequest request) {
        if (request.date().isBefore(LocalDate.now())) {
            throw new InvalidDataException(ErrorMessage.INVALID_DATE);
        }

        Time time = timeRepository.findById(request.time())
                                  .orElseThrow(() -> new NotFoundDataException(ErrorMessage.TIME_NOT_FOUND));

        if (reservationRepository.existsDateAndTime(request.date(), time)) {
            throw new InvalidReservationException(ErrorMessage.RESERVATION_EXISTS);
        }

        Reservation newReservation = new Reservation(null, request.name(), request.date(), time);
        return reservationRepository.save(newReservation);
    }

    public void deleteById(Long id) {
        boolean deleted = reservationRepository.deleteById(id);
        if (!deleted) {
            throw new InvalidReservationException(ErrorMessage.RESERVATION_NOT_FOUND);
        }
    }
}
