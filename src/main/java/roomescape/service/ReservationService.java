package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
            throw new IllegalArgumentException("중복 요청입니다.");
        }
        idempotencyRepository.save(idempotencyKey);

        if (reservationRepository.existsByDateAndTime(newReservation.getDate(), newReservation.getTime())) {
            throw new IllegalArgumentException("이미 예약된 시간입니다!");
        }

        return reservationRepository.save(newReservation);
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
