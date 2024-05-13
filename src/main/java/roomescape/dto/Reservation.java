package roomescape.dto;

import roomescape.exception.NotFoundReservationException;

public class Reservation {
    private Long id;
    private String name;
    private String date;
    private String time;

    public Reservation() {
    }

    public Reservation(Long id, String name, String date, String time) {
        validateEmpty(name);
        validateEmpty(date);
        validateEmpty(time);

        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(String name, String date, String time) {
        validateEmpty(name);
        validateEmpty(date);
        validateEmpty(time);

        this.name = name;
        this.date = date;
        this.time = time;
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

    public static Reservation toEntity(Long id, Reservation reservation) {
        return new Reservation(id, reservation.name, reservation.date, reservation.time);
    }

    private void validateEmpty(String data) {
        if (data.isEmpty()) {
            throw new NotFoundReservationException("값이 존재하지 않습니다.");
        }
    }
}
