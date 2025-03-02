package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.dto.request.CreateReservationRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.global.exception.BadRequestException;
import roomescape.repository.ReservationRepository;
import roomescape.repository.TimeRepository;

import java.time.Clock;
import java.util.List;

import static roomescape.global.exception.ExceptionMessage.INVALID_DATETIME;
import static roomescape.global.exception.ExceptionMessage.RESERVATION_ALREADY_EXISTS;
import static roomescape.global.exception.ExceptionMessage.RESERVATION_NOT_EXISTS;
import static roomescape.global.exception.ExceptionMessage.TIME_NOT_EXISTS;

@Service
public class ReservationService {

    private final Clock clock;
    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;

    public ReservationService(final Clock clock, final ReservationRepository reservationRepository, final TimeRepository timeRepository) {
        this.clock = clock;
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
    }

    public ReservationResponse createReservation(final CreateReservationRequest request) {
        final Time time = getTime(request.timeId());
        final Reservation reservation = request.toReservation(time);

        validateAvailability(reservation);
        validateExpiredDateTime(reservation);

        final Reservation reservationWithId = reservationRepository.save(reservation);
        return new ReservationResponse(reservationWithId);
    }

    private Time getTime(final Long timeId) {
        return timeRepository.findById(timeId)
                .orElseThrow(() -> new BadRequestException(TIME_NOT_EXISTS.getMessage()));
    }

    private void validateAvailability(final Reservation reservation) {
        if (reservationRepository.existsByDateAndTime(reservation.getDate(), reservation.getTime().getTime())) {
            throw new BadRequestException(RESERVATION_ALREADY_EXISTS.getMessage());
        }
    }

    private void validateExpiredDateTime(final Reservation reservation) {
        if (reservation.isExpired(clock)) {
            throw new BadRequestException(INVALID_DATETIME.getMessage());
        }
    }

    public List<ReservationResponse> getReservations() {
        return reservationRepository.findAll()
                .stream()
                .map(ReservationResponse::new)
                .toList();
    }

    public void deleteReservation(final long reservationId) {
        final Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new BadRequestException(RESERVATION_NOT_EXISTS.getMessage()));
        reservationRepository.deleteById(reservation.getId());
    }
}
