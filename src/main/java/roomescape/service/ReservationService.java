package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.advice.IdempotencyKeyMismatchException;
import roomescape.dto.ReservationCreateRequest;
import roomescape.model.Reservation;
import roomescape.repository.IdempotencyRepository;
import roomescape.repository.ReservationRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final IdempotencyRepository idempotencyRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository,
                              IdempotencyRepository idempotencyRepository
    ) {
        this.reservationRepository = reservationRepository;
        this.idempotencyRepository = idempotencyRepository;
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Transactional
    public Reservation addReservation(Reservation newReservation, String idempotencyKey) {

        if (idempotencyRepository.exists(idempotencyKey)) {
            Long existingReservationId = idempotencyRepository.getReservationId(idempotencyKey);

            Reservation existingReservation = reservationRepository.findById(existingReservationId);

            if (!isSameReservation(existingReservation, newReservation)) {
                throw new IdempotencyKeyMismatchException();
            }

            return existingReservation;
        }

        if (reservationRepository.existsByDateAndTime(newReservation.getDate(), newReservation.getTime())) {
            throw new IllegalArgumentException("이미 예약된 시간입니다!");
        }

        Reservation savedReservation = reservationRepository.save(newReservation);

        idempotencyRepository.save(idempotencyKey, savedReservation.getId());

        return savedReservation;
    }

    public Reservation addReservation(Reservation newReservation) {


        Reservation savedReservation = reservationRepository.save(newReservation);

        //idempotencyRepository.save(savedReservation.getId());

        return savedReservation;
    }

    private boolean isSameReservation(Reservation r1, Reservation r2) {
        return r1.getName().equals(r2.getName()) &&
                r1.getDate().equals(r2.getDate()) &&
                r1.getTime().equals(r2.getTime());
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    public boolean exitsKey(String idempotencyKey) {
        return idempotencyRepository.exists(idempotencyKey);
    }

    public Reservation get(ReservationCreateRequest request) {
        return reservationRepository.get(request.name(), request.date(), request.time());
    }
}
