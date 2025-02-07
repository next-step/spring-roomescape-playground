package roomescape.service;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.dto.request.ReservationCreateRequest;
import roomescape.dto.response.ReservationResponse;

@Service
public class ReservationService {
    private AtomicLong index = new AtomicLong(0);

    public ReservationResponse reserve(ReservationCreateRequest request) {
        return new ReservationResponse(index.incrementAndGet(), request.getName(), request.getDate(), request.getTime());
    }
}
