package roomescape.dto;

import roomescape.domain.Reservation;

public class ReservationDto {
    private String name;
    private String date;
    private String time;

    public ReservationDto(String name, String date, String time) {
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

    public static ReservationDto convertToDto(Reservation reservation) {
        return new ReservationDto(reservation.getName(), reservation.getDate(), reservation.getTime());
    }
}
