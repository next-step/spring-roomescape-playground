package roomescape.model;

public class Reservation {

    private Long reservationId;
    private String name;
    private String date;
    private String time;

    public Reservation() {
        this.reservationId = reservationId;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }
}
