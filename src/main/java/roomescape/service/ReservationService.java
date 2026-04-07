package roomescape.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.Manager.IdempotencyManager;
import roomescape.advice.ErrorCode;
import roomescape.advice.IdempotencyKeyMismatchException;
import roomescape.advice.RoomEscapeException;
import roomescape.dto.reservationDto.ReservationCreateRequest;
import roomescape.model.Reservation;
import roomescape.model.Time;
import roomescape.repository.IdempotencyRepository;
import roomescape.repository.ReservationRepository;
import roomescape.repository.TimeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final IdempotencyRepository idempotencyRepository;
    private final IdempotencyManager idempotencyManager;
    private final TimeRepository timeRepository;

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Transactional
    public Reservation addReservation(ReservationCreateRequest request, String idempotencyKey) {
        Time time = timeRepository.findById(request.timeId());

        if (reservationRepository.existsByDateAndTime(request.date(), time.getId())) {
            throw new RoomEscapeException(ErrorCode.RESERVATION_NOT_FOUND);
        }

        Reservation newReservation = Reservation.create(
                request.name(),
                request.date(),
                time
        );

        Reservation savedReservation = reservationRepository.save(newReservation);

        idempotencyManager.saveResponse(idempotencyKey, savedReservation);

        return savedReservation;
    }

    private Reservation validateAndGetExistingReservation(ReservationCreateRequest request, String idempotencyKey) {
        Long existingReservationId = idempotencyRepository.getReservationId(idempotencyKey);

        Reservation existingReservation = reservationRepository.findById(existingReservationId); // 없을 경우 예외 처리 필요

        if (!matches(existingReservation, request)) {
            throw new RoomEscapeException(ErrorCode.IDEMPOTENCY_KEY_MISMATCH);
        }

        return existingReservation;
    }

    private boolean matches(Reservation reservation, ReservationCreateRequest request) {
        return reservation.getName().equals(request.name()) &&
                reservation.getDate().equals(request.date()) &&
                reservation.getTime().getId().equals(request.timeId());
    }


    @Transactional
    public void deleteReservation(Long id) {

        int count = reservationRepository.deleteById(id);

        if (count == 0) {
            throw new RoomEscapeException(ErrorCode.RESERVATION_NOT_FOUND);
        }
    }
}
