package roomescape.domain;

import java.time.LocalDateTime;
import roomescape.exception.customexception.PastDateTimeException;

public class Reservation {
    private static final int RESERVATION_LENGTH_MINUTES = 60;

    private Long id;
    private String name;
    private LocalDateTime dateTime;

    public Reservation(String name, LocalDateTime dateTime) {
        validate(dateTime);
        this.name = name;
        this.dateTime = dateTime;
    }

    public Reservation(Long id, String name, LocalDateTime dateTime) {
        validate(dateTime);
        this.id = id;
        this.name = name;
        this.dateTime = dateTime;
    }

    public boolean conflicts(Reservation newReservation) {
        LocalDateTime endTime = dateTime.plusMinutes(RESERVATION_LENGTH_MINUTES);
        LocalDateTime newEndTime = newReservation.dateTime.plusMinutes(RESERVATION_LENGTH_MINUTES);

        return endTime.isAfter(newReservation.dateTime) && dateTime.isBefore(newEndTime);
    }

    private void validate(LocalDateTime dateTime) {
        if (dateTime.isBefore(LocalDateTime.now())) {
            throw new PastDateTimeException();
        }
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
