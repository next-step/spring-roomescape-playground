package roomescape.data.entity;

import roomescape.common.exception.ReservationErrorCode;
import roomescape.common.exception.ReservationException;
import roomescape.data.dto.ReservationResponse;

final public class Reservation {

    private long id;
    private String name;
    private String date;
    private String time;

    public Reservation(long id, String name, String date, String time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static Reservation create(long id, String name, String date, String time) {
        validate(name, date, time);
        return new Reservation(id, name, date, time);
    }

    private static void validate(String name, String date, String time) {
        if (name == null || name.isEmpty()
                || date == null || date.isEmpty()
                || time == null || time.isEmpty()) {
            throw new ReservationException(ReservationErrorCode.INVALID_ARGUMENT_ERROR);
        }
    }

    public ReservationResponse createResponse() {
        return new ReservationResponse(id, name, date, time);
    }

    public long getId() {
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
