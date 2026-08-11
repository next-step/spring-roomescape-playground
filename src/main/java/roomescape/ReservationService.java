package roomescape;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public Reservation addReservation(ReservationRequest request) {
        validateNotPast(request);
        validateNotDuplicated(request);

        Reservation reservation = new Reservation(
                request.name(),
                request.date(),
                request.time()
        );

        return reservationRepository.save(reservation);
    }

    private void validateNotPast(ReservationRequest request) {
        LocalDateTime reservationDateTime = LocalDateTime.of(request.date(), request.time());

        if (reservationDateTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("과거 시간은 예약할 수 없습니다.");
        }
    }

    private void validateNotDuplicated(ReservationRequest request) {
        if (reservationRepository.existsByDateAndTime(request.date(), request.time())) {
            throw new IllegalArgumentException("이미 예약된 시간입니다.");
        }
    }
}
