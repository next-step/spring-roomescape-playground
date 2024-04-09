package roomescape.dto;

import roomescape.entity.Reservation;
import roomescape.entity.Time;

import java.time.LocalDate;

public class ReservationDTO {

    private String name;
    private LocalDate date;
    private Long timeId;

    public ReservationDTO(String name, LocalDate date, Long time) {
        this.name = name;
        this.date = date;
        this.timeId = time;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public Long getTimeId() {
        return timeId;
    }

    public static Reservation toEntity(ReservationDTO reservationDTO, Time time, Long id) {
        return new Reservation(id, reservationDTO.name, reservationDTO.date, time);
    }

    @Override
    public String toString() {
        return "ReservationDTO{" +
                "name='" + name + '\'' +
                ", date=" + date +
                ", timeId=" + timeId +
                '}';
    }

}