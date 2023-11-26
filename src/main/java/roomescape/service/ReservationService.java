package roomescape.service;

import static roomescape.validation.ReservationValidation.validate;

import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.Reservation;
import roomescape.repository.ReservationRepository;

@Service
@Transactional
public class ReservationService {

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    // 예약 추가
    public  ResponseEntity<Reservation> reserve(Reservation reservation) {
        validate(reservation);

        Reservation newReservation = reservationRepository.save(reservation);
        return ResponseEntity
                .created(URI.create("/reservations/" + newReservation.id()))
                .contentType(MediaType.APPLICATION_JSON)
                .body(newReservation);
    }

    // 예약 조회
    @Transactional(readOnly = true)
    public ResponseEntity<List<Reservation>> findReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        return ResponseEntity.ok().body(reservations);
    }

    // 예약 삭제
    public ResponseEntity<Void> deleteReservation(String id) {
        reservationRepository.delete(id);
        return ResponseEntity.noContent().build();
    }
}
