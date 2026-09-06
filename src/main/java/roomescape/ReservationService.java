package roomescape;

import org.springframework.stereotype.Service;

import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.time.Clock;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final Clock clock;

    public ReservationService(
            ReservationRepository reservationRepository,
            Clock clock
    ) {
        this.reservationRepository = reservationRepository;
        this.clock = clock;
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public void delete(Long id) {
        int deletedCount = reservationRepository.delete(id);

        if (deletedCount == 0) {
            throw new NotFoundReservationException();
        }
    }

    public Reservation findById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(NotFoundReservationException::new);
    }

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("uuuu-MM-dd")
                    .withResolverStyle(ResolverStyle.STRICT);

    private static final DateTimeFormatter TIME_FORMATTER =
            DateTimeFormatter.ofPattern("HH:mm")
                    .withResolverStyle(ResolverStyle.STRICT);

    private void validateReservationDateTime(ReservationRequest request) {
        String date = request.getDate();
        String time = request.getTime();

        if (date == null || time == null
                || !date.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}")
                || !time.matches("[0-9]{2}:[0-9]{2}")) {
            throw new InvalidReservationException();
        }

        LocalDateTime reservationDateTime;

        try {
            LocalDate parsedDate = LocalDate.parse(date, DATE_FORMATTER);
            LocalTime parsedTime = LocalTime.parse(time, TIME_FORMATTER);
            reservationDateTime = LocalDateTime.of(parsedDate, parsedTime);
        } catch (DateTimeParseException exception) {
            throw new InvalidReservationException();
        }

        if (!reservationDateTime.isAfter(LocalDateTime.now(clock))) {
            throw new InvalidReservationException();
        }
    }

    public Reservation create(ReservationRequest request) {
        validateReservationDateTime(request);
        return reservationRepository.save(request);
    }

    public Reservation update(Long id, ReservationRequest request) {
        validateReservationDateTime(request);

        int updatedCount = reservationRepository.update(id, request);

        if (updatedCount == 0) {
            throw new NotFoundReservationException();
        }

        return new Reservation(
                id,
                request.getName(),
                request.getDate(),
                request.getTime()
        );
    }
}
