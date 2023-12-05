package roomescape.service;

import static roomescape.utils.ErrorMessage.NO_DATA_ERROR;

import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.controller.ReservationForm;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.exception.NoDataException;
import roomescape.repository.ReservationRepository;
import roomescape.repository.TimeRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;

    // 예약 추가
    public ResponseEntity<Reservation> reserve(ReservationForm reservationForm) {
        Time time = timeRepository.find(reservationForm.getTime());
        Reservation reservation = new Reservation(reservationForm.getId(), reservationForm.getName(), reservationForm.getDate(), time);
        Long newReservationId = reservationRepository.save(reservation);
        return ResponseEntity
                .created(URI.create("/reservations/" + newReservationId))
                .contentType(MediaType.APPLICATION_JSON)
                .body(reservation);
    }

    // 예약 조회
    @Transactional(readOnly = true)
    public ResponseEntity<List<Reservation>> findReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        return ResponseEntity.ok().body(reservations);
    }

    // 예약 삭제
    public ResponseEntity<Void> deleteReservation(String id) {
        int deletedNum = reservationRepository.delete(id);
        if (deletedNum <= 0) {
            throw new NoDataException(NO_DATA_ERROR);
        }
        return ResponseEntity.noContent().build();
    }
}
