package roomescape.application.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.application.dto.request.CreateReservationRequest;
import roomescape.common.error.ErrorCode;
import roomescape.common.error.exception.EntityNotFoundException;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.exception.ReservationException;
import roomescape.domain.time.Time;
import roomescape.repository.reservation.interfaces.ReservationRepository;
import roomescape.repository.reservation.interfaces.TimeRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;

    public ReservationService(
            ReservationRepository reservationRepository,
            TimeRepository timeRepository
    ) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public Reservation createReservation(CreateReservationRequest createReservationRequestDto) {
        Time foundTime = getFoundTime(createReservationRequestDto);
        validReservationDateTime(createReservationRequestDto, foundTime);

        Reservation reservationIdNull = createReservationRequestDto.toReservation(foundTime);
        return reservationRepository.save(reservationIdNull);
    }

    public void deleteReservation(Long reservationId) {
        Reservation foundReservation = getFoundReservation(reservationId);
        reservationRepository.delete(foundReservation);
    }

    private Time getFoundTime(CreateReservationRequest createReservationRequestDto) {
        return timeRepository.findById(createReservationRequestDto.time())
                .orElseThrow(EntityNotFoundException::new);
    }

    private Reservation getFoundReservation(Long reservationId) {
        return reservationRepository.findById(reservationId).orElseThrow(EntityNotFoundException::new);
    }

    private void validReservationDateTime(CreateReservationRequest createReservationRequestDto, Time foundTime) {
        LocalDateTime reservedDateTime = getRequestReservedDateTime(createReservationRequestDto, foundTime);

        if (isBeforeCurrentTIme(reservedDateTime)) {
            throw new ReservationException(ErrorCode.INVALID_RESERVE_VALUE);
        }

        if (isExistsReservationDateTime(createReservationRequestDto, foundTime)) {
            throw new ReservationException(ErrorCode.EXISTS_TIME_VALUE);
        }
    }

    private static LocalDateTime getRequestReservedDateTime(CreateReservationRequest createReservationRequestDto,
                                                            Time foundTime) {
        return LocalDateTime.of(createReservationRequestDto.date(),
                foundTime.getAvailableTime());
    }

    private static boolean isBeforeCurrentTIme(LocalDateTime reservedDateTime) {
        LocalDateTime now = LocalDateTime.now();
        return reservedDateTime.isBefore(now);
    }

    private boolean isExistsReservationDateTime(
            CreateReservationRequest createReservationRequestDto,
            Time foundTime
    ) {
        return reservationRepository.isExistsByReservedDateAndTime(createReservationRequestDto.date(), foundTime);
    }
}
