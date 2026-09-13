package roomescape;

import java.time.format.DateTimeFormatter;

public class ReservationTimeResponse {

    private final Long id;
    private final String time;
    private static final DateTimeFormatter TIME_FORMATTER =
            DateTimeFormatter.ofPattern("HH:mm");

    public ReservationTimeResponse(Long id, String time) {
        this.id = id;
        this.time = time;
    }

    public static ReservationTimeResponse from(
            ReservationTime reservationTime
    ) {
        return new ReservationTimeResponse(
                reservationTime.getId(),
                reservationTime.getTime().format(TIME_FORMATTER)
        );
    }

    public Long getId() {
        return id;
    }

    public String getTime() {
        return time;
    }
}
