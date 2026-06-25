package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.domain.ReservationValidator;
import roomescape.dto.ReservationRequest;
import roomescape.repository.ReservationRepository;
import roomescape.repository.TimeRepository;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;
    private final ReservationValidator validator;

    public ReservationService(ReservationRepository reservationRepository, TimeRepository timeRepository, ReservationValidator validator) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
        this.validator = validator;
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation createReservation(ReservationRequest request) {
        Long timeId = request.timeId();
        Time reservationTime = timeRepository.findById(timeId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 시간입니다."));

        validator.validateReservationDateTime(request.date(), reservationTime.getTime());
        validateDuplicatedReservation(request.date().toString(), reservationTime.getId());

        Reservation newReservation = new Reservation(
                null,
                request.name(),
                request.date().toString(),
                reservationTime
        );
        return reservationRepository.save(newReservation);
    }

    public void deleteReservation(Long id) {
        if (!reservationRepository.deleteById(id)) {
            throw new IllegalArgumentException("존재하지 않는 예약입니다.");
        }
    }

    private void validateDuplicatedReservation(String date, Long timeId) {
        if (reservationRepository.existsByDateAndTimeId(date, timeId)) {
            throw new IllegalArgumentException("이미 예약된 시간입니다.");
        }
    }
}
