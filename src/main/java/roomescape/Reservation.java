package roomescape;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;

public class Reservation {
    private static final AtomicLong idCounter = new AtomicLong(1);

    private Long id;
    private String name;
    private String date;
    private String time;

    public Reservation() {
    }

    public Reservation(Reservation reservation) {
        this.id = idCounter.getAndIncrement();
        this.name = reservation.name;
        this.date = reservation.date;
        this.time = reservation.time;
    }

    public boolean isPast() {
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
