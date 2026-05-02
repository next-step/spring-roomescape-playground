package roomescape;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import roomescape.exceptions.EmptyNameException;
import roomescape.exceptions.PastDateTimeException;

public class Reservation {
    private Long id;
    private String name;
    private String date;
    private String time;

    public Reservation() {
    }

    public Reservation(Reservation reservation, Long id) {
        validate(reservation);
        this.id = id;
        this.name = reservation.name;
        this.date = reservation.date;
        this.time = reservation.time;
    }

    private void validate(Reservation reservation) {
        if (reservation.name.isBlank()) {
            throw new EmptyNameException();
        }
        if (reservation.isPast()) {
            throw new PastDateTimeException();
        }
    }

    private boolean isPast() {
        String dateTimeString = date + " " + time;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);
        LocalDateTime currentDateTime = LocalDateTime.now();

        return dateTime.isBefore(currentDateTime);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
