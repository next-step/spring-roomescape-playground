package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.JdbcTemplateReservationRepository;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.exception.ReservationException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final JdbcTemplateReservationRepository jdbcRepository;

    public ReservationService(JdbcTemplateReservationRepository jdbcRepository) {
        this.jdbcRepository = jdbcRepository;
    }

    public List<ReservationResponse> findAll() {
        return jdbcRepository.findAll().stream()
                .map(ReservationResponse::from)
                .collect(Collectors.toList());
    }

    public ReservationResponse create(ReservationRequest request) {
        Reservation newReservation = Reservation.create(
                request.name(), request.parseDate(), request.parseTime()
        );
        Reservation storedReservation = jdbcRepository.save(newReservation);
        return ReservationResponse.from(storedReservation);
    }

    public void delete(Long id) {
        int affectedRows = jdbcRepository.deleteById(id);
        if (affectedRows == 0) {
            throw new ReservationException("[ERROR] 삭제하려는 예약을 찾을 수 없습니다.");
        }
    }
}
