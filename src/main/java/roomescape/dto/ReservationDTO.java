package roomescape.dto;

import java.time.LocalDate;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

public class ReservationDTO {
    private long id;
    private String name;
    private LocalDate date;
    private Time time;

    public ReservationDTO(String name, LocalDate date, Time time) {
        this.id = 0;
        this.name = name;
        this.date = date;
        this.time = time;
    }
    public ReservationDTO(long id, String name, LocalDate date, Time time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }
    public static ReservationDTO toEntity(long id, String name, LocalDate date, Time time){
        ReservationDTO newReservationDTO = new ReservationDTO(id, name, date, time);
        return newReservationDTO;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getId() {
        return String.valueOf(id);
    }
}
