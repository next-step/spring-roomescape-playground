package roomescape.reservation.dto;

import roomescape.reservation.model.Reservation;

public class ReservationResponse {
    public long id;
    public String name;
    public String date;
    public String time;

    public ReservationResponse() {}

    public static ReservationResponse from(Reservation reservation) {
        ReservationResponse response = new ReservationResponse();
        response.id = reservation.getId();
        response.name = reservation.getName();
        response.date = reservation.getDate().toString();
        response.time = reservation.getTime().getTime().toString();

        return response;
    }
}