package roomescape.domain;

public class Reservation {
        private Long ReservationId;
        private String name;
        private String date;
        private Time time;

    public Reservation(Long ReservationId, String name, String date, Time time) {
        this.ReservationId = ReservationId;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Long getReservationId() {
        return this.ReservationId;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

}
