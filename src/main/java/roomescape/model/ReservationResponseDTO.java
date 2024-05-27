package roomescape.model;

import lombok.Data;

@Data
public class ReservationResponseDTO {
    private int id;

    private String name;

    private String date;

    private Time time;

    public ReservationResponseDTO(String name, String date, Time time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public ReservationResponseDTO(int id, String name, String date, Time time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public ReservationResponseDTO() {
    }

    public static ReservationResponseDTO ReservationToResponse(Reservation reservation) {
        ReservationResponseDTO result = new ReservationResponseDTO();
        result.setId(reservation.getId());
        result.setTime(reservation.getTime());
        result.setName(reservation.getName());
        result.setDate(reservation.getDate());
        return result;
    }
}
