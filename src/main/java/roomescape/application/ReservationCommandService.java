package roomescape.application;

import org.springframework.stereotype.Service;
import roomescape.application.dto.ReservationCreateRequest;
import roomescape.application.dto.ReservationResponse;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.domain.repository.ReservationRepository;

import java.time.LocalDate;

@Service
public class ReservationCommandService {
    private final ReservationRepository jdbcReservationRepository;

    public ReservationCommandService(final ReservationRepository jdbcReservationRepository) {
        this.jdbcReservationRepository = jdbcReservationRepository;
    }

    public ReservationResponse addReservation(final ReservationCreateRequest request) {
        validateNotEmptyRequest(request);
        final Reservation reservation = new Reservation(
                request.getName(), LocalDate.parse(request.getDate()), new Time(request.getTime())
        );
        return ReservationResponse.from(jdbcReservationRepository.save(reservation));
    }

    private void validateNotEmptyRequest(final ReservationCreateRequest request) {
        if (request.getName().isEmpty() || request.getDate().isEmpty() || request.getTime() == null) {
            throw new IllegalArgumentException("예약 정보의 인자는 비어있을 수 없습니다.");
        }
    }

    public void removeReservation(final Long id) {
        jdbcReservationRepository.delete(id);
    }
}
