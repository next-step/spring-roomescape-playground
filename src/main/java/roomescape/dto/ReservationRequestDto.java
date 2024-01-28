package roomescape.dto;

import roomescape.domain.Reservation;

public class ReservationRequestDto {
    private String name;
    private String date;
    private String time;

    public ReservationRequestDto(String name, String date, String time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public static ReservationRequestDto convertToDto(Reservation reservation) {
        return new ReservationRequestDto(reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    public Reservation toEntity(Long id) {
        return new Reservation(id, name, date, time);
    }
}
