package roomescape.application.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.application.dto.CreateReservationRequestDto;
import roomescape.common.error.ErrorCode;
import roomescape.common.error.exception.EntityNotFoundException;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.exception.ReservationException;
import roomescape.repository.reservation.interfaces.ReservationRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(
            ReservationRepository reservationRepository
    ) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation findByIdOrThrow(Long reservationId) {
        return reservationRepository.findById(reservationId).orElseThrow(EntityNotFoundException::new);
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public Reservation reserve(CreateReservationRequestDto createReservationRequestDto) {
        throwInvalidReserveDateTime(createReservationRequestDto);
        Reservation reservationIdNull = createReservationRequestDto.toReservation();
        return reservationRepository.save(reservationIdNull);
    }

    public void cancelReservation(Long reservationId) {
        Reservation foundReservation = findByIdOrThrow(reservationId);
        reservationRepository.delete(foundReservation);
    }

    private void throwInvalidReserveDateTime(CreateReservationRequestDto createReservationRequestDto) {
        LocalDateTime now = LocalDateTime.now();
        if (LocalDateTime.of(createReservationRequestDto.date(), createReservationRequestDto.time()).isBefore(now)) {
            throw new ReservationException(ErrorCode.INVALID_RESERVE_VALUE);
        }
    }
}
