package roomescape.service;


import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.exception.status.ReservationNotFoundException;
import roomescape.repository.ReservationRepository;
import roomescape.repository.TimeRepository;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;

    public List<ReservationResponse> findAll() {
        return reservationRepository.findAll();
    }

    public ReservationResponse create(ReservationRequest request) {
        LocalDate date = request.getDate();

        Time time = timeRepository.findById(request.getTimeId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 시간입니다."));

        Reservation reservation = Reservation.of(null, request.getName(), date, time);

        return reservationRepository.save(reservation);
    }

    public void deleteById(Long id) {
        int affected = reservationRepository.deleteById(id);
        if (affected == 0) {
            throw new ReservationNotFoundException(id);
        }
    }
}

