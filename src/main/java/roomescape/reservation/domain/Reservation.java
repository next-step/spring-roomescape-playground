package roomescape.reservation.domain;

import java.sql.Date;
import roomescape.time.domain.Time;

public class Reservation {

    private final Long id;
    private final String name;
    private final Date date;
    private final Time time;

    public Reservation(
            Long id,
            String name,
            Date date,
            Time time
    ) {
        validateName(name);

        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static Reservation of(
            String name,
            String date,
            Time time
    ) {
        return new Reservation(
                null,
                name,
                Date.valueOf(date),
                time
        );
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("예약자 이름은 필수입니다.");
        }

        if (name.length() > 10) {
            throw new IllegalArgumentException("예약자 이름은 10자 이하여야 합니다.");
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }
}