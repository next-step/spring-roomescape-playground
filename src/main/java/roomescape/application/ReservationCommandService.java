package roomescape.application;

import org.springframework.stereotype.Service;
import roomescape.application.dto.ReservationCreateRequest;
import roomescape.application.dto.ReservationResponse;
import roomescape.domain.repository.ReservationRepository;

@Service
public class ReservationCommandService {
    private final ReservationRepository jdbcReservationRepository;

    public ReservationCommandService(final ReservationRepository jdbcReservationRepository) {
        this.jdbcReservationRepository = jdbcReservationRepository;
    }

    public ReservationResponse addReservation(final ReservationCreateRequest request) {
        validateNotEmptyRequest(request);
        return ReservationResponse.from(jdbcReservationRepository.save(ReservationCreateRequest.from(request)));
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
