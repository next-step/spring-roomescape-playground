package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.InMemoryReservationRepository;
import roomescape.domain.JdbcTemplateReservationRepository;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final InMemoryReservationRepository reservationRepository = new InMemoryReservationRepository();
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
        Reservation storedReservation = reservationRepository.save(newReservation);
        return ReservationResponse.from(storedReservation);
    }

    public void delete(Long id) {
        reservationRepository.deleteById(id);
    }
}

