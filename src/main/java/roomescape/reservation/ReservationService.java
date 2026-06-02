package roomescape.reservation;

import java.util.Collection;
import org.springframework.stereotype.Service;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.ReservationCreateRequest;
import roomescape.reservation.dto.ReservationCreateResponse;
import roomescape.reservation.dto.ReservationSelectResponse;
import roomescape.time.TimeRepository;
import roomescape.time.domain.Time;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;

    public ReservationService(ReservationRepository reservationRepository, TimeRepository timeRepository) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
    }

    public Collection<ReservationSelectResponse> getReservations() {
        return reservationRepository.findAll()
                .stream()
                .map(ReservationSelectResponse::from)
                .toList();
    }

    public ReservationCreateResponse create(ReservationCreateRequest request) {

        if (reservationRepository.existsDateAndTime(request.date(), request.timeId())) {
            throw new IllegalArgumentException("이미 예약된 날짜와 시간입니다.");
        }

        Time time = timeRepository.findById(request.timeId());

        Reservation reservation = Reservation.of(
                request.name(),
                request.date(),
                time
        );

        return ReservationCreateResponse.from(
                reservationRepository.save(reservation)
        );
    }

    public void deleteById(Long id) {
        reservationRepository.deleteById(id);
    }


}
