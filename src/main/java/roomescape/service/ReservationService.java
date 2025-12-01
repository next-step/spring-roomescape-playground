package roomescape.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.advice.IdempotencyKeyMismatchException;
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
    private final TimeRepository timeRepository;

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Transactional
    public Reservation addReservation(ReservationCreateRequest request, String idempotencyKey) {
        if (idempotencyRepository.exists(idempotencyKey)) {
            return validateAndGetExistingReservation(request, idempotencyKey);
        }

        Time time = timeRepository.findById(request.timeId());

        if (reservationRepository.existsByDateAndTime(request.date(), time.getId())) {
            throw new IllegalArgumentException("이미 예약된 시간입니다!");
        }

        Reservation newReservation = Reservation.create(
                request.name(),
                request.date(),
                time
        );

        Reservation savedReservation = reservationRepository.save(newReservation);
        idempotencyRepository.save(idempotencyKey, savedReservation.getId());

        return savedReservation;
    }

    private Reservation validateAndGetExistingReservation(ReservationCreateRequest request, String idempotencyKey) {
        Long existingReservationId = idempotencyRepository.getReservationId(idempotencyKey);

        Reservation existingReservation = reservationRepository.findById(existingReservationId); // 없을 경우 예외 처리 필요

        if (!matches(existingReservation, request)) {
            throw new IdempotencyKeyMismatchException();
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
            throw new IllegalArgumentException("삭제할 예약을 찾을 수 없습니다.");
        }
    }
}
