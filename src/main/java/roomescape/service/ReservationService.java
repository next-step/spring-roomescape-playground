package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.exception.BadRequestReservationException;
import roomescape.exception.ErrorMessage;
import roomescape.exception.NotFoundReservationException;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final List<Reservation> reservationList = Collections.synchronizedList(new ArrayList<>());
    private final AtomicLong index = new AtomicLong(0);

    public List<ReservationResponse> getAllReservations() {
        return reservationList.stream()
                .map(ReservationResponse::from)
                .collect(Collectors.toList());
    }

    public ReservationResponse createReservation(ReservationRequest request) {
        try {
            LocalDate date = LocalDate.parse(request.date());
            LocalTime time = LocalTime.parse(request.time());

            Long newId = index.incrementAndGet();

            Reservation newReservation = new Reservation(
                    newId,
                    request.name(),
                    date,
                    time
            );

            reservationList.add(newReservation);
            return ReservationResponse.from(newReservation);
        }
        catch (DateTimeParseException e) {
            throw new BadRequestReservationException(ErrorMessage.INVALID_DATE_TIME_FORMAT.getMessage());        }
    }

    public void deleteReservation(Long id) {
        boolean removed = reservationList.removeIf(reservation -> reservation.getId().equals(id));

        if (!removed) {
            throw new NotFoundReservationException(ErrorMessage.NOT_FOUND_RESERVATION.getMessage());
        }
    }
}
