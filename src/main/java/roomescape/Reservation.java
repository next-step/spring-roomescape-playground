package roomescape;

import java.time.LocalDate;
import java.util.Objects;

public class Reservation {

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final ReservationTime time;

    public Reservation(
            Long id,
            String name,
            LocalDate date,
            ReservationTime time
    ) {
        this.id = id;
        this.name = name;
        this.date = Objects.requireNonNull(
                date,
                "예약 날짜는 필수입니다."
        );
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public ReservationTime getTime() {
        return time;
    }
}
