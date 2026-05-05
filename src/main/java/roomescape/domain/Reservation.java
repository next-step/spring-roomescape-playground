package roomescape.domain;

import java.time.LocalDateTime;
import roomescape.exceptions.PastDateTimeException;

public class Reservation {
    private static final int RESERVATION_LENGTH_MINUTES = 60;

    private final Long id;
    private final String name;
    private final LocalDateTime dateTime;

    public Reservation(Long id, String name, LocalDateTime dateTime) {
        if (isPast(dateTime)) {
            throw new PastDateTimeException();
        }
        this.id = id;
        this.name = name;
        this.dateTime = dateTime;
    }

    public boolean conflicts(Reservation newReservation) {
        LocalDateTime endTime = dateTime.plusHours(RESERVATION_LENGTH_MINUTES);
        LocalDateTime newEndTime = newReservation.dateTime.plusHours(RESERVATION_LENGTH_MINUTES);

        return endTime.isAfter(newReservation.dateTime) || dateTime.isBefore(newEndTime);
    }

    private boolean isPast(LocalDateTime dateTime) {
        return dateTime.isBefore(LocalDateTime.now());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
