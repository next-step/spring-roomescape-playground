package roomescape.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.exception.status.ReservationNotFoundException;

@Service
public class ReservationService {
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    public ReservationResponse create(ReservationRequest request) {
        Reservation reservation = Reservation.of(index.getAndIncrement(), request.getName(), request.getDate(), request.getTime());
        reservations.add(reservation);
        return new ReservationResponse(reservation);
    }

    public List<ReservationResponse> findAll() {
        return reservations.stream().map(ReservationResponse::new).toList();
    }

    public void deleteById(Long id) {
        boolean removed = reservations.removeIf(r -> r.getId().equals(id));
        if (!removed) throw new ReservationNotFoundException(id);
    }
}

