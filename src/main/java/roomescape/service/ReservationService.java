package roomescape.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.exception.status.ReservationNotFoundException;
import roomescape.repository.ReservationRepository;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationResponse> findAll() {
        return reservationRepository.findAll();
    }

    public ReservationResponse create(ReservationRequest request) {
        Reservation reservation = Reservation.of(
                null,
                request.getName(),
                request.getDate(),
                request.getTime()
        );
        return reservationRepository.save(reservation);
    }

    public void deleteById(Long id) {
        int affected = reservationRepository.deleteById(id);
        if (affected == 0) {
            throw new ReservationNotFoundException(id);
        }
    }
}

