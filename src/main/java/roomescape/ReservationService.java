package roomescape;

import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.List;

@Service
public class ReservationService {

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("uuuu-MM-dd")
                    .withResolverStyle(ResolverStyle.STRICT);

    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;
    private final Clock clock;

    public ReservationService(
            ReservationRepository reservationRepository,
            TimeRepository timeRepository,
            Clock clock
    ) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
        this.clock = clock;
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public Reservation findById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(NotFoundReservationException::new);
    }

    public Reservation create(String name, String date, Long timeId) {
        validateName(name);

        ReservationTime time = findTime(timeId);
        LocalDate reservationDate =
                validateReservationDateTime(date, time.getTime());

        Reservation reservation = new Reservation(
                null,
                name,
                reservationDate,
                time
        );

        return reservationRepository.save(reservation);
    }

    public Reservation update(
            Long id,
            String name,
            String date,
            Long timeId
    ) {
        validateName(name);

        ReservationTime time = findTime(timeId);
        LocalDate reservationDate =
                validateReservationDateTime(date, time.getTime());

        Reservation reservation = new Reservation(
                id,
                name,
                reservationDate,
                time
        );

        if (reservationRepository.update(reservation) == 0) {
            throw new NotFoundReservationException();
        }

        return reservation;
    }

    public void delete(Long id) {
        if (reservationRepository.delete(id) == 0) {
            throw new NotFoundReservationException();
        }
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new InvalidReservationException();
        }
    }

    private ReservationTime findTime(Long timeId) {
        if (timeId == null || timeId <= 0) {
            throw new InvalidReservationException();
        }

        return timeRepository.findById(timeId)
                .orElseThrow(InvalidReservationException::new);
    }

    private LocalDate validateReservationDateTime(
            String date,
            LocalTime time
    ) {
        if (date == null
                || !date.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}")) {
            throw new InvalidReservationException();
        }

        try {
            LocalDate parsedDate = LocalDate.parse(date, DATE_FORMATTER);
            LocalDateTime reservationDateTime =
                    LocalDateTime.of(parsedDate, time);

            if (!reservationDateTime.isAfter(LocalDateTime.now(clock))) {
                throw new InvalidReservationException();
            }

            return parsedDate;
        } catch (DateTimeParseException exception) {
            throw new InvalidReservationException();
        }
    }
}
